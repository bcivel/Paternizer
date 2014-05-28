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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author memiks
 */
public class PublishFileOnSFTP {

    public boolean publishFile(String host, String port, String user, String password, String folder, List<File> files) throws IOException, JSchException, SftpException {
        JSch jsch = new JSch();
        if (user == null || password == null) {
            return false;
        }

        Session session = jsch.getSession(user, host, Integer.valueOf(port));
        // Java 6 version
        session.setPassword(password.getBytes(Charset.forName("ISO-8859-1")));

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

                for (File file : files) {
                    FileInputStream infile2 = new FileInputStream(file);  //mon fichier que je veux envoyer
                    try {
                        csftp.put(infile2, file.getName());
                    } finally {
                        infile2.close();
                    }
                }

                if (csftp.isConnected()) {
                    csftp.disconnect();
                }

                if (session.isConnected()) {
                    session.disconnect();
                }
                return true;
            }
        }
        return false;
    }


}
