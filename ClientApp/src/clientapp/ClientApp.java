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
            switch(reqNum) {
                case 10:
                    url = new URL("http://localhost:8080/CentralniServer/resources/mesto");
                    break;
                case 11:
                    url = new URL("http://localhost:8080/CentralniServer/resources/filijala");
                    break;
                case 12:
                    url = new URL("http://localhost:8080/CentralniServer/resources/komitent");
                    break;
                case 13:
                    url = new URL("http://localhost:8080/CentralniServer/resources/racun/1");
                    break;
                case 14:
                    url = new URL("http://localhost:8080/CentralniServer/resources/transakcija/1");
                    break;
                default:
                    return;
            }
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
            
            StringTokenizer st = new StringTokenizer(content.toString(), "@@@");
            while(st.hasMoreTokens()) {
                StringTokenizer stt = new StringTokenizer(st.nextToken(), "###");
                while(stt.hasMoreTokens()) {
                    System.out.print(stt.nextToken() + "\t");                    
                }
                System.out.println();
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
    
    private static void kreirajKomitenta() {
        try {
            String naziv = "Petar Nikolic";
            String adresa = "Korzo 15";
            int idMes = 5;
            
            String parameter = naziv + "###" + adresa + "###" + idMes;
            URL url = new URL("http://localhost:8080/CentralniServer/resources/komitent");
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
    
    private static void promeniSediste() {
        try {
            int idMes = 2;
            String parameter = "" + idMes;
            URL url = new URL("http://localhost:8080/CentralniServer/resources/komitent/11");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
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
    
    private static void kreirajRacun() {
        try {
            float dozvoljeniMinus = 50000;
            int idKom = 1;
            int idMes = 1;
            String parameter = "" + dozvoljeniMinus + "###" + idKom + "###" + idMes;
            URL url = new URL("http://localhost:8080/CentralniServer/resources/racun");
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
        kreirajRacun();
    }
    
}
