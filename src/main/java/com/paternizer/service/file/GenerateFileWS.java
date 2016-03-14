/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.service.file;

import com.paternizer.constants.FileConstants;
import com.paternizer.service.TemplateService;
import com.paternizer.service.email.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import org.json.JSONObject;

/**
 *
 * @author bcivel
 */
@WebService
@SOAPBinding(style = Style.RPC)
public class GenerateFileWS {

    public void generateFile(
            @WebParam(name = "fileURL") String fileURL,
            @WebParam(name = "fileName") String fileName,
            @WebParam(name = "parameters") String parameters) {

        String template = null;
            try {
                URL templateURL = new URL(fileURL);
                TemplateService templateService = new TemplateService();
                template = templateService.getFile(templateURL);
            } catch (MalformedURLException e) {
                System.err.println(" Unknow URL : " + fileURL + ".");
            } catch (IOException e) {
                System.err.println(e);
            }

            try{
            if (template != null) {
                JSONObject jsonObj = new JSONObject(parameters);
                
                for (int a=0; a<jsonObj.names().length(); a++) {
                    String parameter = (String) jsonObj.names().get(a);
                    String values = jsonObj.getString(parameter);

                    System.err.println(" PARAMETER : " + parameter + " VALUE : " + values);

                    template = template.replaceAll("#" + parameter + "#", values);
                }

                // if no file name just display file instead of create in in PaternizerDocuments
                if (fileName != null && !"".equals(fileName.trim())) {
                    //TODO Send extension in parameter
                    File file = new File(FileConstants.DOCUMENT_FOLDER + "temp" + FileConstants.FOLDER_SEPARATOR + fileName);
//                    if (!file.exists()) {
//                            file.mkdirs();
//                        }
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(template.getBytes());
                    fileOutputStream.close();

                } 
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenerateFileWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenerateFileWS.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }
    }
}
