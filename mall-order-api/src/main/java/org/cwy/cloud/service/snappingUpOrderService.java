package org.cwy.cloud.service;

import org.cwy.cloud.DTO.orderDTO;

public interface snappingUpOrderService {
    Integer getSnappingOrderId(Integer userId, Integer goodsId, Integer buyNub);

    Integer addSnappingUpOrder(orderDTO orderData);
}
