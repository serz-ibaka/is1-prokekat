/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import java.util.ArrayList;
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
import jparesources.*;

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
        System.out.println("Ovdje sam");
        StringTokenizer st = new StringTokenizer(parameter, "###");
        float dozvoljeniMinus = Float.parseFloat(st.nextToken());
        int idKom = Integer.parseInt(st.nextToken());
        int idMes = Integer.parseInt(st.nextToken());
        
        Mesto mesto;
        Komitent komitent;
        try {
            mesto = em.createNamedQuery("Mesto.findByIdmes", Mesto.class).setParameter("idmes", idMes).getSingleResult();
            komitent = em.createNamedQuery("Komitent.findByIdkom", Komitent.class).setParameter("idkom", idKom).getSingleResult();
        } catch(Exception e) {
            // nemoguce kreirati racun
            return;
        }
        EntityTransaction et = em.getTransaction();
        et.begin();
        Racun racun = new Racun();
        racun.setIdkom(komitent);
        racun.setIdmes(mesto);
        racun.setDozvoljeniminus(dozvoljeniMinus);
        em.persist(racun);
        et.commit();
    }
    
    private static void request6(String parameter) {
        int idRac = Integer.parseInt(parameter);
        
        Racun racun;
        try {
            racun = em.createNamedQuery("Racun.findByIdrac", Racun.class).setParameter("idrac", idRac).getSingleResult();
        } catch(Exception e) {
            // nema racuna
            return;
        }
        EntityTransaction et = em.getTransaction();
        et.begin();
        racun.setStatus('Z');
        em.persist(racun);
        et.commit();
    }
    
    private static void request7(String parameter) {
        StringTokenizer st = new StringTokenizer(parameter, "###");
        // "" + idRacSa + "###" + idRacNa + "###" + idFil + "###" + iznos + "###" + svrha
        int idRacSa = Integer.parseInt(st.nextToken());
        int idRacNa = Integer.parseInt(st.nextToken());
        float iznos = Float.parseFloat(st.nextToken());
        String svrha = st.nextToken();
        
        Racun racunSa = null;
        Racun racunNa = null;
        try {
            racunSa = em.createNamedQuery("Racun.findByIdrac", Racun.class).setParameter("idrac", idRacSa).getSingleResult();
            racunNa = em.createNamedQuery("Racun.findByIdrac", Racun.class).setParameter("idrac", idRacNa).getSingleResult();
        } catch (Exception e) {}
        
        if(racunSa == null || racunNa == null || iznos < 0
                || racunSa.getStatus() == 'Z' || racunSa.getStatus() == 'B' || racunNa.getStatus() == 'Z') {
            // prenos se ne moze izvrsiti
            return;
        }
        
        EntityTransaction et = em.getTransaction();
        et.begin();
        
        racunSa.setBrojtransakcija(racunSa.getBrojtransakcija() + 1);
        racunSa.setStanje(racunSa.getStanje() - iznos);
        if(racunSa.getStanje() < -racunSa.getDozvoljeniminus()) racunSa.setStatus('B');
        racunNa.setBrojtransakcija(racunNa.getBrojtransakcija() + 1);
        racunNa.setStanje(racunNa.getStanje() + iznos);
        if(racunNa.getStanje() > -racunNa.getDozvoljeniminus()) racunNa.setStatus('A');
        
        Transakcija transakcijaSa = new Transakcija();
        Transakcija transakcijaNa = new Transakcija();
        transakcijaSa.setSvrha(svrha);
        transakcijaNa.setSvrha(svrha);
        transakcijaSa.setIznos(iznos);
        transakcijaNa.setIznos(iznos);
        transakcijaSa.setTransakcijaPK(new TransakcijaPK(idRacSa, racunSa.getBrojtransakcija()));
        transakcijaNa.setTransakcijaPK(new TransakcijaPK(idRacNa, racunNa.getBrojtransakcija()));
        
        Prenos prenosSa = new Prenos();
        Prenos prenosNa = new Prenos();
        prenosSa.setIdracsa(racunSa);
        prenosSa.setIdracna(racunNa);
        prenosNa.setIdracsa(racunSa);
        prenosNa.setIdracna(racunNa);
        prenosSa.setPrenosPK(new PrenosPK(idRacSa, racunSa.getBrojtransakcija()));
        prenosNa.setPrenosPK(new PrenosPK(idRacNa, racunNa.getBrojtransakcija()));
        prenosSa.setTransakcija(transakcijaSa);
        prenosNa.setTransakcija(transakcijaNa);
        
        transakcijaSa.setPrenos(prenosSa);
        transakcijaNa.setPrenos(prenosNa);
        
        em.persist(racunSa);
        em.persist(racunNa);
        em.persist(transakcijaSa);
        em.persist(transakcijaNa);
        em.persist(prenosSa);
        em.persist(prenosNa);
        
        et.commit();
    }
    
    private static void request8(String parameter) {
        StringTokenizer st = new StringTokenizer(parameter, "###");
        // "" + idRacSa + "###" + idRacNa + "###" + idFil + "###" + iznos + "###" + svrha
        int idRacNa = Integer.parseInt(st.nextToken());
        int idFil = Integer.parseInt(st.nextToken());
        float iznos = Float.parseFloat(st.nextToken());
        String svrha = st.nextToken();
        
        Racun racunNa = null;
        Filijala filijala = null;
        try {
            racunNa = em.createNamedQuery("Racun.findByIdrac", Racun.class).setParameter("idrac", idRacNa).getSingleResult();
            filijala = em.createNamedQuery("Filijala.findByIdfil", Filijala.class).setParameter("idfil", idFil).getSingleResult();
        } catch(Exception e) {}
        
        if(racunNa == null || filijala == null || racunNa.getStatus() == 'Z' || iznos < 0) {
            // ne moze se izvrsiti uplata
            return;
        }
        
        EntityTransaction et = em.getTransaction();
        et.begin();
        racunNa.setBrojtransakcija(racunNa.getBrojtransakcija() + 1);
        racunNa.setStanje(racunNa.getStanje() + iznos);
        if(racunNa.getStanje() > -racunNa.getDozvoljeniminus()) racunNa.setStatus('A');
        Transakcija transakcija = new Transakcija();
        transakcija.setSvrha(svrha);
        transakcija.setIznos(iznos);
        transakcija.setTransakcijaPK(new TransakcijaPK(idRacNa, racunNa.getBrojtransakcija()));
        Uplata uplata = new Uplata();
        uplata.setUplataPK(new UplataPK(idRacNa, racunNa.getBrojtransakcija()));
        uplata.setIdfil(filijala);
        uplata.setTransakcija(transakcija);
        uplata.setIdracna(racunNa);
        transakcija.setUplata(uplata);
        
        em.persist(racunNa);
        em.persist(transakcija);
        em.persist(uplata);
        et.commit();
    }
    
    private static void request9(String parameter) {
        StringTokenizer st = new StringTokenizer(parameter, "###");
        // "" + idRacSa + "###" + idRacNa + "###" + idFil + "###" + iznos + "###" + svrha
        int idRacSa = Integer.parseInt(st.nextToken());
        int idFil = Integer.parseInt(st.nextToken());
        float iznos = Float.parseFloat(st.nextToken());
        String svrha = st.nextToken();
        
        Racun racunSa = null;
        Filijala filijala = null;
        try{
            racunSa = em.createNamedQuery("Racun.findByIdrac", Racun.class).setParameter("idrac", idRacSa).getSingleResult();
            filijala = em.createNamedQuery("Filijala.findByIdfil", Filijala.class).setParameter("idfil", idFil).getSingleResult();
        } catch(Exception e) {}
        
        if(racunSa == null || filijala == null || racunSa.getStatus() == 'B' || racunSa.getStatus() == 'Z' || iznos < 0) {
            // ne moze se izvrsiti isplata
            return;
        }
        
        EntityTransaction et = em.getTransaction();
        et.begin();
        racunSa.setBrojtransakcija(racunSa.getBrojtransakcija() + 1);
        racunSa.setStanje(racunSa.getStanje() - iznos);
        if(racunSa.getStanje() < -racunSa.getDozvoljeniminus()) racunSa.setStatus('B');
        Transakcija transakcija = new Transakcija();
        transakcija.setSvrha(svrha);
        transakcija.setIznos(iznos);
        transakcija.setTransakcijaPK(new TransakcijaPK(idRacSa, racunSa.getBrojtransakcija()));
        Isplata isplata = new Isplata();
        isplata.setIsplataPK(new IsplataPK(idRacSa, racunSa.getBrojtransakcija()));
        isplata.setIdfil(filijala);
        isplata.setTransakcija(transakcija);
        isplata.setIdracsa(racunSa);
        transakcija.setIsplata(isplata);
        
        em.persist(racunSa);
        em.persist(transakcija);
        em.persist(isplata);
        et.commit();
    }
    
    private static void request13(int idKom) {
        try {
            Komitent komitent = null;
            try {
            komitent = em.createNamedQuery("Komitent.findByIdkom", Komitent.class)
                    .setParameter("idkom", idKom).getSingleResult();
            } catch(Exception e) {
                TextMessage txtMsg = getContext().createTextMessage("");
                txtMsg.setIntProperty("request", 13);
                getProducer().send(responseTopic, txtMsg);
                return;
            }
            List<Racun> listaRacuna = em.createQuery("SELECT r FROM Racun r WHERE r.idkom = :idkom", Racun.class)
                    .setParameter("idkom", komitent).getResultList();
            StringBuilder sb = new StringBuilder();
//            sb.append("Id Racuna###Stanje###Dozvoljeni Minus###Status###Datum Otvaranja###Broj Transakcija###Komitent###Mesto");
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
//            sb.append("Id Racuna###Redni Broj###Iznos###Datum Obavljanja###Svrha###Tip Transakcije###Id Racuna Sa###Id Racuna Na");
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
                } else {System.out.println("kita kita kita");}
            }
            TextMessage txtMsg = getContext().createTextMessage(sb.toString());
            txtMsg.setIntProperty("request", 14);
            getProducer().send(responseTopic, txtMsg);
        } catch (JMSException ex) {}
    }
    
    private static void request21(String parameter) {
        StringTokenizer st = new StringTokenizer(parameter, "###");
        int id = Integer.parseInt(st.nextToken());
        String naziv = st.nextToken();
        int postanskiBroj = Integer.parseInt(st.nextToken());
        
        EntityTransaction et = em.getTransaction();
        et.begin();
        Mesto mesto = new Mesto(id, naziv, postanskiBroj);
        em.persist(mesto);
        et.commit();
    }
    
    private static void request22(String parameter) {
        StringTokenizer st = new StringTokenizer(parameter, "###");
        int id = Integer.parseInt(st.nextToken());
        String naziv = st.nextToken();
        String adresa = st.nextToken();
        int idMes = Integer.parseInt(st.nextToken());
        
        Mesto mesto = em.createNamedQuery("Mesto.findByIdmes", Mesto.class).setParameter("idmes", idMes).getSingleResult();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Filijala filijala = new Filijala(idMes, naziv, adresa);
        filijala.setIdmes(mesto);
        em.persist(filijala);
        et.commit();
    }
    
    private static void request23(String parameter) {
        StringTokenizer st = new StringTokenizer(parameter, "###");
        int id = Integer.parseInt(st.nextToken());
        String naziv = st.nextToken();
        String adresa = st.nextToken();
        int idMes = Integer.parseInt(st.nextToken());
        
        Mesto mesto = em.createNamedQuery("Mesto.findByIdmes", Mesto.class).setParameter("idmes", idMes).getSingleResult();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Komitent komitent = new Komitent(id, naziv, adresa);
        komitent.setIdmes(mesto);
        em.persist(komitent);
        et.commit();
    }
    
    private static void request24(String parameter) { // idkom idmes adresa
        System.out.println(parameter);
        StringTokenizer st = new StringTokenizer(parameter, "###");
        int id = Integer.parseInt(st.nextToken());
        int idMes = Integer.parseInt(st.nextToken());
        String adresa = st.nextToken();
        
        Mesto mesto = em.createNamedQuery("Mesto.findByIdmes", Mesto.class).setParameter("idmes", idMes).getSingleResult();
        Komitent komitent = em.createNamedQuery("Komitent.findByIdkom", Komitent.class).setParameter("idkom", id).getSingleResult();
        komitent.setIdmes(mesto);
        komitent.setAdresa(adresa);
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(komitent);
        et.commit();
    }
    
    private static void request25() {
        List<Mesto> listaMesta1 = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
        ArrayList<Mesto> listaMesta = new ArrayList<>(listaMesta1);
        List<Filijala> listaFilijala1 = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
        ArrayList<Filijala> listaFilijala = new ArrayList<>(listaFilijala1);
        List<Komitent> listaKomitenata1 = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
        ArrayList<Komitent> listaKomitenata = new ArrayList<>(listaKomitenata1);
        List<Racun> listaRacuna1 = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
        ArrayList<Racun> listaRacuna = new ArrayList<>(listaRacuna1);
        List<Transakcija> listaTransakcija1 = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
        ArrayList<Transakcija> listaTransakcija = new ArrayList<>(listaTransakcija1);
        List<Uplata> listaUplata1 = em.createNamedQuery("Uplata.findAll", Uplata.class).getResultList();
        ArrayList<Uplata> listaUplata = new ArrayList<>(listaUplata1);
        List<Isplata> listaIsplata1 = em.createNamedQuery("Isplata.findAll", Isplata.class).getResultList();
        ArrayList<Isplata> listaIsplata = new ArrayList<>(listaIsplata1);
        List<Prenos> listaPrenosa1 = em.createNamedQuery("Prenos.findAll", Prenos.class).getResultList();
        ArrayList<Prenos> listaPrenosa = new ArrayList<>(listaPrenosa1);
        
        try {
            ObjectMessage msgMesto = getContext().createObjectMessage();
            msgMesto.setIntProperty("request", 25);
            msgMesto.setStringProperty("tabela", "mesto");
            msgMesto.setObject(listaMesta);
            getProducer().send(responseTopic, msgMesto);
            
            ObjectMessage msgFilijala = getContext().createObjectMessage();
            msgFilijala.setIntProperty("request", 25);
            msgFilijala.setStringProperty("tabela", "filijala");
            msgFilijala.setObject(listaFilijala);
            getProducer().send(responseTopic, msgFilijala);
            
            ObjectMessage msgKomitent = getContext().createObjectMessage();
            msgKomitent.setIntProperty("request", 25);
            msgKomitent.setStringProperty("tabela", "komitent");
            msgKomitent.setObject(listaKomitenata);
            getProducer().send(responseTopic, msgKomitent);
            ObjectMessage msgRacun = getContext().createObjectMessage();
            msgRacun.setIntProperty("request", 25);
            msgRacun.setStringProperty("tabela", "racun");
            msgRacun.setObject(listaRacuna);
            getProducer().send(responseTopic, msgRacun);
            ObjectMessage msgTransakcija = getContext().createObjectMessage();
            msgTransakcija.setIntProperty("request", 25);
            msgTransakcija.setStringProperty("tabela", "transakcija");
            msgTransakcija.setObject(listaTransakcija);
            getProducer().send(responseTopic, msgTransakcija);
            ObjectMessage msgUplata = getContext().createObjectMessage();
            msgUplata.setIntProperty("request", 25);
            msgUplata.setStringProperty("tabela", "uplata");
            msgUplata.setObject(listaUplata);
            getProducer().send(responseTopic, msgUplata);
            ObjectMessage msgIsplata = getContext().createObjectMessage();
            msgIsplata.setIntProperty("request", 25);
            msgIsplata.setStringProperty("tabela", "isplata");
            msgIsplata.setObject(listaIsplata);
            getProducer().send(responseTopic, msgIsplata);
            ObjectMessage msgPrenos = getContext().createObjectMessage();
            msgPrenos.setIntProperty("request", 25);
            msgPrenos.setStringProperty("tabela", "prenos");
            msgPrenos.setObject(listaPrenosa);
            getProducer().send(responseTopic, msgPrenos);
        } catch(JMSException e) {}
    }
    
    private static void request31() {
        List<Mesto> listaMesta1 = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
        ArrayList<Mesto> listaMesta = new ArrayList<>(listaMesta1);
        List<Filijala> listaFilijala1 = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
        ArrayList<Filijala> listaFilijala = new ArrayList<>(listaFilijala1);
        List<Komitent> listaKomitenata1 = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
        ArrayList<Komitent> listaKomitenata = new ArrayList<>(listaKomitenata1);
        List<Racun> listaRacuna1 = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
        ArrayList<Racun> listaRacuna = new ArrayList<>(listaRacuna1);
        List<Transakcija> listaTransakcija1 = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
        ArrayList<Transakcija> listaTransakcija = new ArrayList<>(listaTransakcija1);
        List<Uplata> listaUplata1 = em.createNamedQuery("Uplata.findAll", Uplata.class).getResultList();
        ArrayList<Uplata> listaUplata = new ArrayList<>(listaUplata1);
        List<Isplata> listaIsplata1 = em.createNamedQuery("Isplata.findAll", Isplata.class).getResultList();
        ArrayList<Isplata> listaIsplata = new ArrayList<>(listaIsplata1);
        List<Prenos> listaPrenosa1 = em.createNamedQuery("Prenos.findAll", Prenos.class).getResultList();
        ArrayList<Prenos> listaPrenosa = new ArrayList<>(listaPrenosa1);
        
        try {
            ObjectMessage msgMesto = getContext().createObjectMessage();
            msgMesto.setIntProperty("request", 31);
            msgMesto.setStringProperty("tabela", "mesto");
            msgMesto.setObject(listaMesta);
            getProducer().send(responseTopic, msgMesto);
            
            ObjectMessage msgFilijala = getContext().createObjectMessage();
            msgFilijala.setIntProperty("request", 31);
            msgFilijala.setStringProperty("tabela", "filijala");
            msgFilijala.setObject(listaFilijala);
            getProducer().send(responseTopic, msgFilijala);
            
            ObjectMessage msgKomitent = getContext().createObjectMessage();
            msgKomitent.setIntProperty("request", 31);
            msgKomitent.setStringProperty("tabela", "komitent");
            msgKomitent.setObject(listaKomitenata);
            getProducer().send(responseTopic, msgKomitent);
            ObjectMessage msgRacun = getContext().createObjectMessage();
            msgRacun.setIntProperty("request", 31);
            msgRacun.setStringProperty("tabela", "racun");
            msgRacun.setObject(listaRacuna);
            getProducer().send(responseTopic, msgRacun);
            ObjectMessage msgTransakcija = getContext().createObjectMessage();
            msgTransakcija.setIntProperty("request", 31);
            msgTransakcija.setStringProperty("tabela", "transakcija");
            msgTransakcija.setObject(listaTransakcija);
            getProducer().send(responseTopic, msgTransakcija);
            ObjectMessage msgUplata = getContext().createObjectMessage();
            msgUplata.setIntProperty("request", 31);
            msgUplata.setStringProperty("tabela", "uplata");
            msgUplata.setObject(listaUplata);
            getProducer().send(responseTopic, msgUplata);
            ObjectMessage msgIsplata = getContext().createObjectMessage();
            msgIsplata.setIntProperty("request", 31);
            msgIsplata.setStringProperty("tabela", "isplata");
            msgIsplata.setObject(listaIsplata);
            getProducer().send(responseTopic, msgIsplata);
            ObjectMessage msgPrenos = getContext().createObjectMessage();
            msgPrenos.setIntProperty("request", 31);
            msgPrenos.setStringProperty("tabela", "prenos");
            msgPrenos.setObject(listaPrenosa);
            getProducer().send(responseTopic, msgPrenos);
        } catch(JMSException e) {}
    }
    
    public static void main(String[] args) {
        JMSConsumer consumer = getContext().createConsumer(requestTopic, 
            "request=5 OR request=6 OR request=7 OR request=8 OR request=9 OR request=13 OR "
          + "request=14 OR request=21 OR request=22 OR request=23 OR request=24 OR request=25 OR request=31", false);
        while(true) {
            try {
                ObjectMessage objMsg = (ObjectMessage) consumer.receive();
                switch(objMsg.getIntProperty("request")) {
                    case 5:
                        request5((String)objMsg.getObject());
                        break;
                    case 6:
                        request6((String)objMsg.getObject());
                        break;
                    case 7:
                        request7((String)objMsg.getObject());
                        break;
                    case 8:
                        request8((String)objMsg.getObject());
                        break;
                    case 9:
                        request9((String)objMsg.getObject());
                        break;
                    case 13:
                        request13(objMsg.getIntProperty("idKom"));
                        break;
                    case 14:
                        request14(objMsg.getIntProperty("idRac"));
                        break;
                    case 21:
                        request21(objMsg.getStringProperty("parameter"));
                        break;
                    case 22:
                        request22(objMsg.getStringProperty("parameter"));
                        break;
                    case 23:
                        request23(objMsg.getStringProperty("parameter"));
                        break;
                    case 24:
                        request24(objMsg.getStringProperty("parameter"));
                        break;
                    case 25:
                        request25();
                        break;
                    case 31:
                        request31();
                        break;
                }
            } catch (JMSException ex) {}
        }
    }
    
}
