/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.servlet;

import com.paternizer.service.ReadTibcoJmsQueue;
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
public class ReadJmsQueue extends HttpServlet {

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
            String queueName = request.getParameter("queueName")==null?"":request.getParameter("queueName");
            String host = request.getParameter("host")==null?"":request.getParameter("host");
            String port = request.getParameter("port")==null?"":request.getParameter("port");
            String user = request.getParameter("user")==null?"":request.getParameter("user");
            String pass = request.getParameter("password")==null?"":request.getParameter("password");
            String serverUrl = "tcp://"+host+":"+port;
//String serverUrl = "tcp://tib1prl4:15130";
//    String user = "admin";
//    String pass = "Flow@RE7T";
//    private String queueName = "APP.RFR.Publication.MediaRequest.001.TO.MEDIA-SERVEUR";
//localhost:8080/Paternizer/ReadJmsQueue?queueName=APP.RFR.Publication.MediaRequest.001.TO.MEDIA-SERVEUR&host=tib1prl4&port=15130&user=admin&pass=Flow@RE7T

            if (queueName.isEmpty()||host.isEmpty()||port.isEmpty()||null==user||null==pass){
            out.print("<h3>These Parameters are mandatory :</h3><ul><li>queueName</li><li>host</li><li>port</li><li>user</li><li>password</li></ul>");
            }else{
            ReadTibcoJmsQueue rtjq = new ReadTibcoJmsQueue();
            JSONArray data = rtjq.readQueue(serverUrl, user, pass, queueName);
            
//            JSONArray data = new JSONArray();
//            JSONObject obj = new JSONObject();
//            obj.put("text", "toto");
//             obj.put("content", "<xml>toto tutu</xml>");
//                obj.put("id", "12345");
//                obj.put("priority", "P1");
//                obj.put("type", "file");
//                obj.put("parent", "#");
//                obj.put("folder", "#");
//                obj.put("name", "12345");
//                obj.put("icon", "jstree-file");
//                data.put(obj);
//                obj = new JSONObject();
//            obj.put("text", "tutu");
//             obj.put("content", "<xml>toto tutu tit tetet</xml>");
//                obj.put("id", "123456");
//                obj.put("priority", "P1");
//                obj.put("type", "file");
//                obj.put("parent", "#");
//                obj.put("folder", "#");
//                obj.put("name", "123456");
//                obj.put("icon", "jstree-file");
//                data.put(obj);
                
            response.setContentType("application/json");
        response.getWriter().print(data.toString());
            }
        } catch(Exception ex) {
            out.print("Exception found : " + ex);
        }
        finally {
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
