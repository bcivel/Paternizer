/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.mina.util.Base64;

/**
 *
 * @author bcivel
 */
public class ConvertString extends HttpServlet {

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
        String format = request.getParameter("format");
        String inputString = request.getParameter("inputString");
        String inputStringPath = request.getParameter("inputStringPath");
        if (format == null || inputString == null) {
                out.println("This servlet is used to compress files\n");
                out.println("Parameters needed :\n");
                out.println("inputString: String to convert\n");
                out.println("inputStringPath: Path to the String to convert\n");
                out.println("format: The format for the conversion (base64,)\n");
            }
        try {
            if ("base64".equals(format)){
                byte[] encodedBytes;
                if (inputStringPath==null){
            encodedBytes = Base64.encodeBase64(inputString.getBytes());
                } else {
            File file = new File(inputStringPath);
            String str = FileUtils.readFileToString(file, "UTF-8");
            encodedBytes =   Base64.encodeBase64(str.getBytes()); 
                }
    out.print("<response>");
    out.print(new String(encodedBytes, Charset.forName("UTF-8")));
    out.print("</response>");
    
            }
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
