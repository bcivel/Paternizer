/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author memiks
 */
public class PublishFileOnFTP {

    public boolean publishFile(String host, String port, String user, String password, String folder, List<File> files) throws IOException {
        FTPClient client = new FTPClient();
        client.connect(host, Integer.valueOf(port));
        if (user != null && password != null) {
            client.login(user, password);
        }

        client.setFileType(client.BINARY_FILE_TYPE);
        client.setFileTransferMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);

        // Positionement sur le bon repertoire
        if (client.changeWorkingDirectory(folder)) {

            for (File file : files) {
                FileInputStream infile2 = new FileInputStream(file);  //mon fichier que je veux envoyer
                try {
                    if (client.storeFile(file.getName(), infile2)) {
                        System.err.println(" FILE " + file.getName() + " SAVED");
                        return true;
                        // Upload OK
                    } else {
                        System.err.println(" FILE " + file.getName() + " NOT SAVED !!! " + client.getStatus());
                        // Erreur
                    }
                } finally {
                    infile2.close();
                }
            }
        }
        if (client.isConnected()) {
            client.logout();
        }
        return false;
    }


}
