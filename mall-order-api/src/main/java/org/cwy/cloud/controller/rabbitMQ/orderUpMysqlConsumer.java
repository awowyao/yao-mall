package org.cwy.cloud.controller.rabbitMQ;

import jakarta.annotation.Resource;
import org.cwy.cloud.mapper.orderMapper;
import org.cwy.cloud.model.PO.orderMsgPO;
import org.cwy.cloud.model.PO.orderPO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/21 16:28
 */
@Component
public class orderUpMysqlConsumer {
    @Resource
    private orderMapper orderMapper;

    @RabbitListener(queues = "orderUpMysqlQueue")
    public void orderUpMysql(orderPO order) {
        System.out.println(order);
        orderMapper.insert(order);

    }
}
