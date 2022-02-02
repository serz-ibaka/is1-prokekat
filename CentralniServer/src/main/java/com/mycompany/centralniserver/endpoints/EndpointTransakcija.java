/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.centralniserver.endpoints;

import static com.mycompany.centralniserver.endpoints.EndpointKomitent.sContext;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Sergej
 */
@Path("transakcija")
public class EndpointTransakcija {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory cf;
    
    @Resource(lookup = "requestTopic")
    public Topic requestTopic;
    
    @Resource(lookup = "responseTopic")
    public Topic responseTopic;
    
    @GET
    @Path("{idrac}")
    public Response dohvatiTransakcije(@PathParam("idrac") int idRac) {
        if(EndpointKomitent.sContext == null) sContext = cf.createContext();
        JMSContext context = sContext;
        JMSProducer producer = context.createProducer();
        ObjectMessage objMsg = context.createObjectMessage();
        try {
            objMsg.setIntProperty("request", 14);
            objMsg.setIntProperty("idRac", idRac);
        } catch (JMSException ex) {}
        producer.send(requestTopic, objMsg);
        
        JMSConsumer consumer = context.createConsumer(responseTopic, "request=14", false);
        String message = null;
        try {
            message = ((TextMessage) consumer.receive()).getText();
        } catch (JMSException ex) {}
        
        return Response.status(Response.Status.OK).entity(message).build();
    }
    
    @Path("prenos")
    @POST
    public Response kreirajPrenos(String parameter) {
        if(sContext == null) sContext = cf.createContext();
        JMSContext context = sContext;
        JMSProducer producer = context.createProducer();
        ObjectMessage objMsg = context.createObjectMessage(parameter);
        try {
            objMsg.setIntProperty("request", 7);
        } catch (JMSException ex) {}
        producer.send(requestTopic, objMsg);
        
        return Response.status(Response.Status.OK).build();
    }
    
    @Path("uplata")
    @POST
    public Response kreirajUplatu(String parameter) {
        if(sContext == null) sContext = cf.createContext();
        JMSContext context = sContext;
        JMSProducer producer = context.createProducer();
        ObjectMessage objMsg = context.createObjectMessage(parameter);
        try {
            objMsg.setIntProperty("request", 8);
        } catch (JMSException ex) {}
        producer.send(requestTopic, objMsg);
        
        return Response.status(Response.Status.OK).build();
    }
    
    @Path("isplata")
    @POST
    public Response kreirajIsplatu(String parameter) {
        if(sContext == null) sContext = cf.createContext();
        JMSContext context = sContext;
        JMSProducer producer = context.createProducer();
        ObjectMessage objMsg = context.createObjectMessage(parameter);
        try {
            objMsg.setIntProperty("request", 9);
        } catch (JMSException ex) {}
        producer.send(requestTopic, objMsg);
        
        return Response.status(Response.Status.OK).build();
    }
    
}
