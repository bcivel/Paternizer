/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.service;

import static com.paternizer.constants.FileConstants.DOCUMENT_FOLDER;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author bcivel
 */
public class GenerateXMLFile {

    public String generate(String templatePath, String outputPath) {
        String result = "";
        URL url = null;
        try {
//            url = new URL(templateURL);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            InputStream istream = connection.getInputStream();
            
            File source = new File(templatePath);
            File dest = new File(outputPath);
                
            FileUtils.copyFile(source, dest);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(GenerateXMLFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenerateXMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
