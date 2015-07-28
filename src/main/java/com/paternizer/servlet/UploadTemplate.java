/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.servlet;

import com.paternizer.constants.FileConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author memiks
 */
public class UploadTemplate extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (ServletFileUpload.isMultipartContent(request)) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                String fileName = null;
                List items = upload.parseRequest(request);
                List items2 = items;
                Iterator iterator = items.iterator();
                Iterator iterator2 = items2.iterator();
                String idNC = "";
                String path = "/";

                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();

                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        if (name.equals("template")) {
                            idNC = item.getString();
                        } else if (name.equals("path")){
                            path = item.getString();
                        }
                    }
                }

                while (iterator2.hasNext()) {
                    FileItem item = (FileItem) iterator2.next();

                    if (!item.isFormField()) {
                        fileName = item.getName();
                        String root = FileConstants.DOCUMENT_FOLDER + "templates" + path;
                        File pathFile = new File(root + idNC);
                        if (!pathFile.exists()) {
                            pathFile.mkdirs();
                        }

                        String fullPath = pathFile + FileConstants.FOLDER_SEPARATOR + fileName;
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
                out.println("File uploaded.");
            } catch (FileUploadException ex) {
                out.println("An error occured File is not uploaded.");
                Logger.getLogger(UploadTemplate.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                out.close();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
