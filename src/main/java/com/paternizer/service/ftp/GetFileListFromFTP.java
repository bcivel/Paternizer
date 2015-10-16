/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.service.ftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author bcivel
 */
public class GetFileListFromFTP {

    public JSONArray getFileList(String host, String port, String user, String password, String folder, String fileName) throws IOException {
        JSONArray success = new JSONArray();
        FTPClient client = new FTPClient();
        System.out.print("trying to connect" + host + ":" + Integer.valueOf(port));
        client.connect(host, Integer.valueOf(port));
        System.out.print(client.isConnected());
        if (user != null && password != null) {
            client.login(user, password);
        }

        client.setFileType(client.BINARY_FILE_TYPE);
        client.setFileTransferMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            String parent = "#";
            // Positionement sur le bon repertoire
            success = getListOfItem(client, folder, success, parent);
            if (client.isConnected()) {
                client.logout();
            }
            return success;
        } finally {
            try {
                baos.close();
            } catch (Exception ex) {
                System.out.print(ex);
            }
        }

    }

    private JSONArray getListOfItem(FTPClient client, String folder, JSONArray data, String parentName) throws IOException {

        if (client.changeWorkingDirectory(folder)) {
            System.out.print("changed to folder" + folder);
            FTPFile[] files = client.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    JSONObject obj = new JSONObject();
                    if (files[i].isFile()) {
                        obj.put("type", "file");
                        obj.put("id", files[i].getName());
                        obj.put("parent", parentName);
                        obj.put("text", files[i].getName());
                        obj.put("folder", folder);
                        obj.put("name", files[i].getName());
                        obj.put("icon", "jstree-file");
                    } else if (files[i].isDirectory()) {
                        obj.put("type", "folder");
                        obj.put("id", files[i].getName());
                        obj.put("parent", parentName);
                        obj.put("text", files[i].getName());
                        obj.put("folder", folder);
                        obj.put("name", files[i].getName());
                        getListOfItem(client, files[i].getName(), data, files[i].getName());
                    }
                    data.put(obj);
                    if (i == files.length - 1 && !parentName.equals("#")) {
                        client.changeToParentDirectory();
                    }

                }
            }
        }
        return data;
    }

    public JSONArray GetFileListFromSFTP(String host, String port, String user, String password, String folder) throws IOException, JSchException, SftpException {
        JSch jsch = new JSch();
        JSONArray success = new JSONArray();
        if (user == null || password == null) {
            return success;
        }
System.out.print("trying to connect" + host + ":" + Integer.valueOf(port));
        Session session = jsch.getSession(user, host);
        // Java 6 version
        session.setPassword(password);

        Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        System.out.print("connected");
        if (session.isConnected()) {
            // Initializing a channel
            Channel channel = session.openChannel("sftp");
            channel.connect();

            if (channel.isConnected()) {
                ChannelSftp csftp = (ChannelSftp) channel;

                // Positionement sur le bon repertoire
                csftp.cd(folder);

                Vector<LsEntry> entries = csftp.ls("*.*");
                for (LsEntry entry : entries) {
                    JSONObject obj = new JSONObject();
                    obj.put("type", "file");
                    obj.put("id", entry.getFilename());
                    obj.put("parent", folder);
                    obj.put("text", entry.getFilename());
                    obj.put("folder", folder);
                    obj.put("name", entry.getFilename());
                    obj.put("icon", "jstree-file");
                    success.put(obj);
                }
                
                if (csftp.isConnected()) {
                    csftp.disconnect();
                }

                if (session.isConnected()) {
                    session.disconnect();
                }
                return success;
            }
        }
        return success;
    }
}
