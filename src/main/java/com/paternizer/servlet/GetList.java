/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.servlet;

import com.paternizer.constants.FileConstants;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author bcivel
 */
public class GetList extends HttpServlet {

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
        try {

            String type = request.getParameter("type");

            JSONArray data = new JSONArray();
            File folder = new File(FileConstants.DOCUMENT_FOLDER + type + "/");
            String parent = "#";
            data = getListOfItem(folder, data, parent);

            response.setContentType("application/json");
            response.getWriter().print(data.toString());

        } finally {
            out.close();
        }
    }

    private JSONArray getListOfItem(File folder, JSONArray data, String parent) throws IOException {
        System.out.print(folder.getAbsolutePath());

        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                JSONObject obj = new JSONObject();
                if (listOfFiles[i].isFile()) {
                    obj.put("type", "file");
                    obj.put("id", folder.getAbsolutePath() + "/" + listOfFiles[i].getName());
                    obj.put("parent", parent);
                    obj.put("text", listOfFiles[i].getName());
                    obj.put("folder", folder.getAbsolutePath());
                    obj.put("name", listOfFiles[i].getName());
                    obj.put("icon", "jstree-file");
                } else if (listOfFiles[i].isDirectory()) {
                    obj.put("type", "folder");
                    obj.put("id", folder.getAbsolutePath() + "/" + listOfFiles[i].getName());
                    obj.put("parent", parent);
                    obj.put("text", listOfFiles[i].getName());
                    obj.put("folder", folder.getAbsolutePath());
                    obj.put("name", listOfFiles[i].getName());
                    getListOfItem(new File(folder.getAbsolutePath() + "/" + listOfFiles[i].getName()), data, folder.getAbsolutePath() + "/" + listOfFiles[i].getName());
                }
                data.put(obj);
            }
        }
        return data;
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
