/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.servlet;

import com.paternizer.service.FileInject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.apache.commons.io.FileUtils;
import org.apache.mina.util.Base64;

/**
 *
 * @author bcivel
 */
public class CallWS extends HttpServlet {

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
        PrintWriter pw = response.getWriter();

        String result = null;
        ByteArrayOutputStream out = null;
        String envelopeFilePath = request.getParameter("envelopeFile");
        String method = request.getParameter("method");
        String servicePath = request.getParameter("servicePath");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // WS-Security header values
        if (envelopeFilePath == null || method == null || servicePath == null) {
            pw.println("This servlet is used to call WebService\n");
            pw.println("Parameters needed :\n");
            pw.println("envelopeFile: The Path of the envelope");
            pw.println("method: The nameof the method to call");
            pw.println("servicePath: WSDL");
        } else {
            SOAPConnectionFactory soapConnectionFactory;
            SOAPConnection soapConnection = null;
            try {
                File envelopeFile = new File(envelopeFilePath);
                String envelope = FileUtils.readFileToString(envelopeFile, "UTF-8");
                //Initialize SOAP Connection
                soapConnectionFactory = SOAPConnectionFactory.newInstance();
                soapConnection = soapConnectionFactory.createConnection();

                // Create SOAP Request
                MimeHeaders headers = new MimeHeaders();
                headers.addHeader("SOAPAction", method);
                headers.addHeader("Content-Type", SOAPConstants.SOAP_1_1_CONTENT_TYPE);
                
                if (username != null && password != null){
                String loginPassword = username + ":" + password;
                byte[] encodedString = Base64.encodeBase64(loginPassword.getBytes());
                String str = new String(encodedString, "UTF-8");
                headers.addHeader("Authorization", "Basic " + str);
                }
                
                InputStream inputStream = new ByteArrayInputStream(envelope.getBytes("UTF-8"));
                MessageFactory messageFactory = MessageFactory.newInstance();
                SOAPMessage input = messageFactory.createMessage(headers, inputStream);

                // Call the WS
                SOAPMessage soapResponse = soapConnection.call(input, servicePath);
                out = new ByteArrayOutputStream();
                
                // Store the response in memory (Using the persistent ExecutionSOAPResponse object)
                soapResponse.writeTo(out);
                result = out.toString();

                pw.print(result);

                //Log out
                Logger.getLogger(FileInject.class.getName()).log(Level.INFO, null, "CALL_RESPONSE : " + result);

                //Delete File
//            File file = new File(attachmentUrl);
//            file.delete();
            } catch (SOAPException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            } catch (Exception ex) {
                Logger.getLogger(FileInject.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (soapConnection != null) {
                        soapConnection.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (SOAPException ex) {
                    Logger.getLogger(FileInject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FileInject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
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
