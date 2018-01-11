package com.whitepigeongallery.rabbitmqxmldemo.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.MessageConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

@ApplicationScoped
public class RabbitAdminProducer {

    @Inject
    private ConnectionFactory connectionFactory;

    @Inject
    private MessageConverter messageConverter;

    @Produces
    @Singleton
    public RabbitAdmin rabbitAdmin() {
        final RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.getRabbitTemplate().setMessageConverter(messageConverter);
        return rabbitAdmin;
    }
}
