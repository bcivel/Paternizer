/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
                URL templateURL = new URL(request.getParameter("fileName"));
                template = getFile(templateURL);
            } catch (MalformedURLException e) {
                System.err.println(" Unknow URL : " + request.getParameter("fileName") + ".");
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

                out.println(template);
            }
        } finally {
            out.close();
        }
    }

    public String getFile(URL u) throws IOException {
        URLConnection uc = u.openConnection();
        int FileLenght = uc.getContentLength();
        if (FileLenght == -1) {
            throw new IOException("Unable to read file.");
        }

        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new InputStreamReader(uc.getInputStream()));

        String line;
        StringBuilder template = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.length() == 0) {
                break;
            }
            template.append(line);
        }

        return template.toString();
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
