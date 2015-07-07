/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.service;

import com.tibco.tibjms.TibjmsQueueConnectionFactory;
import com.tibco.tibjms.admin.QueueInfo;
import com.tibco.tibjms.admin.StatData;
import com.tibco.tibjms.admin.TibjmsAdmin;
import com.tibco.tibjms.admin.TibjmsAdminException;
import java.util.logging.Level;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bcivel
 */
public class ReadTibcoJmsQueue {

    private final Logger log = LoggerFactory.getLogger(ReadTibcoJmsQueue.class);

    String serverUrl = "tcp://tib1prl4:15130";
    String user = "admin";
    String pass = "Flow@RE7T";
    private String queueName = "APP.RFR.Publication.MediaRequest.001.TO.MEDIA-SERVEUR";

    QueueConnection connection = null;
    TibjmsAdmin admin = null;

    public void readQueue() {

        try {
            
            String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<media id=\"12345\" type=\"Photo\" source=\"TES\" xmlns=\"http://redoute.com/mediaserver/dto/request\">\n" +
"    <signature>12345678909876543212345678</signature>\n" +
"    <externalURL>http://toto/tutu.fr</externalURL>\n" +
"    <metadatas>\n" +
"        <metadata name=\"internalProductId\">511218|3662495053984</metadata>\n" +
"        <metadata name=\"ranking\">3</metadata>\n" +
"    </metadatas>\n" +
"    <services>\n" +
"        <service name=\"ICC\"/>\n" +
"        <service name=\"format\">\n" +
"            <parameter name=\"target\">jpg</parameter>\n" +
"        </service>\n" +
"        <service name=\"resize\">\n" +
"            <parameter name=\"size\">1200</parameter>\n" +
"            <parameter name=\"size\">641</parameter>\n" +
"            <parameter name=\"size\">800</parameter>\n" +
"            <parameter name=\"size\">400</parameter>\n" +
"            <parameter name=\"maxSizeOnly\">true</parameter>\n" +
"            <parameter name=\"originalSizeByDefault\">false</parameter>\n" +
"            <parameter name=\"square\">true</parameter>\n" +
"            <parameter name=\"background\">white</parameter>\n" +
"            <parameter name=\"format\">JPG</parameter>\n" +
"        </service>\n" +
"    </services>\n" +
"</media>";
            
QueueConnectionFactory qcf = new TibjmsQueueConnectionFactory(serverUrl);
            connection = qcf.createQueueConnection(user, pass);
            QueueSession session = connection.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

            javax.jms.Queue queue = session.createQueue(queueName);
            QueueSender sender = session.createSender(queue);

            TextMessage message = session.createTextMessage();
            message.setText(s);
            sender.send(message);
            
            connection.close();
            System.out.print("inserted");

            admin = new TibjmsAdmin(serverUrl, user, pass);
            QueueInfo qInfo = admin.getQueue(queueName);
            long pendingMsgCount = qInfo.getPendingMessageCount();
            StatData sd = qInfo.getInboundStatistics();
            long totalMsgs = sd.getTotalMessages();
            

            System.out.print("count:" + pendingMsgCount);
            System.out.print("msg:" + qInfo.getJNDINames().toString());
            

        } catch (TibjmsAdminException ex) {
            java.util.logging.Logger.getLogger(ReadTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            java.util.logging.Logger.getLogger(ReadTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
//                connection.close();
                admin.close();
            } catch (TibjmsAdminException ex) {
                java.util.logging.Logger.getLogger(ReadTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
