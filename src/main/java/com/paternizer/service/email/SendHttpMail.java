/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.service.email;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.mail.util.ByteArrayDataSource;
import org.apache.commons.mail.HtmlEmail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author bcivel
 */
public class SendHttpMail {

    public static void sendHtmlMail(String host, int port, String body, String subject, String from, String to, String pictureUrl, String picturePath) throws Exception {
        //System.out.println("--- START SEND MAIL ---");
        HtmlEmail email = new HtmlEmail();
        email.setSmtpPort(port);
        email.setDebug(false);
        email.setHostName(host);
        email.setFrom(from);
        //System.out.println("--- START ---");
        DateFormat timeFormat = new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss:SSS");

        List<BufferedImage> biList = new ArrayList();
        if (picturePath != null) {
            //System.out.println("--- CONNECTED TO ---" + picturePath);
            Document doc = Jsoup.connect(picturePath).get();
            //System.out.println("--- DOC ---" + doc);
            for (Element file : doc.select("a")) {
                //System.out.println(file.text());
                if (file.text().contains("jpg")) {
                    URL url = new URL(picturePath + "/" + file.text());
                    BufferedImage originalImage = ImageIO.read(url);
                    biList.add(originalImage);
                }
            }
        } else {
            URL url = new URL(pictureUrl);
            BufferedImage originalImage = ImageIO.read(url);
            biList.add(originalImage);
        }

        for (BufferedImage bi : biList) {
            Date timec = new Date();
            String curtime = timeFormat.format(timec).replace(":", "");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            DataSource pngdt = new ByteArrayDataSource(imageInByte, "image/jpeg");
            body += ("</br>");
            body += ("<img src=\"cid:") + (email.embed(pngdt, "Bam" + curtime + ".jpeg", "BamGraph" + curtime)) + ("\">");
            body += ("</br>");
        }
        email.setSubject(subject);
        email.setHtmlMsg(body);

        String[] destinataire = to.split(";");

        for (int i = 0; i < destinataire.length; i++) {
            String name;
            String emailaddress;
            if (destinataire[i].contains("<")) {
                String[] destinatairedata = destinataire[i].split("<");
                name = destinatairedata[0].trim();
                emailaddress = destinatairedata[1].replace(">", "").trim();
            } else {
                name = "";
                emailaddress = destinataire[i];
            }
            email.addTo(emailaddress, name);
        }

//        String[] copy = cc.split(";");
//
//        for (int i = 0; i < copy.length; i++) {
//            String namecc;
//            String emailaddresscc;
//            if (copy[i].contains("<")){
//            String[] copydata = copy[i].split("<");
//            namecc = copydata[0].trim();
//            emailaddresscc = copydata[1].replace(">", "").trim();
//            } else {
//            namecc = "";
//            emailaddresscc = copy[i];
//            }
//            email.addCc(emailaddresscc, namecc);
//        }
        email.setTLS(true);

        email.send();

    }
}
