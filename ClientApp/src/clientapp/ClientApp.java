/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergej
 */
public class ClientApp {
    
    private static void request(int reqNum) {
        try {
            URL url = null;
            if(reqNum == 10) url = new URL("http://localhost:8080/CentralniServer/resources/mesto");
            if(reqNum == 11) url = new URL("http://localhost:8080/CentralniServer/resources/filijala");
            if(reqNum == 12) url = new URL("http://localhost:8080/CentralniServer/resources/komitent");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            
            int status = con.getResponseCode();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            
            StringTokenizer st = new StringTokenizer(content.toString(), "###");
            while(st.hasMoreTokens()) {
                System.out.println(st.nextToken());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void kreirajMesto() {
        try {
            String naziv = "Zrenjanin";
            int postanskiBroj = 23000;
            
            String parameter = naziv + "###" + postanskiBroj;
            URL url = new URL("http://localhost:8080/CentralniServer/resources/mesto");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(parameter);
            out.flush();
            out.close();
            
            con.getResponseCode();
        } catch (IOException ex) {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void kreirajFilijalu() {
        try {
            String naziv = "Addiko banka";
            String adresa = "Korzo 15";
            int idMes = 5;
            
            String parameter = naziv + "###" + adresa + "###" + idMes;
            URL url = new URL("http://localhost:8080/CentralniServer/resources/filijala");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(parameter);
            out.flush();
            out.close();
            
            con.getResponseCode();
        } catch (IOException ex) {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        request(10);
        kreirajMesto();
        request(10);
        
        request(11);
        System.out.println();
        kreirajFilijalu();
        request(11);
    }
    
}
