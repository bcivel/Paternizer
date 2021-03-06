/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.servlet;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.paternizer.constants.FileConstants;
import com.paternizer.service.ftp.GetFileFromFTP;
import com.paternizer.service.ftp.PublishFileOnFTP;
import com.paternizer.service.ftp.PublishFileOnSFTP;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author bcivel
 */
public class GetFromFtp extends HttpServlet {

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
        
        String sftp = request.getParameter("sftp");
        String host = request.getParameter("host");
        String port = request.getParameter("port");
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String folder = request.getParameter("folder");
        String fileName = request.getParameter("fileName");
        JSONObject result = new JSONObject();
        if (sftp != null) {
            GetFileFromFTP getFromFTP = new GetFileFromFTP();
            try {
                result = getFromFTP.getFileOnSFTP(host, port, user, password, folder, fileName);
            } catch (JSchException ex) {
                Logger.getLogger(GetFromFtp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SftpException ex) {
                Logger.getLogger(GetFromFtp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            GetFileFromFTP getFromFTP = new GetFileFromFTP();
            result = getFromFTP.getFile(host, port, user, password, folder, fileName);
        }
        
        response.setContentType("application/json");
        response.getWriter().print(result.toString());
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
