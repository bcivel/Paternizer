/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.servlet;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author bcivel
 */
public class SendFromESBToMaxxing extends HttpServlet {

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
            throws ServletException, IOException, JSchException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            //Connect to ESB FTP
            String srchost = request.getParameter("srchost");
            String srcport = request.getParameter("srcport");
            String srcuser = request.getParameter("srcuser");
            String srcpass = request.getParameter("srcpass");
            String srcfolder = request.getParameter("srcfolder");
            String desthost = request.getParameter("desthost");
            String destport = request.getParameter("destport");
            String destuser = request.getParameter("destuser");
            String destpass = request.getParameter("destpass");
            String destfolder = request.getParameter("destfolder");
            String[] fileNames = request.getParameterValues("fileName");
            String dwr = request.getParameter("delete");
            boolean takeAll = false;
            boolean deleteWhenRed = false;
            if (fileNames==null){
                takeAll = true;
            }
            if (dwr!=null){
                deleteWhenRed = true;
            }
            List<String> fileToTreat = new ArrayList();
            
            JSch jsch = new JSch();
            
            Session session = jsch.getSession(srcuser, srchost);
            session.setPassword(srcpass);

            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            if (!session.isConnected()) {
                System.out.print("session not connected");
            }

            // Initializing a channel
            Channel channel = session.openChannel("sftp");
            channel.connect();

            if (!channel.isConnected()) {
                System.out.print("channel not connected");
            }
            ChannelSftp csftp = (ChannelSftp) channel;

            // Positionement sur le bon repertoire
            csftp.cd(srcfolder);
            
            if (takeAll){
            Vector<ChannelSftp.LsEntry> entries = csftp.ls("*.*");
                for (ChannelSftp.LsEntry entry : entries) {
                    fileToTreat.add(entry.getFilename());
                    }
            } else {
            fileToTreat = Arrays.asList(fileNames);
            }

            //Connect to MAXXING FTP
            FTPClient client = new FTPClient();
            client.connect(desthost, Integer.valueOf(destport));
            client.login(destuser, destpass);

            client.setFileType(client.BINARY_FILE_TYPE);
            client.setFileTransferMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);

            // Positionement sur le bon repertoire
            if (client.changeWorkingDirectory(destfolder)) {
            }

            for (int i = 0; i < fileToTreat.size(); i++) {
                String fileName = fileToTreat.get(i);

                InputStream is = csftp.get(fileName);

                if (client.storeFile(fileName, is)) {
                    System.err.println(" FILE " + fileName + " SAVED");
                    if (deleteWhenRed){
                    csftp.rm(fileName);
                    }
                    // Upload OK
                } else {
                    System.err.println(" FILE " + fileName + " NOT SAVED !!! " + client.getStatus());
                    // Erreur
                }

            }

            if (csftp.isConnected()) {
                csftp.disconnect();
            }

            if (channel.isConnected()) {
                channel.disconnect();
            }

            if (session.isConnected()) {
                session.disconnect();
            }

            if (client.isConnected()) {
                client.disconnect();
            }

        } catch (JSchException ex) {
            Logger.getLogger(SendFromESBToMaxxing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SftpException ex) {
            Logger.getLogger(SendFromESBToMaxxing.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
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
        try {
            processRequest(request, response);
        } catch (JSchException ex) {
            Logger.getLogger(SendFromESBToMaxxing.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (JSchException ex) {
            Logger.getLogger(SendFromESBToMaxxing.class.getName()).log(Level.SEVERE, null, ex);
        }
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
