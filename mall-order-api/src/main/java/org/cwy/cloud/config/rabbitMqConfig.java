package org.cwy.cloud.config;


import org.springframework.amqp.core.*;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class rabbitMqConfig {

    // 异步更新数据库的交换机
    private static final String orderUpMysqlExchange = "orderUpMysqlExchange";
    // 异步更新数据库的队列
    private static final String orderUpMysqlQueue = "orderUpMysqlQueue";
    // 异步更新数据库的key
    private static final String orderUpMysqlKey = "order.up.mysql.*";
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    /**
     * @description:  异步保存Mysql数据库路由
     * @param:
     * @return: org.springframework.amqp.core.TopicExchange
     * @author yao
     * @date: 2024/8/21 15:54
     */
    @Bean
    public TopicExchange orderUpMysqlExchange() {
        return new TopicExchange(orderUpMysqlExchange);
    }

    @Bean
    public Queue orderUpMysqlQueue() {
        return new Queue(orderUpMysqlQueue,true,false,false);
    }

    @Bean
    public Binding orderUpMysqlBinding() {
        return BindingBuilder.bind(orderUpMysqlQueue()).to(orderUpMysqlExchange()).with(orderUpMysqlKey);
    }

    @Bean
    public DirectExchange userOrderDelayExchange() {
        return new DirectExchange("user.order.delay_exchange");
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue userOrderDelayQueue() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("x-dead-letter-exchange", "user.order.receive_exchange");
        map.put("x-dead-letter-routing-key", "user.order.receive_key");
        return new Queue("user.order.delay_queue", true, false, false, map);
    }

    /**
     * 给死信队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding userOrderDelayBinding() {
        return BindingBuilder.bind(userOrderDelayQueue()).to(userOrderDelayExchange()).with("user.order.delay_key");
    }

    /**
     * 死信接收交换机
     *
     * @return
     */
    @Bean
    public DirectExchange userOrderReceiveExchange() {
        return new DirectExchange("user.order.receive_exchange");
    }

    /**
     * 死信接收队列
     *
     * @return
     */
    @Bean
    public Queue userOrderReceiveQueue() {
        return new Queue("user.order.receive_queue");
    }

    /**
     * 死信交换机绑定消费队列
     *
     * @return
     */
    @Bean
    public Binding userOrderReceiveBinding() {
        return BindingBuilder.bind(userOrderReceiveQueue()).to(userOrderReceiveExchange()).with("user.order.receive_key");
    }



//    @Bean
//    orderTimeReceiveHandler getReceive() {
//        return new orderTimeReceiveHandler();
//    }
}
