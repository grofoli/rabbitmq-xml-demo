package com.whitepigeongallery.rabbitmqxmldemo.amqp;

import org.springframework.amqp.support.converter.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class MessageConverterProducer {

    @Produces
    @ApplicationScoped
    public MessageConverter messageConverter() {
        final ContentTypeDelegatingMessageConverter messageConverter =
                new ContentTypeDelegatingMessageConverter();
        final Map<String,MessageConverter> delegates = delegatesConverters();
        messageConverter.setDelegates(delegates);
        return messageConverter;
    }

    private Map<String,MessageConverter> delegatesConverters() {
        final Map<String, MessageConverter> delegatesConverters = new HashMap<>();
        delegatesConverters.put("text/plain", new SimpleMessageConverter());
        delegatesConverters.put("application/json", new Jackson2JsonMessageConverter());
  //      delegatesConverters.put("application/xml",
  //              new MarshallingMessageConverter(xmlMessageConverter()));
        delegatesConverters.put("application/xml", new SimpleMessageConverter());
        return delegatesConverters;
    }

    private Marshaller xmlMessageConverter() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setSchema(new ClassPathResource("xsd/ShipOrder.xsd"));
        return marshaller;
    }
}
