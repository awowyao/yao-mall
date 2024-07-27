package org.cwy.cloud.service;

import org.cwy.cloud.DTO.orderDTO;

public interface orderService {
    Integer getOrderId(Integer userId, Integer goodsId, Integer buyNub);

    Integer addOrder(orderDTO orderData) throws InterruptedException;


}
