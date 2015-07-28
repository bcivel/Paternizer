/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.servlet;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.paternizer.service.PublishFileOnFTP;
import com.paternizer.service.PublishFileOnSFTP;
import com.paternizer.service.PublishOnTibcoJmsQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author bcivel
 */
public class PublishOnQJMS extends HttpServlet {

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

        String host = request.getParameter("host");
        String port = request.getParameter("port");
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String queueName = request.getParameter("queueName");

        if (request.getParameterValues("fileName") != null) {
            String[] fileNames = request.getParameterValues("fileName");
            List<File> fileList = new ArrayList<File>();
            for (int i = 0; i < fileNames.length; i++) {
                try {
                    String fileName = fileNames[i];
                    File file = new File(fileName);
                    fileList.add(file);
                } finally {
                    out.close();
                }

            }
            for (File f : fileList){
            String str = FileUtils.readFileToString(f, "UTF-8");
            PublishOnTibcoJmsQueue publishOnTibcoJmsQueue = new PublishOnTibcoJmsQueue();
            publishOnTibcoJmsQueue.sendXmlByJms(host, port, user, port, queueName, str);
            out.println(" Message : " + f.getName() + " Sent<br>");
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
