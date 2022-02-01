/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import java.util.Date;
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
import jparesources.Komitent;
import jparesources.Mesto;
import jparesources.Racun;
import jparesources.Transakcija;

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
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");;
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
    
    private static void request5(String parameter) {
        StringTokenizer st = new StringTokenizer(parameter, "###");
        float dozvoljeniMinus = Float.parseFloat(st.nextToken());
        int idKom = Integer.parseInt(st.nextToken());
        int idMes = Integer.parseInt(st.nextToken());
        
        Mesto mesto = em.createNamedQuery("Mesto.findByIdmes", Mesto.class).setParameter("idmes", idMes).getSingleResult();
        Komitent komitent = em.createNamedQuery("Komitent.findByIdkom", Komitent.class).setParameter("idkom", idKom).getSingleResult();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Racun racun = new Racun();
        racun.setIdkom(komitent);
        racun.setIdmes(mesto);
        racun.setDozvoljeniminus(dozvoljeniMinus);
        em.persist(racun);
        et.commit();
    }
    
    private static void request13(int idKom) {
        try {
            Komitent komitent = em.createNamedQuery("Komitent.findByIdkom", Komitent.class)
                    .setParameter("idkom", idKom).getSingleResult();
            if(komitent == null) {
                TextMessage txtMsg = getContext().createTextMessage("");
                txtMsg.setIntProperty("request", 13);
                getProducer().send(responseTopic, txtMsg);
                return;
            }
            List<Racun> listaRacuna = em.createQuery("SELECT r FROM Racun r WHERE r.idkom = :idkom", Racun.class)
                    .setParameter("idkom", komitent).getResultList();
            StringBuilder sb = new StringBuilder();
            sb.append("Id Racuna###Stanje###Dozvoljeni Minus###Status###Datum Otvaranja###Broj Transakcija###Komitent###Mesto");
            for(Racun racun : listaRacuna) {
                sb.append("@@@").append(racun.getIdrac())
                    .append("###").append(racun.getStanje())
                    .append("###").append(racun.getDozvoljeniminus())
                    .append("###").append(racun.getStatus())
                    .append("###").append(racun.getOtvaranje().toString())
                    .append("###").append(racun.getBrojtransakcija())
                    .append("###").append(racun.getIdkom().getNaziv())
                    .append("###").append(racun.getIdmes().getNaziv());
            }
            TextMessage txtMsg = getContext().createTextMessage(sb.toString());
            txtMsg.setIntProperty("request", 13);
            getProducer().send(responseTopic, txtMsg);
        } catch (JMSException ex) {}
    }
    
    private static void request14(int idRac) {
        try {
            List<Transakcija> listaTransakcija = em.createNamedQuery("Transakcija.findByIdrac", Transakcija.class)
                    .setParameter("idrac", idRac).getResultList();
            StringBuilder sb = new StringBuilder();
            sb.append("Id Racuna###Redni Broj###Iznos###Datum Obavljanja###Svrha###Tip Transakcije###Id Racuna Sa###Id Racuna Na");
            for(Transakcija transakcija : listaTransakcija) {
                sb.append("@@@").append(transakcija.getTransakcijaPK().getIdrac())
                    .append("###").append(transakcija.getTransakcijaPK().getRednibroj())
                    .append("###").append(transakcija.getIznos())
                    .append("###").append(transakcija.getObavljanje().toString())
                    .append("###").append(transakcija.getSvrha());
                if(transakcija.getUplata() != null) {
                    sb.append("###").append("Uplata")
                        .append("###").append("-1")
                        .append("###").append(transakcija.getUplata().getIdracna().getIdrac());
                } else if(transakcija.getIsplata() != null) {
                    sb.append("###").append("Isplata")
                        .append("###").append(transakcija.getIsplata().getIdracsa().getIdrac())
                        .append("###").append("-1");
                } else if(transakcija.getPrenos() != null) {
                    sb.append("###").append("Prenos")
                        .append("###").append(transakcija.getPrenos().getIdracsa().getIdrac())
                        .append("###").append(transakcija.getPrenos().getIdracna().getIdrac());
                }
            }
            TextMessage txtMsg = getContext().createTextMessage(sb.toString());
            txtMsg.setIntProperty("request", 14);
            getProducer().send(responseTopic, txtMsg);
        } catch (JMSException ex) {}
    }
    
    public static void main(String[] args) {
//        JMSContext context = cf.createContext();
        JMSConsumer consumer = getContext().createConsumer(requestTopic, 
            "request=5 OR request=6 OR request=7 OR request=8 OR request=9 OR request=13 OR request=14", false);
        while(true) {
            try {
                ObjectMessage objMsg = (ObjectMessage) consumer.receive();
                switch(objMsg.getIntProperty("request")) {
                    case 5:
                        request5((String)objMsg.getObject());
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 13:
                        request13(objMsg.getIntProperty("idKom"));
                        break;
                    case 14:
                        request14(objMsg.getIntProperty("idRac"));
                        break;
                }
            } catch (JMSException ex) {}
        }
    }
    
}
