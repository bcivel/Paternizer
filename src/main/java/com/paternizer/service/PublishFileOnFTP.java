/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author memiks
 */
public class PublishFileOnFTP {

    public boolean publishFile(File file) throws IOException {
        FTPClient client = new FTPClient();
        client.connect("ftp.redoute.fr", 21);
        client.login("tempo2", "kFjvBMYC");

        client.setFileType(client.BINARY_FILE_TYPE);

        // Positionement sur le bon repertoire
        if (client.changeWorkingDirectory("./tmp/flesur/")) {

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
        if (client.isConnected()) {
            client.logout();
        }
        return false;
    }


}
