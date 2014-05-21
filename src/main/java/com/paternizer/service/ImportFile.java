/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author bcivel
 */
public class ImportFile extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        
        //sur la page :
//        <form action="ImportFile" method="post" name="selectFile" enctype="multipart/form-data">    
//        <div style="float:right; border-style: solid; border-width: 1px; background-color: white ">
//        Add File : 
//        <input type="file" id="file" name="file" style="width:300px">
//        <input id="Load" name="Load" style="display:none" value="Y">
//        <input id="idNC" name="idNC" style="display:none" value="<%=ncid%>">
//        <input type="submit" value="Save Documents">
//        </div>
//    </form>
                
                
                
        if (ServletFileUpload.isMultipartContent(request)) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                String fileName = null;
                List items = upload.parseRequest(request);
                List items2 = items;
                Iterator iterator = items.iterator();
                Iterator iterator2 = items2.iterator();
                File uploadedFile = null;
                String idNC = "";
                
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();

                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        if (name.equals("idNC")) {
                            idNC = item.getString();
                            System.out.println(idNC);
                        } 
                    } 
                }
                
                
                while (iterator2.hasNext()) {
                    FileItem item = (FileItem) iterator2.next();

                    if (!item.isFormField()) {
                        fileName = item.getName();
                        System.out.print(item.getSize());
                    
                    String root = "D:\\CerberusDocuments\\redip\\";
                        File pathFile = new File(root + idNC);
                        if (!pathFile.exists()) {
                            pathFile.mkdirs();
                        }
                       
                    String fullPath = pathFile + "\\" + fileName;
                    OutputStream outputStream = new FileOutputStream(fullPath);
                    InputStream inputStream = item.getInputStream();
                                        
                    int readBytes = 0;
                    byte[] buffer = new byte[10000];
                    while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
                         outputStream.write(buffer, 0, readBytes);
                    }
                    outputStream.close();
                    inputStream.close();
                    
                }
                }
                
//                ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
//                IQualityNonconformitiesDocService nonconformitiesDocService = appContext.getBean(IQualityNonconformitiesDocService.class);
//        
//                
//            QualityNonconformitiesDoc ncDoc = new QualityNonconformitiesDoc();
//            ncDoc.setIdQualityNonconformities(Integer.valueOf(idNC));
//            ncDoc.setLinkToDoc(fileName);
            
//            String addLinktodoc = nonconformitiesDocService.addNonconformityDoc(ncDoc);
              
            response.sendRedirect("qualitynonconformitydetails.jsp?ncid="+idNC);
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    }
}