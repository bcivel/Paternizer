/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.servlet;

import com.tibco.action.jms.tibjmsQueueSender;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author memiks
 */
public class ExecuteQJMS extends HttpServlet {

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
        /* TODO output your page here. You may use following sample code. */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ExecuteQJMS</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet ExecuteQJMS at " + request.getContextPath() + "</h1>");
        try {

            String server = request.getParameter("server");
            String user = request.getParameter("user");
            String password = request.getParameter("password");
            String queueName = request.getParameter("queueName");
            String fileName = request.getParameter("fileName");
            String filePath = request.getParameter("filePath");
            String lpar = request.getParameter("lpar");
            String job = request.getParameter("job");

            String[] args = new String[16];
            int i = 0;
            args[i] = "-server";
            i++;
            args[i] = server;
            i++;
            args[i] = "-user";
            i++;
            args[i] = user;
            i++;
            args[i] = "-password";
            i++;
            args[i] = password;
            i++;
            args[i] = "-queue";
            i++;
            args[i] = queueName;
            i++;
            args[i] = "-filename";
            i++;
            args[i] = fileName;
            i++;
            args[i] = "-filepath";
            i++;
            args[i] = filePath;
            i++;
            args[i] = "-lpar";
            i++;
            args[i] = lpar;
            i++;
            args[i] = "-job";
            i++;
            args[i] = job;
            i++;

            tibjmsQueueSender t = new tibjmsQueueSender(args);

            out.println("</body>");
            out.println("</html>");
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
