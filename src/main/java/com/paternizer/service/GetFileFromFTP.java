/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author bcivel
 */
public class GetFileFromFTP {

    public boolean getFile(String host, String port, String user, String password, String folder, String fileName) throws IOException {
        FTPClient client = new FTPClient();
        client.connect(host, Integer.valueOf(port));
        System.out.print(client.isConnected());
        if (user != null && password != null) {
            client.login(user, password);
        }
        
        client.setFileType(client.BINARY_FILE_TYPE);
        client.setFileTransferMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);

        
        // Positionement sur le bon repertoire
        if (client.changeWorkingDirectory(folder)) {
            
            File downloadFile1 = new File("C:\\Users\\bcivel\\Desktop\\toto.txt");
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = client.retrieveFile(fileName, outputStream1);
            outputStream1.close();
 
            
        }
        if (client.isConnected()) {
            client.logout();
        }
        return false;
    }


}
