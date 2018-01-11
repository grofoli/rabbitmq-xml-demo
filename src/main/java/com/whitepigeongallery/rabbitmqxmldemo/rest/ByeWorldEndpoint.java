package com.whitepigeongallery.rabbitmqxmldemo.rest;


import generated.Shiporder;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.MessageConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Path("/bye")
@ApplicationScoped
public class ByeWorldEndpoint {

    @Inject
    private RabbitAdmin rabbitAdmin;

    @Inject
    private MessageConverter messageConverter;

    @GET
    @Produces("text/plain")
    public Response doGet() {
        rabbitAdmin.initialize();

        Object message = rabbitAdmin.getRabbitTemplate().receiveAndConvert("shipping");
        return Response.ok(message.toString()).build();
    }
}