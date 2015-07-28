/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.servlet.ServletOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author bcivel
 */
public class GetFileFromFTP {

    public String getFile(String host, String port, String user, String password, String folder, String fileName) throws IOException {
        String success = "";
        FTPClient client = new FTPClient();
        client.connect(host, Integer.valueOf(port));
        System.out.print(client.isConnected());
        if (user != null && password != null) {
            client.login(user, password);
        }

        client.setFileType(client.BINARY_FILE_TYPE);
        client.setFileTransferMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // Positionement sur le bon repertoire
            if (client.changeWorkingDirectory(folder)) {

                FTPFile[] files = client.listFiles();

                String lastFileName = "";
                for (int a = files.length - 1; a > 0; a--) {
                    if (files[a].isFile()) {
                        lastFileName = files[a].getName();
                        break;
                    }
                }

                //If filename empty, get the last file
                String nameOfFileToRetrieve = fileName != null ? fileName : lastFileName;

                if (!"".equals(lastFileName)) {
                    if (client.retrieveFile(nameOfFileToRetrieve, baos)) {
                        success = new String(baos.toByteArray());
                    }
                } else {
                    success = "No File in the Folder";
                }
            }
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

    public String getFileOnSFTP(String host, String port, String user, String password, String folder, String fileName) throws IOException, JSchException, SftpException {
        JSch jsch = new JSch();
        String success = "";
        if (user == null || password == null) {
            return "USER/PASS Must be feed";
        }

        Session session = jsch.getSession(user, host);
        // Java 6 version
        session.setPassword(password);

        Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        if (session.isConnected()) {
            // Initializing a channel
            Channel channel = session.openChannel("sftp");
            channel.connect();

            if (channel.isConnected()) {
                ChannelSftp csftp = (ChannelSftp) channel;

                // Positionement sur le bon repertoire
                csftp.cd(folder);
                InputStream is = csftp.get(fileName);
                success = IOUtils.toString(is, "UTF-8"); 

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
