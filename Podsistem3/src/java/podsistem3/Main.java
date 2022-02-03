/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import java.util.ArrayList;
import java.util.List;
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
import jparesources.Isplata;
import jparesources.Komitent;
import jparesources.Mesto;
import jparesources.Prenos;
import jparesources.Racun;
import jparesources.Transakcija;
import jparesources.Uplata;

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
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");;
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

    private static void refreshBackup() {
        try {
            JMSConsumer consumerMesto = getContext().createConsumer(responseTopic, "request=25 AND tabela='mesto'", false);
            JMSConsumer consumerFilijala = getContext().createConsumer(responseTopic, "request=25 AND tabela='filijala'", false);
            JMSConsumer consumerKomitent = getContext().createConsumer(responseTopic, "request=25 AND tabela='komitent'", false);
            JMSConsumer consumerRacun = getContext().createConsumer(responseTopic, "request=25 AND tabela='racun'", false);
            JMSConsumer consumerTransakcija = getContext().createConsumer(responseTopic, "request=25 AND tabela='transakcija'", false);
            JMSConsumer consumerUplata = getContext().createConsumer(responseTopic, "request=25 AND tabela='uplata'", false);
            JMSConsumer consumerIsplata = getContext().createConsumer(responseTopic, "request=25 AND tabela='isplata'", false);
            JMSConsumer consumerPrenos = getContext().createConsumer(responseTopic, "request=25 AND tabela='prenos'", false);
            
            ObjectMessage objMsg2 = getContext().createObjectMessage();
            objMsg2.setIntProperty("request", 25);
            getProducer().send(requestTopic, objMsg2);
            
            // TODO parsiranje
            ArrayList<Mesto> listaMesta = (ArrayList<Mesto>) ((ObjectMessage) consumerMesto.receive()).getObject();
            ArrayList<Filijala> listaFilijala = (ArrayList<Filijala>) ((ObjectMessage) consumerFilijala.receive()).getObject();
            ArrayList<Komitent> listaKomitenata = (ArrayList<Komitent>) ((ObjectMessage) consumerKomitent.receive()).getObject();
            ArrayList<Racun> listaRacuna = (ArrayList<Racun>) ((ObjectMessage) consumerRacun.receive()).getObject();
            ArrayList<Transakcija> listaTransakcija = (ArrayList<Transakcija>) ((ObjectMessage) consumerTransakcija.receive()).getObject();
            ArrayList<Uplata> listaUplata = (ArrayList<Uplata>) ((ObjectMessage) consumerUplata.receive()).getObject();
            ArrayList<Isplata> listaIsplata = (ArrayList<Isplata>) ((ObjectMessage) consumerIsplata.receive()).getObject();
            ArrayList<Prenos> listaPrenosa = (ArrayList<Prenos>) ((ObjectMessage) consumerPrenos.receive()).getObject();
                        
            EntityTransaction et = em.getTransaction();
            et.begin();
            for(Mesto mesto : listaMesta) {
                em.merge(mesto);
                em.flush();
            }
            for(Filijala filijala : listaFilijala) {
                em.merge(filijala);
                em.flush();
            }
            for(Komitent komitent : listaKomitenata) {
                em.merge(komitent);
                em.flush();
            }
            for(Racun racun : listaRacuna) {
                em.merge(racun);
                em.flush();
            }
            for(Transakcija transakcija : listaTransakcija) em.merge(transakcija);
            for(Uplata uplata : listaUplata) em.merge(uplata);
            for(Isplata isplata : listaIsplata) em.merge(isplata);
            for(Prenos prenos : listaPrenosa) em.merge(prenos);
            et.commit();
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void request15() {
        List<Mesto> listaMesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
        List<Filijala> listaFilijala = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
        List<Komitent> listaKomitenata = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
        List<Racun> listaRacuna = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
        List<Transakcija> listaTransakcija = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
        List<Uplata> listaUplata = em.createNamedQuery("Uplata.findAll", Uplata.class).getResultList();
        List<Isplata> listaIsplata = em.createNamedQuery("Isplata.findAll", Isplata.class).getResultList();
        List<Prenos> listaPrenosa = em.createNamedQuery("Prenos.findAll", Prenos.class).getResultList();
    
        StringBuilder sb = new StringBuilder();
        for(Mesto mesto : listaMesta) {
            sb.append(mesto.getIdmes()).append("###")
                    .append(mesto.getNaziv()).append("###")
                    .append(mesto.getPostanskibroj()).append("@@@");
        }
        sb.append("$$$");
        for(Filijala filijala : listaFilijala) {
            sb.append(filijala.getIdfil()).append("###")
                    .append(filijala.getNaziv()).append("###")
                    .append(filijala.getAdresa()).append("@@@");
        }
        sb.append("$$$");
        for(Komitent komitent : listaKomitenata) {
            sb.append(komitent.getIdkom()).append("###")
                    .append(komitent.getNaziv()).append("###")
                    .append(komitent.getAdresa()).append("@@@");
        }
        sb.append("$$$");
        for(Racun racun : listaRacuna) {
            sb.append(racun.getIdrac()).append("###")
                    .append(racun.getStanje()).append("###")
                    .append(racun.getDozvoljeniminus()).append("###")
                    .append(racun.getStatus()).append("###")
                    .append(racun.getOtvaranje().toString()).append("###")
                    .append(racun.getBrojtransakcija()).append("###")
                    .append(racun.getIdkom().getNaziv()).append("###")
                    .append(racun.getIdmes().getNaziv()).append("@@@");
        }
        sb.append("$$$");
        for(Transakcija transakcija : listaTransakcija) {
            sb.append(transakcija.getTransakcijaPK().getIdrac()).append("###")
                    .append(transakcija.getTransakcijaPK().getRednibroj()).append("###")
                    .append(transakcija.getIznos()).append("###")
                    .append(transakcija.getObavljanje().toString()).append("###")
                    .append(transakcija.getSvrha()).append("###");
            if(transakcija.getUplata() != null) {
                sb.append("Uplata").append("###")
                        .append("-1").append("###")
                        .append(transakcija.getUplata().getIdracna().getIdrac()).append("@@@");
            } else if(transakcija.getIsplata() != null) {
                sb.append("Isplata").append("###")
                        .append(transakcija.getIsplata().getIdracsa().getIdrac()).append("###")
                        .append("-1").append("@@@");
            } else if(transakcija.getPrenos() != null) {
                sb.append("Prenos").append("###")
                        .append(transakcija.getPrenos().getIdracsa().getIdrac()).append("###")
                        .append(transakcija.getPrenos().getIdracna().getIdrac()).append("@@@");
            }
        }
        TextMessage txtMsg = getContext().createTextMessage(sb.toString());
        try {
            txtMsg.setIntProperty("request", 15);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        getProducer().send(responseTopic, txtMsg);
    }
    
    private static void request16() {
        try {            
            JMSConsumer consumerMesto = getContext().createConsumer(responseTopic, "request=31 AND tabela='mesto'", false);
            JMSConsumer consumerFilijala = getContext().createConsumer(responseTopic, "request=31 AND tabela='filijala'", false);
            JMSConsumer consumerKomitent = getContext().createConsumer(responseTopic, "request=31 AND tabela='komitent'", false);
            JMSConsumer consumerRacun = getContext().createConsumer(responseTopic, "request=31 AND tabela='racun'", false);
            JMSConsumer consumerTransakcija = getContext().createConsumer(responseTopic, "request=31 AND tabela='transakcija'", false);
            JMSConsumer consumerUplata = getContext().createConsumer(responseTopic, "request=31 AND tabela='uplata'", false);
            JMSConsumer consumerIsplata = getContext().createConsumer(responseTopic, "request=31 AND tabela='isplata'", false);
            JMSConsumer consumerPrenos = getContext().createConsumer(responseTopic, "request=31 AND tabela='prenos'", false);
            
            ObjectMessage objMsg = getContext().createObjectMessage();
            objMsg.setIntProperty("request", 31);
            getProducer().send(requestTopic, objMsg);
            
            // TODO parsiranje
            List<Mesto> listaMesta1 = (ArrayList<Mesto>) ((ObjectMessage) consumerMesto.receive()).getObject();
            List<Filijala> listaFilijala1 = (ArrayList<Filijala>) ((ObjectMessage) consumerFilijala.receive()).getObject();
            List<Komitent> listaKomitenata1 = (ArrayList<Komitent>) ((ObjectMessage) consumerKomitent.receive()).getObject();
            List<Racun> listaRacuna1 = (ArrayList<Racun>) ((ObjectMessage) consumerRacun.receive()).getObject();
            List<Transakcija> listaTransakcija1 = (ArrayList<Transakcija>) ((ObjectMessage) consumerTransakcija.receive()).getObject();
            List<Uplata> listaUplata1 = (ArrayList<Uplata>) ((ObjectMessage) consumerUplata.receive()).getObject();
            List<Isplata> listaIsplata1 = (ArrayList<Isplata>) ((ObjectMessage) consumerIsplata.receive()).getObject();
            List<Prenos> listaPrenosa1 = (ArrayList<Prenos>) ((ObjectMessage) consumerPrenos.receive()).getObject();
            
            List<Mesto> listaMesta2 = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
            List<Filijala> listaFilijala2 = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
            List<Komitent> listaKomitenata2 = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
            List<Racun> listaRacuna2 = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
            List<Transakcija> listaTransakcija2 = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
            
            StringBuilder sb = new StringBuilder();
            
            sb.append("Mesto#");
            for(int i = 0; i < listaMesta1.size(); i++) {
                if(i >= listaMesta2.size()) {
                    Mesto mesto = listaMesta1.get(i);
                    sb.append(mesto.getIdmes()).append("\t")
                            .append(mesto.getNaziv()).append("\t")
                            .append(mesto.getPostanskibroj()).append("#");
                    
                }
            }
            
            sb.append("#Filijala#");
            for(int i = 0; i < listaFilijala1.size(); i++) {
                if(i >= listaFilijala2.size()) {
                    Filijala filijala = listaFilijala1.get(i);
                    sb.append(filijala.getIdfil()).append("\t")
                            .append(filijala.getNaziv()).append("\t")
                            .append(filijala.getAdresa()).append("\t")
                            .append(filijala.getIdmes().getNaziv()).append("#");
                }
            }
            
            sb.append("#Komitent#");
            for(int i = 0; i < listaKomitenata1.size(); i++) {
                Komitent komitent = null;
                if(i >= listaKomitenata2.size()) {
                    komitent = listaKomitenata1.get(i);
                } else if(!listaKomitenata1.get(i).getAdresa().equals(listaKomitenata2.get(i).getAdresa())) {
                    komitent = listaKomitenata1.get(i);
                }
                if(komitent != null) {
                    sb.append(komitent.getIdkom()).append("\t")
                            .append(komitent.getNaziv()).append("\t")
                            .append(komitent.getAdresa()).append("\t")
                            .append(komitent.getIdmes().getNaziv()).append("#");
                }
            }
            
            sb.append("#Racun#");
            for(int i = 0; i < listaRacuna1.size(); i++) {
                Racun racun = null;
                if(i >= listaRacuna2.size()) {
                    racun = listaRacuna1.get(i);
                } else if(listaRacuna1.get(i).getBrojtransakcija() != listaRacuna2.get(i).getBrojtransakcija()) {
                    racun = listaRacuna1.get(i);
                }
                if(racun != null) {
                    sb.append(racun.getIdrac()).append("\t")
                            .append(racun.getStanje()).append("\t")
                            .append(racun.getDozvoljeniminus()).append("\t")
                            .append(racun.getStatus()).append("\t")
                            .append(racun.getBrojtransakcija()).append("#");
                }
            }
            
            sb.append("#Transakcija#");
            for(int i = 0; i < listaTransakcija1.size(); i++) {
                if(i >= listaTransakcija2.size()) {
                    Transakcija transakcija  = listaTransakcija1.get(i);
                    sb.append(transakcija.getTransakcijaPK().getIdrac()).append("\t")
                            .append(transakcija.getTransakcijaPK().getRednibroj()).append("\t")
                            .append(transakcija.getIznos()).append("\t")
                            .append(transakcija.getObavljanje()).append("#");
                }
            }
            
            TextMessage txtMsg = getContext().createTextMessage(sb.toString());
            try {
                txtMsg.setIntProperty("request", 16);
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            getProducer().send(responseTopic, txtMsg);
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(120000);
                    refreshBackup();
                }
            } catch(InterruptedException e) {}
        });
        
        t.start();
        
        JMSConsumer consumer = getContext().createConsumer(requestTopic, 
            "request=15 OR request=16", false);
        while(true) {
            try {
                ObjectMessage objMsg = (ObjectMessage) consumer.receive();
                switch(objMsg.getIntProperty("request")) {
                    case 15:
                        request15();
                        break;
                    case 16:
                        request16();
                        break;
                }
            } catch (JMSException ex) {}
        }
    }
    
}
