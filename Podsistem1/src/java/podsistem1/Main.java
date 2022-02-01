/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import java.util.List;
import java.util.StringTokenizer;
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
    
    
    
//    public static JMSContext context = cf.createContext();
//    public static JMSProducer producer = context.createProducer();
    
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
    }
    
    private static void request2(String parameter) {
        StringTokenizer st = new StringTokenizer(parameter, "###");
        String naziv = st.nextToken();
        String adresa = st.nextToken();
        int idMes = Integer.parseInt(st.nextToken());
        
        Mesto mesto = em.createNamedQuery("Mesto.findByIdmes", Mesto.class).setParameter("idmes", idMes).getSingleResult();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Filijala filijala = new Filijala();
        filijala.setNaziv(naziv);
        filijala.setAdresa(adresa);
        filijala.setIdmes(mesto);
        em.persist(filijala);
        et.commit();
    }
    
    private static void request10() {
        try {
//            JMSContext context = cf.createContext();
//            JMSProducer producer = context.createProducer();
            
            List<Mesto> listaMesta = em.createNamedQuery("Mesto.findAll").getResultList();
            StringBuilder sb = new StringBuilder();
            for(Mesto mesto : listaMesta) {
                sb.append("IdMes: ").append(mesto.getIdmes());
                sb.append(", Naziv: ").append(mesto.getNaziv());            
                sb.append(", Postanski broj: ").append(mesto.getPostanskibroj()).append("###");
            }
            TextMessage txtMsg = getContext().createTextMessage(sb.toString());
            txtMsg.setIntProperty("request", 10);
            getProducer().send(responseTopic, txtMsg);
        } catch (JMSException ex) {}
    }
    
    private static void request11() {
        try {
//            JMSContext context = cf.createContext();
//            JMSProducer producer = context.createProducer();
            
            List<Filijala> listaFilijala = em.createNamedQuery("Filijala.findAll").getResultList();
            StringBuilder sb = new StringBuilder();
            for(Filijala filijala : listaFilijala) {
                sb.append("IdFil: ").append(filijala.getIdfil());
                sb.append(", Naziv: ").append(filijala.getNaziv());
                sb.append(", Adresa: ").append(filijala.getAdresa());
                sb.append(", Mesto: ").append(filijala.getIdmes().getNaziv()).append("###");
            }
            TextMessage txtMsg = getContext().createTextMessage(sb.toString());
            txtMsg.setIntProperty("request", 11);
            getProducer().send(responseTopic, txtMsg);
        } catch (JMSException ex) {}
    }
    
    private static void request12() {
        try {
//            JMSContext context = cf.createContext();
//            JMSProducer producer = context.createProducer();
            
            List<Komitent> listaKomitenata = em.createNamedQuery("Komitent.findAll").getResultList();
            StringBuilder sb = new StringBuilder();
            for(Komitent komitent : listaKomitenata) {
                sb.append("IdKom: ").append(komitent.getIdkom());
                sb.append(", Naziv: ").append(komitent.getNaziv());
                sb.append(", Adresa: ").append(komitent.getAdresa());
                sb.append(", Mesto: ").append(komitent.getIdmes().getNaziv()).append("###");
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
                        break;
                    case 4:
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
