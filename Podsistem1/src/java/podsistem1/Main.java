/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jparesources.Filijala;
import jparesources.Komitent;
import jparesources.Mesto;

/**
 *
 * @author Sergej
 */
public class Main {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public static ConnectionFactory cf;
    
    @Resource(lookup = "responseTopic")
    public static Topic responseTopic;
    
    @Resource(lookup = "requestTopic")
    public static Topic requestTopic;
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");;
    private static EntityManager em = emf.createEntityManager();
    
    private static JMSContext context;
    private static JMSProducer producer;
    
    private static JMSContext getContext() {
        if(context == null) {
            context = cf.createContext();
        }
        return context;
    }
    
    private static JMSProducer getProducer() {
        if(context == null) {
            context = cf.createContext();
        }
        if(producer == null) {
            producer = context.createProducer();
        }
        return producer;
    }
        
    public static void close() {
        em.close();
        emf.close();
    }
    
    
    private static void request1(String parameter) {
        StringTokenizer st = new StringTokenizer(parameter, "###");
        String naziv = st.nextToken();
        int postanskiBroj = Integer.parseInt(st.nextToken());
        
        EntityTransaction et = em.getTransaction();
        et.begin();
        Mesto mesto = new Mesto();
        mesto.setNaziv(naziv);
        mesto.setPostanskibroj(postanskiBroj);
        em.persist(mesto);
        et.commit();
        
        parameter = mesto.getIdmes() + "###" + parameter;
        ObjectMessage objMsg = getContext().createObjectMessage();
       
        try {
            objMsg.setIntProperty("request", 21);
            objMsg.setStringProperty("parameter", parameter); // id, naziv, broj
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        getProducer().send(requestTopic, objMsg);
        
    }
    
    private static void request2(String parameter) {
        StringTokenizer st = new StringTokenizer(parameter, "###");
        String naziv = st.nextToken();
        String adresa = st.nextToken();
        int idMes = Integer.parseInt(st.nextToken());
        
        Mesto mesto;
        try {
            mesto = em.createNamedQuery("Mesto.findByIdmes", Mesto.class).setParameter("idmes", idMes).getSingleResult();
        } catch(Exception e) {
            // mesto ne postoji
            return;
        }
        EntityTransaction et = em.getTransaction();
        et.begin();
        Filijala filijala = new Filijala();
        filijala.setNaziv(naziv);
        filijala.setAdresa(adresa);
        filijala.setIdmes(mesto);
        em.persist(filijala);
        et.commit();
        
        parameter = filijala.getIdfil() + "###" + parameter;
        ObjectMessage objMsg = getContext().createObjectMessage();
       
        try {
            objMsg.setIntProperty("request", 22);
            objMsg.setStringProperty("parameter", parameter); // id, naziv, adresa, mesto
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        getProducer().send(requestTopic, objMsg);
    }
    
    private static void request3(String parameter) {
        StringTokenizer st = new StringTokenizer(parameter, "###");
        String naziv = st.nextToken();
        String adresa = st.nextToken();
        int idMes = Integer.parseInt(st.nextToken());
        
        Mesto mesto;
        try {
            mesto = em.createNamedQuery("Mesto.findByIdmes", Mesto.class).setParameter("idmes", idMes).getSingleResult();
        } catch(Exception e) {
            // mesto ne postoji
            return;
        }
        EntityTransaction et = em.getTransaction();
        et.begin();
        Komitent komitent = new Komitent();
        komitent.setNaziv(naziv);
        komitent.setAdresa(adresa);
        komitent.setIdmes(mesto);
        em.persist(komitent);
        et.commit();
        
        parameter = komitent.getIdkom() + "###" + parameter;
        ObjectMessage objMsg = getContext().createObjectMessage();
       
        try {
            objMsg.setIntProperty("request", 23);
            objMsg.setStringProperty("parameter", parameter); // id, naziv, adresa, idmes
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        getProducer().send(requestTopic, objMsg);
    }
    
    private static void request4(String parameter) {
        StringTokenizer st = new StringTokenizer(parameter, "###");
        int idKom = Integer.parseInt(st.nextToken());
        int idMes = Integer.parseInt(st.nextToken());
        String adresa = st.nextToken();
        
        Komitent komitent;
        Mesto mesto;
        try {
            komitent = em.createNamedQuery("Komitent.findByIdkom", Komitent.class)
                .setParameter("idkom", idKom).getSingleResult();
            mesto = em.createNamedQuery("Mesto.findByIdmes", Mesto.class)
                .setParameter("idmes", idMes).getSingleResult();
        } catch(Exception e) {
            // ne postoji komitent ili mesto
            return;
        }
        EntityTransaction et = em.getTransaction();
        et.begin();
        komitent.setIdmes(mesto);
        komitent.setAdresa(adresa);
        em.persist(komitent);
        et.commit();
        
        parameter = idKom + "###" + idMes + "###" + adresa;
        ObjectMessage objMsg = getContext().createObjectMessage();
       
        try {
            objMsg.setIntProperty("request", 24);
            objMsg.setStringProperty("parameter", parameter); // id, naziv, broj, idmes
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        getProducer().send(requestTopic, objMsg);
    }
    
    private static void request10() {
        try {
            
            List<Mesto> listaMesta = em.createNamedQuery("Mesto.findAll").getResultList();
            StringBuilder sb = new StringBuilder();
            sb.append("Id Mesta###Naziv###Postanski Broj");
            for(Mesto mesto : listaMesta) {
                sb.append("@@@").append(mesto.getIdmes())
                    .append("###").append(mesto.getNaziv())
                    .append("###").append(mesto.getPostanskibroj());
            }
            TextMessage txtMsg = getContext().createTextMessage(sb.toString());
            txtMsg.setIntProperty("request", 10);
            getProducer().send(responseTopic, txtMsg);
        } catch (JMSException ex) {}
    }
    
    private static void request11() {
        try {
            List<Filijala> listaFilijala = em.createNamedQuery("Filijala.findAll").getResultList();
            StringBuilder sb = new StringBuilder();
            sb.append("Id Filijale###Naziv###Adresa###Mesto");
            for(Filijala filijala : listaFilijala) {
                sb.append("@@@").append(filijala.getIdfil())
                    .append("###").append(filijala.getNaziv())
                    .append("###").append(filijala.getAdresa())
                    .append("###").append(filijala.getIdmes().getNaziv());
            }
            TextMessage txtMsg = getContext().createTextMessage(sb.toString());
            txtMsg.setIntProperty("request", 11);
            getProducer().send(responseTopic, txtMsg);
        } catch (JMSException ex) {}
    }
    
    private static void request12() {
        try {
            
            List<Komitent> listaKomitenata = em.createNamedQuery("Komitent.findAll").getResultList();
            StringBuilder sb = new StringBuilder();
            sb.append("Id Komitenta###Naziv###Adresa###Mesto");
            for(Komitent komitent : listaKomitenata) {
                sb.append("@@@").append(komitent.getIdkom())
                    .append("###").append(komitent.getNaziv())
                    .append("###").append(komitent.getAdresa())
                    .append("###").append(komitent.getIdmes().getNaziv());
            }
            TextMessage txtMsg = getContext().createTextMessage(sb.toString());
            txtMsg.setIntProperty("request", 12);
            getProducer().send(responseTopic, txtMsg);
        } catch (JMSException ex) {}
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        JMSContext context = cf.createContext();
        JMSConsumer consumer = getContext().createConsumer(requestTopic, 
            "request=1 OR request=2 OR request=3 OR request=4 OR request=10 OR request=11 OR request=12", false);
        while(true) {
            try {
                ObjectMessage objMsg = (ObjectMessage) consumer.receive();
                switch(objMsg.getIntProperty("request")) {
                    case 1:
                        request1((String)objMsg.getObject());
                        break;
                    case 2:
                        request2((String)objMsg.getObject());
                        break;
                    case 3:
                        request3((String)objMsg.getObject());
                        break;
                    case 4:
                        request4((String)objMsg.getObject());
                        break;
                    case 10:
                        request10();
                        break;
                    case 11:
                        request11();
                        break;
                    case 12:
                        request12();
                        break;
                }
            } catch (JMSException ex) {}
        }
    }
    
}
