package com.whitepigeongallery.rabbitmqxmldemo.rest;


import generated.Shiporder;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

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

    @GET
    @Produces("text/plain")
    public Response doGet() {
        rabbitAdmin.initialize();
        Message message = rabbitAdmin.getRabbitTemplate().receive("shipping");
        Shiporder shiporder = SerializationUtils.deserialize(message.getBody());
        return Response.ok(shiporder.getOrderperson()).build();
    }

    private byte[] serialize(Shiporder shiporder) {
        return SerializationUtils.serialize(shiporder);
    }
}