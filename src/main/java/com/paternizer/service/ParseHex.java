/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 *
 * @author bcivel
 */
public class ParseHex {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        InputStreamReader rdr = new InputStreamReader(new FileInputStream("c:\\MN.SITDDE.ENTETE"), java.nio.charset.Charset.forName("IBM01147"));
        BufferedReader br = new BufferedReader(rdr); // to read a single line from the file
        String line; 
        StringBuilder sb = new StringBuilder();
         while((line = br.readLine()) != null) {
             System.out.println(new String(line.getBytes(),java.nio.charset.Charset.forName("ISO-8859-15")));
             sb.append(new String(line.getBytes(),java.nio.charset.Charset.forName("ISO-8859-15")));
          }

         File f=new File("C:\\Users\\bcivel\\Desktop\\toto.txt");
         
         if(!f.exists()){
            f.createNewFile();
            System.out.println("new file:" + f);
        }
        
            FileWriter fwriter = new FileWriter(f);
            BufferedWriter bwriter = new BufferedWriter(fwriter);
            bwriter.write(sb.toString());
            bwriter.close();
   
    }
}
