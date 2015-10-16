/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.xml.sax.SAXException;

/**
 *
 * @author bcivel
 */
public class FileInject {
    
    public SOAPMessage createSoapRequest(String envelope, String method) throws SOAPException, IOException, SAXException, ParserConfigurationException {
        //String unescapedEnvelope = HtmlUtils.htmlUnescape(envelope);
        MimeHeaders headers = new MimeHeaders();
        headers.addHeader("SOAPAction", method);
        headers.addHeader("Content-Type", SOAPConstants.SOAP_1_2_CONTENT_TYPE);

        InputStream input = new ByteArrayInputStream(envelope.getBytes("UTF-8"));
        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.DYNAMIC_SOAP_PROTOCOL);
        return messageFactory.createMessage(headers, input);
    }

    public String callSOAP(String attachmentUrl, String fileName, String servicePath, String vendorId) {
        String result = null;
        ByteArrayOutputStream out = null;
        //http://schemas.xmlsoap.org/soap/envelope/
        String envelope = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ns=\"http://Redcats/File/ESB-TIBCO/File/1.0/InjectFile/1.0\">"
                + "   <soap:Header/>"
                + "   <soap:Body>"
                + "      <ns:InjectFileRequest_1.0>"
                + "         <ns:EUserId>"+vendorId+"</ns:EUserId>"
                + "         <ns:ESystem>400</ns:ESystem>"
                + "         <ns:FlowName>MKP_Products</ns:FlowName>"
                + "         <ns:FlowVersion>002</ns:FlowVersion>"
                + "         <ns:FileName>" + fileName +"</ns:FileName>"
                + "         <ns:fileAttachedWithSoapMsg>true</ns:fileAttachedWithSoapMsg>"
                + "      </ns:InjectFileRequest_1.0>"
                + "   </soap:Body>"
                + "</soap:Envelope>";
        //String servicePath = "http://re7fonc.services.siege.red/Redcats/File/ESB-TIBCO/File/1.0";
//        String servicePath = "http://WIN-RV3BTGQQF19:11722/Redcats/File/ESB-TIBCO/File/1.0";
        String method = "InjectFile_1.0";

        SOAPConnectionFactory soapConnectionFactory;
        SOAPConnection soapConnection = null;
        try {
            //Initialize SOAP Connection
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();

            // Create SOAP Request
            SOAPMessage input = createSoapRequest(envelope, method);

            //Add attachment File if specified
//            URL url = new URL(attachmentUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            InputStream istream = connection.getInputStream();
            System.out.print(attachmentUrl);
            FileDataSource fds = new FileDataSource(attachmentUrl);
            DataHandler handler = new DataHandler(fds);
            AttachmentPart attachPart = input.createAttachmentPart(handler);
            attachPart.setContentType("text/xml; charset=UTF-8");
            attachPart.setContentId(handler.getName());
            attachPart.setMimeHeader("content-disposition", "attachment; filename="+handler.getName());
            input.addAttachmentPart(attachPart);

            // Call the WS
            SOAPMessage soapResponse = soapConnection.call(input, servicePath);
            out = new ByteArrayOutputStream();

            // Store the response in memory (Using the persistent ExecutionSOAPResponse object)
            soapResponse.writeTo(out);
            result = out.toString();

            System.out.print(result);
            
            //Log out
            Logger.getLogger(FileInject.class.getName()).log(Level.INFO, null, "CALL_RESPONSE : " + result);
            
            //Delete File
//            File file = new File(attachmentUrl);
//            file.delete();

        } catch (SOAPException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (ParserConfigurationException e) {
            System.out.println(e);
        } catch (SAXException e) {
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

        return result;
    }
}
