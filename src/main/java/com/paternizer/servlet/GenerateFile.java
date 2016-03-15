/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.servlet;

import com.paternizer.constants.FileConstants;
import com.paternizer.service.TemplateService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.nio.cs.StandardCharsets;

/**
 *
 * @author memiks
 */
public class GenerateFile extends HttpServlet {

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
        String template = null;
        try {
            try {
                URL templateURL = new URL(request.getParameter("fileURL"));
                TemplateService templateService = new TemplateService();
                template = templateService.getFile(templateURL);
            } catch (MalformedURLException e) {
                System.err.println(" Unknow URL : " + request.getParameter("fileURL") + ".");
            } catch (IOException e) {
                System.err.println(e);
            }

            if (template != null) {
                Map<String, String[]> parametersName = request.getParameterMap();

                for (Map.Entry<String, String[]> entry : parametersName.entrySet()) {
                    String parameter = entry.getKey();
                    String[] values = entry.getValue();

                    System.err.println(" PARAMETER : " + parameter + " VALUE : " + values[0]);

                    template = template.replaceAll("#" + parameter + "#", values[0]);
                }

                if (request.getParameter("escapeDoubleQuote") != null) {
                    template = template.replace("\"", "\\\"");
                }

                // if no file name just display file instead of create in in PaternizerDocuments
                if (request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName").trim())) {
                    //TODO Send extension in parameter
                    File file = new File(FileConstants.DOCUMENT_FOLDER + "temp" + FileConstants.FOLDER_SEPARATOR + request.getParameter("fileName"));
//                    if (!file.exists()) {
//                            file.mkdirs();
//                   
                    try {
                        file.createNewFile();
                    } catch (Exception ex) {
                        out.println(ex.toString());
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(template.getBytes());
                    fileOutputStream.close();

                    if (request.getParameter("fromGui") == null) {
                        if (request.getParameter("printMessage") == null) {
                            out.println(FileConstants.DOCUMENT_URL + "temp" + FileConstants.FOLDER_SEPARATOR + request.getParameter("fileName"));
                        } else {
                            out.println("<xmp id=\"response\">"+new String(template.getBytes(), "UTF-8")+"</xmp>");
                        }
                    } else {
                        response.sendRedirect("./uploadOnFtp.jsp?fileURL=" + FileConstants.DOCUMENT_URL + "temp" + FileConstants.FOLDER_SEPARATOR + request.getParameter("fileName"));
                    }

                } else {
                    out.println(template);
                }
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
