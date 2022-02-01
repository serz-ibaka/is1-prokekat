/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.centralniserver.endpoints;

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
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Sergej
 */
@Path("komitent")
public class EndpointKomitent {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory cf;
    
    @Resource(lookup = "requestTopic")
    public Topic requestTopic;
    
    @Resource(lookup = "responseTopic")
    public Topic responseTopic;
    
    public static JMSContext sContext;
    
    @GET
    public Response dohvatiKomitente() {
        if(sContext == null) sContext = cf.createContext();
        JMSContext context = sContext;
        JMSProducer producer = context.createProducer();
        ObjectMessage objMsg = context.createObjectMessage();
        try {
            objMsg.setIntProperty("request", 12);
        } catch (JMSException ex) {}
        producer.send(requestTopic, objMsg);
        
        JMSConsumer consumer = context.createConsumer(responseTopic, "request=12", false);
        String message = null;
        try {
            message = ((TextMessage) consumer.receive()).getText();
        } catch (JMSException ex) {}
        
        return Response.status(Response.Status.OK).entity(message).build();
    }
    
}
