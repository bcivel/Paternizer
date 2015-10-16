/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.service.ftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.json.JSONObject;

/**
 *
 * @author bcivel
 */
public class GetFileFromFTP {

    /**
     * Get File from FTP Server. If filename is empty, it will retrieve the last
     * file.
     *
     * @param host
     * @param port
     * @param user
     * @param password
     * @param folder
     * @param fileName
     * @return
     * @throws IOException
     */
    public JSONObject getFile(String host, String port, String user, String password, String folder, String fileName) throws IOException {
        JSONObject success = new JSONObject();

        /**
         * Connect to FTP
         */
        FTPClient client = new FTPClient();
        client.connect(host, Integer.valueOf(port));
        if (user != null && password != null) {
            client.login(user, password);
        }

        client.setFileType(client.BINARY_FILE_TYPE);
        client.setFileTransferMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // Positioning on directory
            if (client.changeWorkingDirectory(folder)) {

                FTPFile[] files = client.listFiles();

                //Get list of files to get the last one
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
                        success.put("name", lastFileName);
                        success.put("content", new String(baos.toByteArray()));
                    }
                } else {
                    success = null;
                }
            }
            if (client.isConnected()) {
                client.logout();
            }
            return success;
        } finally {
            try {
                baos.close();
            } catch (IOException ex) {
                System.out.print(ex);
            }
        }

    }

    public JSONObject getFileOnSFTP(String host, String port, String user, String password, String folder, String fileName) throws IOException, JSchException, SftpException {
        JSch jsch = new JSch();
        JSONObject success = new JSONObject();
        
        //System.out.print("trying to connect" + host + ":" + Integer.valueOf(port));
        Session session = jsch.getSession(user, host);
        // Java 6 version
        session.setPassword(password);

        Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        //System.out.print("connected");
        if (session.isConnected()) {
            // Initializing a channel
            Channel channel = session.openChannel("sftp");
            channel.connect();

            if (channel.isConnected()) {
                ChannelSftp csftp = (ChannelSftp) channel;

                // Positionement sur le bon repertoire
                csftp.cd(folder);

                if (fileName == null) {
                    Vector<ChannelSftp.LsEntry> entries = csftp.ls("*.*");
                    fileName = entries.get(0).getFilename();
                }
                //System.out.print(fileName);

                InputStream is = csftp.get(fileName);
                success.put("name", fileName);
                success.put("content", IOUtils.toString(is, "UTF-8"));

                if (csftp.isConnected()) {
                    csftp.disconnect();
                }

                if (session.isConnected()) {
                    session.disconnect();
                }
                return success;
            }
        }
        return null;
    }

}
