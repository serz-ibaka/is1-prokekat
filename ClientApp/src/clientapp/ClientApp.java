/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Sergej
 */
public class ClientApp {
    
    private static void kreirajMesto(String naziv, int postanskiBroj) {
        try {
            
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
    
    private static void kreirajFilijalu(String naziv, String adresa, int idMes) {
        try {
            
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
    
    private static void kreirajKomitenta(String naziv, String adresa, int idMes) {
        try {
            
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
    
    private static void promeniSediste(int idKom, int idMes, String adresa) {
        try {
            String parameter = "" + idMes + "###" + adresa;
            URL url = new URL("http://localhost:8080/CentralniServer/resources/komitent/" + idKom);
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
    
    private static void kreirajRacun(float dozvoljeniMinus, int idKom, int idMes) {
        try {
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
    
    private static void zatvoriRacun(int idRac) {
        try {
            String parameter = "" + idRac;
            URL url = new URL("http://localhost:8080/CentralniServer/resources/racun");
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
    
    private static void kreirajUplatu(int idRacNa, int idFil, float iznos, String svrha) {
        try {
            String parameter = "" + idRacNa + "###" + idFil + "###" + iznos + "###" + svrha;
            URL url = new URL("http://localhost:8080/CentralniServer/resources/transakcija/uplata");
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
    
    private static void kreirajIsplatu(int idRacSa, int idFil, float iznos, String svrha) {
        try {
            String parameter = "" + idRacSa + "###" + idFil + "###" + iznos + "###" + svrha;
            URL url = new URL("http://localhost:8080/CentralniServer/resources/transakcija/isplata");
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
    
    private static void kreirajPrenos(int idRacSa, int idRacNa, float iznos, String svrha) {
        try {
            String parameter = "" + idRacSa + "###" + idRacNa + "###" + iznos + "###" + svrha;
            URL url = new URL("http://localhost:8080/CentralniServer/resources/transakcija/prenos");
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
    
    private static String dohvatiMesta() {
        try {
            URL url = new URL("http://localhost:8080/CentralniServer/resources/mesto");
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
            
            StringBuilder sb = new StringBuilder();
            StringTokenizer st = new StringTokenizer(content.toString(), "@@@");
            sb.append("IdKom       Naziv mesta   Postanski broj \n");
            while(st.hasMoreTokens()) {
                StringTokenizer stt = new StringTokenizer(st.nextToken(), "###");
                sb.append(String.format("%5s", stt.nextToken()));
                sb.append(String.format("   %15s", stt.nextToken()));
                sb.append(String.format("   %14s ", stt.nextToken()));
                sb.append("\n");
            }
            
            return sb.toString();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    private static String dohvatiFilijale() {
        try {
            URL url = new URL("http://localhost:8080/CentralniServer/resources/filijala");
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
            
            StringBuilder sb = new StringBuilder();
            StringTokenizer st = new StringTokenizer(content.toString(), "@@@");
            sb.append("IdFil    Naziv filijale                      Adresa             Mesto \n");
            while(st.hasMoreTokens()) {
                StringTokenizer stt = new StringTokenizer(st.nextToken(), "###");
                sb.append(String.format("%5s", stt.nextToken()));
                sb.append(String.format("   %15s", stt.nextToken()));
                sb.append(String.format("   %25s", stt.nextToken()));
                sb.append(String.format("   %15s ", stt.nextToken()));
                sb.append("\n");
            }
            
            return sb.toString();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    private static String dohvatiKomitente() {
        try {
            URL url = new URL("http://localhost:8080/CentralniServer/resources/komitent");
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
            
            StringBuilder sb = new StringBuilder();
            StringTokenizer st = new StringTokenizer(content.toString(), "@@@");
            sb.append("IdKom   Naziv komitenta                      Adresa             Mesto \n");
            while(st.hasMoreTokens()) {
                StringTokenizer stt = new StringTokenizer(st.nextToken(), "###");
                sb.append(String.format("%5s", stt.nextToken()));
                sb.append(String.format("   %15s", stt.nextToken()));
                sb.append(String.format("   %25s", stt.nextToken()));
                sb.append(String.format("   %15s ", stt.nextToken()));
                sb.append("\n");
            }
            
            return sb.toString();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    private static String dohvatiRacuneKomitenta(int idKom) {
        try {
            URL url = new URL("http://localhost:8080/CentralniServer/resources/racun/" + idKom);
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
            
            StringBuilder sb = new StringBuilder();
            StringTokenizer st = new StringTokenizer(content.toString(), "@@@");
            sb.append("IdRac      Stanje   DozvMinus   Status                  Datum otvaranja   BrTrans         Komitent            Mesto \n");
            while(st.hasMoreTokens()) {
                StringTokenizer stt = new StringTokenizer(st.nextToken(), "###");
                sb.append(String.format("%5s", stt.nextToken()));
                sb.append(String.format("   %9s", stt.nextToken()));
                sb.append(String.format("   %9s", stt.nextToken()));
                sb.append(String.format("        %s", stt.nextToken()));
                sb.append(String.format("   %30s", stt.nextToken()));
                sb.append(String.format("       %3s", stt.nextToken()));
                sb.append(String.format("  %15s", stt.nextToken()));
                sb.append(String.format("  %15s ", stt.nextToken()));
                sb.append("\n");
            }
            
            return sb.toString();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    private static String dohvatiTransakcijeRacuna(int idRac) {
        try {
            URL url = new URL("http://localhost:8080/CentralniServer/resources/transakcija/" + idRac);
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
            
            StringBuilder sb = new StringBuilder();
            StringTokenizer st = new StringTokenizer(content.toString(), "@@@");
            sb.append("IdRac   Rbr       Iznos                 Datum obavljanja                  Svrha          Tip   RacunSa   RacunNa \n");
            while(st.hasMoreTokens()) {
                StringTokenizer stt = new StringTokenizer(st.nextToken(), "###");
                sb.append(String.format("%5s", stt.nextToken()));
                sb.append(String.format("   %3s", stt.nextToken()));
                sb.append(String.format("   %9s", stt.nextToken()));
                sb.append(String.format("   %30s", stt.nextToken()));
                sb.append(String.format("   %20s", stt.nextToken()));
                sb.append(String.format("   %10s", stt.nextToken()));
                sb.append(String.format("   %7s", stt.nextToken()));
                sb.append(String.format("   %7s ", stt.nextToken()));
//                sb.append(String.format("%4s", stt.nextToken()));
                sb.append("\n");
            }
            
            return sb.toString();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    private static String dohvatiBackup() {
        try{
            URL url = new URL("http://localhost:8080/CentralniServer/resources/backup");
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
            
            String str = content.toString();
            StringBuilder sb = new StringBuilder();
            StringTokenizer st = new StringTokenizer(str, "$$$");
            int i = 0;
            String arr[] = { "Mesto", "Filijala", "Komitent", "Racun", "Transakcija" };
            while(st.hasMoreTokens()) {
                sb.append(arr[i++]).append("\n");
                StringTokenizer stt = new StringTokenizer(st.nextToken(), "@@@");
                while(stt.hasMoreTokens()) {
                    StringTokenizer sttt = new StringTokenizer(stt.nextToken(), "###");
                    while(sttt.hasMoreTokens()) {
                        sb.append(sttt.nextToken() + "\t");
                    }
                    sb.append("\n");
                }
                sb.append("\n");
            }
            
            return sb.toString();
        } catch (IOException e) {}
        return "";
    }
    
    private static String dohvatiRazlike() {
        try{
            URL url = new URL("http://localhost:8080/CentralniServer/resources/backup/differences");
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
            
            StringBuilder sb = new StringBuilder();
            StringTokenizer st = new StringTokenizer(content.toString(), "#");
            while(st.hasMoreTokens()) {
                sb.append(st.nextToken()).append("\n");
            }
            
            return sb.toString();
        } catch (IOException e) {}
        return "";
    }
    
    JFrame frame = new JFrame();
    JPanel panel = new JPanel(new BorderLayout());
    JComboBox comboBox;
    
    
    public ClientApp() {
        
        String requests[] = { "Kreiraj mesto",
            "Kreiraj filijalu u mestu",
            "Kreiraj komitenta",
            "Promeni sediste komitenta",
            "Otvori racun",
            "Zatvori racun",
            "Kreiraj prenos",
            "Kreiraj uplatu",
            "Kreiraj isplatu",
            "Dohvati mesta",
            "Dohvati filijale",
            "Dohvati komitente",
            "Dohvati racune komitenta",
            "Dohvati transakcije racuna",
            "Dohvati rezervnu kopiju",
            "Dohvati razlike podataka i rezervne kopije"
        };
        
        comboBox = new JComboBox(requests);
        comboBox.setEditable(true);
        
        JPanel westPanel = new JPanel(new GridLayout(16, 1));
        westPanel.add(comboBox);
        
        JLabel labelIdMesta = new JLabel("Id mesta: ");
        JTextField textFieldIdMesta = new JTextField(4);
        JPanel panelIdMesta = new JPanel();
        panelIdMesta.add(labelIdMesta);
        panelIdMesta.add(textFieldIdMesta);
        JPanel panelIdMesta1 = new JPanel(new BorderLayout());
        panelIdMesta1.add(panelIdMesta, BorderLayout.WEST);
        westPanel.add(panelIdMesta1);
        
        JLabel labelNazivMesta = new JLabel("Naziv mesta: ");
        JTextField textFieldNazivMesta = new JTextField(15);
        JPanel panelNazivMesta = new JPanel();
        panelNazivMesta.add(labelNazivMesta);
        panelNazivMesta.add(textFieldNazivMesta);
        JPanel panelNazivMesta1 = new JPanel(new BorderLayout());
        panelNazivMesta1.add(panelNazivMesta, BorderLayout.WEST);
        westPanel.add(panelNazivMesta1);
        
        JLabel labelPostanskiBroj = new JLabel("Postanski broj: ");
        JTextField textFieldPostanskiBroj = new JTextField(6);
        JPanel panelPostanskiBroj = new JPanel();
        panelPostanskiBroj.add(labelPostanskiBroj);
        panelPostanskiBroj.add(textFieldPostanskiBroj);
        JPanel panelPostanskiBroj1 = new JPanel(new BorderLayout());
        panelPostanskiBroj1.add(panelPostanskiBroj, BorderLayout.WEST);
        westPanel.add(panelPostanskiBroj1);
        
        JLabel labelIdFilijale = new JLabel("Id filijale: ");
        JTextField textFieldIdFilijale = new JTextField(4);
        JPanel panelIdFilijale = new JPanel();
        panelIdFilijale.add(labelIdFilijale);
        panelIdFilijale.add(textFieldIdFilijale);
        JPanel panelIdFilijale1 = new JPanel(new BorderLayout());
        panelIdFilijale1.add(panelIdFilijale, BorderLayout.WEST);
        westPanel.add(panelIdFilijale1);
        
        JLabel labelNazivFilijale = new JLabel("Naziv filijale: ");
        JTextField textFieldNazivFilijale = new JTextField(15);
        JPanel panelNazivFilijale = new JPanel();
        panelNazivFilijale.add(labelNazivFilijale);
        panelNazivFilijale.add(textFieldNazivFilijale);
        JPanel panelNazivFilijale1 = new JPanel(new BorderLayout());
        panelNazivFilijale1.add(panelNazivFilijale, BorderLayout.WEST);
        westPanel.add(panelNazivFilijale1);
        
        JLabel labelAdresaFilijale = new JLabel("Adresa filijale: ");
        JTextField textFieldAdresaFilijale = new JTextField(20);
        JPanel panelAdresaFilijale = new JPanel();
        panelAdresaFilijale.add(labelAdresaFilijale);
        panelAdresaFilijale.add(textFieldAdresaFilijale);
        JPanel panelAdresaFilijale1 = new JPanel(new BorderLayout());
        panelAdresaFilijale1.add(panelAdresaFilijale, BorderLayout.WEST);
        westPanel.add(panelAdresaFilijale1);
        
        JLabel labelIdKomitenta = new JLabel("Id komitenta: ");
        JTextField textFieldIdKomitenta = new JTextField(4);
        JPanel panelIdKomitenta = new JPanel();
        panelIdKomitenta.add(labelIdKomitenta);
        panelIdKomitenta.add(textFieldIdKomitenta);
        JPanel panelIdKomitenta1 = new JPanel(new BorderLayout());
        panelIdKomitenta1.add(panelIdKomitenta, BorderLayout.WEST);
        westPanel.add(panelIdKomitenta1);
        
        JLabel labelNazivKomitenta = new JLabel("Naziv komitenta");
        JTextField textFieldNazivKomitenta = new JTextField(15);
        JPanel panelNazivKomitenta = new JPanel();
        panelNazivKomitenta.add(labelNazivKomitenta);
        panelNazivKomitenta.add(textFieldNazivKomitenta);
        JPanel panelNazivKomitenta1 = new JPanel(new BorderLayout());
        panelNazivKomitenta1.add(panelNazivKomitenta, BorderLayout.WEST);
        westPanel.add(panelNazivKomitenta1);
        
        JLabel labelAdresaKomitenta = new JLabel("Adresa komitenta: ");
        JTextField textFieldAdresaKomitenta = new JTextField(20);
        JPanel panelAdresaKomitenta = new JPanel();
        panelAdresaKomitenta.add(labelAdresaKomitenta);
        panelAdresaKomitenta.add(textFieldAdresaKomitenta);
        JPanel panelAdresaKomitenta1 = new JPanel(new BorderLayout());
        panelAdresaKomitenta1.add(panelAdresaKomitenta, BorderLayout.WEST);
        westPanel.add(panelAdresaKomitenta1);
        
        JLabel labelIdRacuna = new JLabel("Id racuna: ");
        JTextField textFieldIdRacuna = new JTextField(4);
        JPanel panelIdRacuna = new JPanel();
        panelIdRacuna.add(labelIdRacuna);
        panelIdRacuna.add(textFieldIdRacuna);
        JPanel panelIdRacuna1 = new JPanel(new BorderLayout());
        panelIdRacuna1.add(panelIdRacuna, BorderLayout.WEST);
        westPanel.add(panelIdRacuna1);
        
        JLabel labelDozvoljeniMinus = new JLabel("Dozvoljeni minus: ");
        JTextField textFieldDozvoljeniMinus = new JTextField(7);
        JPanel panelDozvoljeniMinus = new JPanel();
        panelDozvoljeniMinus.add(labelDozvoljeniMinus);
        panelDozvoljeniMinus.add(textFieldDozvoljeniMinus);
        JPanel panelDozvoljeniMinus1 = new JPanel(new BorderLayout());
        panelDozvoljeniMinus1.add(panelDozvoljeniMinus, BorderLayout.WEST);
        westPanel.add(panelDozvoljeniMinus1);
        
        JLabel labelIdRacunaSa = new JLabel("Id racuna sa: ");
        JTextField textFieldIdRacunaSa = new JTextField(4);
        JPanel panelIdRacunaSa = new JPanel();
        panelIdRacunaSa.add(labelIdRacunaSa);
        panelIdRacunaSa.add(textFieldIdRacunaSa);
        JPanel panelIdRacunaSa1 = new JPanel(new BorderLayout());
        panelIdRacunaSa1.add(panelIdRacunaSa, BorderLayout.WEST);
        westPanel.add(panelIdRacunaSa1);
        
        JLabel labelIdRacunaNa = new JLabel("Id racuna na: ");
        JTextField textFieldIdRacunaNa = new JTextField(4);
        JPanel panelIdRacunaNa = new JPanel();
        panelIdRacunaNa.add(labelIdRacunaNa);
        panelIdRacunaNa.add(textFieldIdRacunaNa);
        JPanel panelIdRacunaNa1 = new JPanel(new BorderLayout());
        panelIdRacunaNa1.add(panelIdRacunaNa, BorderLayout.WEST);
        westPanel.add(panelIdRacunaNa1);
        
        JLabel labelIznos = new JLabel("Iznos: ");
        JTextField textFieldIznos = new JTextField(7);
        JPanel panelIznos = new JPanel();
        panelIznos.add(labelIznos);
        panelIznos.add(textFieldIznos);
        JPanel panelIznos1 = new JPanel(new BorderLayout());
        panelIznos1.add(panelIznos, BorderLayout.WEST);
        westPanel.add(panelIznos1);
        
        JLabel labelSvrha = new JLabel("Svrha: ");
        JTextField textFieldSvrha = new JTextField(30);
        JPanel panelSvrha = new JPanel();
        panelSvrha.add(labelSvrha);
        panelSvrha.add(textFieldSvrha);
        JPanel panelSvrha1 = new JPanel(new BorderLayout());
        panelSvrha1.add(panelSvrha, BorderLayout.WEST);
        westPanel.add(panelSvrha1);
        
        comboBox.addItemListener(ie -> {
            textFieldIdMesta.setEditable(false);
            textFieldNazivMesta.setEditable(false);
            textFieldPostanskiBroj.setEditable(false);
            textFieldIdFilijale.setEditable(false);
            textFieldNazivFilijale.setEditable(false);
            textFieldAdresaFilijale.setEditable(false);
            textFieldIdKomitenta.setEditable(false);
            textFieldNazivKomitenta.setEditable(false);
            textFieldAdresaKomitenta.setEditable(false);
            textFieldIdRacuna.setEditable(false);
            textFieldDozvoljeniMinus.setEditable(false);
            textFieldIdRacunaSa.setEditable(false);
            textFieldIdRacunaNa.setEditable(false);
            textFieldIznos.setEditable(false);
            textFieldSvrha.setEditable(false);
            int selected = comboBox.getSelectedIndex();
            switch(selected) {
                case 0:
                    textFieldNazivMesta.setEditable(true);
                    textFieldPostanskiBroj.setEditable(true);
                    break;
                case 1:
                    textFieldNazivFilijale.setEditable(true);
                    textFieldAdresaFilijale.setEditable(true);
                    textFieldIdMesta.setEditable(true);
                    break;
                case 2:
                    textFieldNazivKomitenta.setEditable(true);
                    textFieldAdresaKomitenta.setEditable(true);
                    textFieldIdMesta.setEditable(true);
                    break;
                case 3:
                    textFieldIdMesta.setEditable(true);
                    textFieldAdresaKomitenta.setEditable(true);
                    textFieldIdKomitenta.setEditable(true);
                    break;
                case 4:
                    textFieldDozvoljeniMinus.setEditable(true);
                    textFieldIdKomitenta.setEditable(true);
                    textFieldIdMesta.setEditable(true);
                    break;
                case 5:
                    textFieldIdRacuna.setEditable(true);
                    break;
                case 6:
                    textFieldIznos.setEditable(true);
                    textFieldSvrha.setEditable(true);
                    textFieldIdRacunaSa.setEditable(true);
                    textFieldIdRacunaNa.setEditable(true);
                    break;
                case 7:
                    textFieldIznos.setEditable(true);
                    textFieldSvrha.setEditable(true);
                    textFieldIdRacunaNa.setEditable(true);
                    textFieldIdFilijale.setEditable(true);
                    break;
                case 8:
                    textFieldIznos.setEditable(true);
                    textFieldSvrha.setEditable(true);
                    textFieldIdRacunaSa.setEditable(true);
                    textFieldIdFilijale.setEditable(true);
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    textFieldIdKomitenta.setEditable(true);
                    break;
                case 13:
                    textFieldIdRacuna.setEditable(true);
                    break;
                case 14:
                    break;
                case 15:
                    break;
            }
        });
        comboBox.setSelectedIndex(0);
        
        JTextArea textArea = new JTextArea(30, 100);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JButton button = new JButton("Pokreni");
        
        button.addActionListener(ae -> {
            textArea.setText("");
            int selected = comboBox.getSelectedIndex();
            switch(selected + 1) {
                case 1:
                    String naziv = textFieldNazivMesta.getText();
                    int postanskiBroj = Integer.parseInt(textFieldPostanskiBroj.getText());
                    kreirajMesto(naziv, postanskiBroj);
                    break;
                case 2:
                    naziv = textFieldNazivFilijale.getText();
                    String adresa = textFieldAdresaFilijale.getText();
                    int idMesta = Integer.parseInt(textFieldIdMesta.getText());
                    kreirajFilijalu(naziv, adresa, idMesta);
                    break;
                case 3:
                    naziv = textFieldNazivKomitenta.getText();
                    adresa = textFieldAdresaKomitenta.getText();
                    idMesta = Integer.parseInt(textFieldIdMesta.getText());
                    kreirajKomitenta(naziv, adresa, idMesta);
                    break;
                case 4:
                    int idKomitenta = Integer.parseInt(textFieldIdKomitenta.getText());
                    adresa = textFieldAdresaKomitenta.getText();
                    idMesta = Integer.parseInt(textFieldIdMesta.getText());
                    promeniSediste(idKomitenta, idMesta, adresa);
                    break;
                case 5:
                    float dozvoljeniMinus = Float.parseFloat(textFieldDozvoljeniMinus.getText());
                    idKomitenta = Integer.parseInt(textFieldIdKomitenta.getText());
                    idMesta = Integer.parseInt(textFieldIdMesta.getText());
                    kreirajRacun(dozvoljeniMinus, idKomitenta, idMesta);
                    break;
                case 6:
                    int idRacuna = Integer.parseInt(textFieldIdRacuna.getText());
                    zatvoriRacun(idRacuna);
                    break;
                case 7:
                    int idRacunaSa = Integer.parseInt(textFieldIdRacunaSa.getText());
                    int idRacunaNa = Integer.parseInt(textFieldIdRacunaNa.getText());
                    float iznos = Float.parseFloat(textFieldIznos.getText());
                    String svrha = textFieldSvrha.getText();
                    kreirajPrenos(idRacunaSa, idRacunaNa, iznos, svrha);
                    break;
                case 8:
                    idRacunaNa = Integer.parseInt(textFieldIdRacunaNa.getText());
                    int idFilijale = Integer.parseInt(textFieldIdFilijale.getText());
                    iznos = Float.parseFloat(textFieldIznos.getText());
                    svrha = textFieldSvrha.getText();
                    kreirajUplatu(idRacunaNa, idFilijale, iznos, svrha);
                    break;
                case 9:
                    idRacunaSa = Integer.parseInt(textFieldIdRacunaSa.getText());
                    idFilijale = Integer.parseInt(textFieldIdFilijale.getText());
                    iznos = Float.parseFloat(textFieldIznos.getText());
                    svrha = textFieldSvrha.getText();
                    kreirajIsplatu(idRacunaSa, idFilijale, iznos, svrha);
                    break;
                case 10:
                    textArea.setText(dohvatiMesta());
                    break;
                case 11:
                    textArea.setText(dohvatiFilijale());
                    break;
                case 12:
                    textArea.setText(dohvatiKomitente());
                    break;
                case 13:
                    idKomitenta = Integer.parseInt(textFieldIdKomitenta.getText());
                    textArea.setText(dohvatiRacuneKomitenta(idKomitenta));
                    break;
                case 14:
                    idRacuna = Integer.parseInt(textFieldIdRacuna.getText());
                    textArea.setText(dohvatiTransakcijeRacuna(idRacuna));
                    break;
                case 15:
                    textArea.setText(dohvatiBackup());
                    break;
                case 16:
                    textArea.setText(dohvatiRazlike());
                    break;
            }
            
            textFieldAdresaFilijale.setText("");
            textFieldAdresaKomitenta.setText("");
            textFieldDozvoljeniMinus.setText("");
            textFieldIdFilijale.setText("");
            textFieldIdKomitenta.setText("");
            textFieldIdMesta.setText("");
            textFieldIdRacuna.setText("");
            textFieldIdRacunaNa.setText("");
            textFieldIdRacunaSa.setText("");
            textFieldIznos.setText("");
            textFieldNazivFilijale.setText("");
            textFieldNazivKomitenta.setText("");
            textFieldNazivMesta.setText("");
            textFieldPostanskiBroj.setText("");
            textFieldSvrha.setText("");
        });
        
        
        
//        JPanel eastPanel = new JPanel(new BorderLayout());
//        eastPanel.add(te)
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        JScrollPane sp = new JScrollPane(textArea);
        panel.add(sp, BorderLayout.EAST);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        panel.add(westPanel, BorderLayout.WEST);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setBounds(200, 200, 1600, 600);
        frame.add(panel);
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ClientApp();
    }
    
}
