package org.cwy.cloud.service;

import org.cwy.cloud.model.DTO.orderDTO;

public interface snappingUpOrderService {

    Integer addSnappingUpOrder(orderDTO orderData);

    Integer getSnappingOrderId(Integer userId, Integer goodsId, Integer snapingId, Integer sku, Integer buyNub);
}
