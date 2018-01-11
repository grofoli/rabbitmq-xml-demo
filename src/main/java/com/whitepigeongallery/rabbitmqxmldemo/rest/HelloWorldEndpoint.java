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


@Path("/hello")
@ApplicationScoped
public class HelloWorldEndpoint {

	@Inject
	private RabbitAdmin rabbitAdmin;

	@GET
	@Produces("text/plain")
	public Response doGet() {
		rabbitAdmin.initialize();
		rabbitAdmin.declareQueue(new Queue("shipping"));
		final Shiporder shiporder = createShipping();
		Message message = MessageBuilder.withBody(serialize(shiporder))
				.setContentType(MessageProperties.CONTENT_TYPE_XML)
				.setMessageId("123")
				.setHeader("bar", "baz")
				.build();
		rabbitAdmin.getRabbitTemplate().send("shipping", message);
		return Response.ok("Hello from WildFly Swarm!").build();
	}

	private byte[] serialize(Shiporder shiporder) {
		return SerializationUtils.serialize(shiporder);
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