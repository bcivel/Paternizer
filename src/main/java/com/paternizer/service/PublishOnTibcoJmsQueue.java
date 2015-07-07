/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.service;

import com.tibco.tibjms.TibjmsQueueConnectionFactory;
import java.util.logging.Level;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bcivel
 */
public class PublishOnTibcoJmsQueue {
   
    private final Logger log = LoggerFactory.getLogger(PublishOnTibcoJmsQueue.class);

    String serverUrl = "tcp://tib1prl4:15130";
    String user = "admin";
    String pass = "Flow@RE7T";
    private String queueName = "APP.RFR.Publication.MediaRequest.001.TO.MEDIA-SERVEUR";

    QueueConnection connection = null;

    public void sendXmlByJms(final String xml) {

        try {
            QueueConnectionFactory qcf = new TibjmsQueueConnectionFactory(serverUrl);
            connection = qcf.createQueueConnection(user, pass);
            QueueSession session = connection.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

            javax.jms.Queue queue = session.createQueue(queueName);
            QueueSender sender = session.createSender(queue);

            TextMessage message = session.createTextMessage();
            message.setText(xml);
            sender.send(message);
            
            connection.close();
            
        } catch (JMSException ex) {
            java.util.logging.Logger.getLogger(PublishOnTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.close();
            } catch (JMSException ex1) {
                java.util.logging.Logger.getLogger(PublishOnTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.close();
            } catch (JMSException ex) {
                java.util.logging.Logger.getLogger(PublishOnTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
