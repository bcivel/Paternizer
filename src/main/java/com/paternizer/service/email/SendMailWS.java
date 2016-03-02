/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.service.email;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 *
 * @author bcivel
 */
@WebService
@SOAPBinding(style = Style.RPC)
public class SendMailWS {

    public void sendHtmlMail(
            @WebParam(name = "host") String host,
            @WebParam(name = "port") Integer port,
            @WebParam(name = "subject") String subject,
            @WebParam(name = "from") String from,
            @WebParam(name = "to") String to,
            @WebParam(name = "pictureUrl") String pictureUrl,
            @WebParam(name = "picturePath") String picturePath,
            @WebParam(name = "body") String body) {

        SendHttpMail shm = new SendHttpMail();
        try {
            shm.sendHtmlMail(host, port, body, subject, from, to, pictureUrl, picturePath);
        } catch (Exception ex) {
            Logger.getLogger(SendMailWS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
