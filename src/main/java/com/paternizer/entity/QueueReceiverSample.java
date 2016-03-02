/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.entity;

import javax.annotation.PostConstruct;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.stereotype.Component;

/**
 *
 * @author bcivel
 */
public class QueueReceiverSample {
    
    @PostConstruct
    public void init(){

        QueueConnectionFactory queueConnectionFactory = null;
        Queue ringQueue = null;

        try {

            Context jndiContext = new InitialContext();
            queueConnectionFactory = 
              (QueueConnectionFactory) 
                jndiContext.lookup("QueueConnectionFactory");
            ringQueue = (Queue) jndiContext.lookup("RingQueue");

        } catch (NamingException nameEx) {
          System.out.println("Naming Exception: " + nameEx.toString());
        }

        QueueConnection queueConnection = null;

        try {

            queueConnection = 
              queueConnectionFactory.createQueueConnection();
            QueueSession queueSession = 
              queueConnection.createQueueSession(
                false, Session.AUTO_ACKNOWLEDGE);
            QueueReceiver queueReceiver = 
              queueSession.createReceiver(ringQueue);
            queueConnection.start();

            TextMessage textMessage = null;

            while (true) {
                textMessage = (TextMessage) queueReceiver.receive(1);
                System.out.println(" receiving line " + 
                                   " : " + textMessage.getText());
                if (textMessage.getText().equals("end of message")) {
                    break;
                }
            } 
        
            queueConnection.close();
            
            System.out.println(" ring receiver closed");
            
        } catch (javax.jms.JMSException jmsEx) {
            System.out.println("JMS Exception: " + jmsEx.toString());
        } finally { if (queueConnection != null)
            {   try
                { queueConnection.close();
                } catch (javax.jms.JMSException jmse) {} 
            } 
        }
    }
}
