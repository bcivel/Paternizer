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
import java.util.Enumeration;
import java.util.logging.Level;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bcivel
 */
public class ReadTibcoJmsQueue {

    private final Logger log = LoggerFactory.getLogger(ReadTibcoJmsQueue.class);

//    String serverUrl = "tcp://tib1prl4:15130";
//    String user = "admin";
//    String pass = "Flow@RE7T";
//    private String queueName = "APP.RFR.Publication.MediaRequest.001.TO.MEDIA-SERVEUR";
    QueueConnection connection = null;
    TibjmsAdmin admin = null;

    public JSONArray readQueue(String serverUrl, String user, String pass, String queueName) {

        JSONArray result = new JSONArray();

        try {

            QueueConnectionFactory qcf = new TibjmsQueueConnectionFactory(serverUrl);
            connection = qcf.createQueueConnection(user, pass);
            QueueSession session = connection.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

            javax.jms.Queue queue = session.createQueue(queueName);
//            QueueSender sender = session.createSender(queue);
//            TextMessage message = session.createTextMessage();
//            message.setText("totos");
//            sender.send(message);
//            System.out.print("inserted");

            admin = new TibjmsAdmin(serverUrl, user, pass);
            QueueInfo qInfo = admin.getQueue(queueName);
            long pendingMsgCount = qInfo.getPendingMessageCount();

            QueueBrowser qb = session.createBrowser(queue);
            Enumeration en = qb.getEnumeration();
int i = 0;
            while (en.hasMoreElements()) {
                i++;
                JSONObject obj = new JSONObject();
                TextMessage tm = (TextMessage) en.nextElement();
                obj.put("text", tm.getJMSMessageID());
                obj.put("content", tm.getText());
                obj.put("id", tm.getJMSMessageID());
                obj.put("priority", tm.getJMSPriority());
                obj.put("type", "file");
                obj.put("parent", "#");
                obj.put("folder", "#");
                obj.put("name", tm.getJMSMessageID());
                obj.put("icon", "jstree-file");
                result.put(obj);
                if (i == 10)
                    break;
            }

            connection.close();

            for (String toto : qInfo.getJNDINames()) {
                System.out.print("msg:" + toto);
            }

        } catch (TibjmsAdminException ex) {
            java.util.logging.Logger.getLogger(ReadTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
            result.put("Error :" + ex);
            return result;
        } catch (JMSException ex) {
            java.util.logging.Logger.getLogger(ReadTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
            result.put("Error :" + ex);
            return result;
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
                if (null != admin) {
                    admin.close();
                }
            } catch (TibjmsAdminException ex) {
                java.util.logging.Logger.getLogger(ReadTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JMSException ex) {
                java.util.logging.Logger.getLogger(ReadTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;

    }

    public JSONArray getMessage(String serverUrl, String user, String pass, String queueName) {

        JSONArray result = new JSONArray();

        try {

            QueueConnectionFactory qcf = new TibjmsQueueConnectionFactory(serverUrl);
            connection = qcf.createQueueConnection(user, pass);
            QueueSession session = connection.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

            javax.jms.Queue queue = session.createQueue(queueName);
//            QueueSender sender = session.createSender(queue);
//            TextMessage message = session.createTextMessage();
//            message.setText("totos");
//            sender.send(message);
//            System.out.print("inserted");

            admin = new TibjmsAdmin(serverUrl, user, pass);
            QueueInfo qInfo = admin.getQueue(queueName);
            long pendingMsgCount = qInfo.getPendingMessageCount();

            QueueBrowser qb = session.createBrowser(queue);
            Enumeration en = qb.getEnumeration();

            while (en.hasMoreElements()) {
                TextMessage tm = (TextMessage) en.nextElement();
                result.put(tm.getText());
            }

            connection.close();

            for (String toto : qInfo.getJNDINames()) {
                System.out.print("msg:" + toto);
            }

        } catch (TibjmsAdminException ex) {
            java.util.logging.Logger.getLogger(ReadTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
            result.put("Error :" + ex);
            return result;
        } catch (JMSException ex) {
            java.util.logging.Logger.getLogger(ReadTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
            result.put("Error :" + ex);
            return result;
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
                if (null != admin) {
                    admin.close();
                }
            } catch (TibjmsAdminException ex) {
                java.util.logging.Logger.getLogger(ReadTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JMSException ex) {
                java.util.logging.Logger.getLogger(ReadTibcoJmsQueue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;

    }
}
