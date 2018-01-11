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


@Path("/hello")
@ApplicationScoped
public class HelloWorldEndpoint {

	@Inject
	private RabbitAdmin rabbitAdmin;

	@Inject
	private MessageConverter messageConverter;

	@GET
	@Produces("text/plain")
	public Response doGet() {
		rabbitAdmin.initialize();
		rabbitAdmin.declareQueue(new Queue("shipping"));
		final Shiporder shiporder = createShipping();
		final MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_XML);
		final Message msg = messageConverter.toMessage(shiporder, messageProperties);
		rabbitAdmin.getRabbitTemplate().send("shipping", msg);
		return Response.ok("Message sent!").build();
	}


	private Shiporder createShipping() {
		final Shiporder shiporder = new Shiporder();
		shiporder.setOrderid("111");
		shiporder.setOrderperson("Marian Shipper");
		final Shiporder.Shipto shipto = new Shiporder.Shipto();
		shipto.setAddress("Shipperska 11");
		shipto.setCity("Wieden");
		shipto.setCountry("Austria");
		shipto.setName("Infonova GmbH");
		shiporder.setShipto(shipto);
		return shiporder;
	}
}