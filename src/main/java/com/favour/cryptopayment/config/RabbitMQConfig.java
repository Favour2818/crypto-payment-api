package com.favour.cryptopayment.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.queue}")
    private String queue;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    // ── Queue ────────────────────────────────────────────────────────────
    // durable=true means the queue survives a RabbitMQ restart
    @Bean
    public Queue paymentQueue() {
        return new Queue(queue, true);
    }

    // ── Exchange ─────────────────────────────────────────────────────────
    // DirectExchange routes messages to queues based on the routing key
    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange(exchange);
    }

    // ── Binding ──────────────────────────────────────────────────────────
    // Connects the queue to the exchange via the routing key
    @Bean
    public Binding binding(Queue paymentQueue, DirectExchange paymentExchange) {
        return BindingBuilder
                .bind(paymentQueue)
                .to(paymentExchange)
                .with(routingKey);
    }

    // ── Message Converter ────────────────────────────────────────────────
    // Serialises/deserialises messages as JSON instead of raw bytes
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
