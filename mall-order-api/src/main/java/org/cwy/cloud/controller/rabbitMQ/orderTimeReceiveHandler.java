package org.cwy.cloud.controller.rabbitMQ;

import jakarta.annotation.Resource;
import org.cwy.cloud.PO.orderMsgPO;
import org.cwy.cloud.PO.orderPO;
import org.cwy.cloud.mapper.orderMapper;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class orderTimeReceiveHandler {

    @Resource
    private orderMapper orderMapper;

    @RabbitListener(queues = "user.order.receive_queue")
    public void receive_email(orderMsgPO orderMsg) {
        System.out.println(orderMsg.getOId());
        orderPO orderPO = orderMapper.selectById(orderMsg.getOId());
        System.out.println(orderPO);
        if (orderPO.getState() == 1) {
            orderPO.setState(5);
        }
        orderMapper.updateById(orderPO);
//        System.out.println();
//        System.out.println(channel);
//        System.out.println("QUEUE_INFORM_EMAIL msg"+body);
    }
}
