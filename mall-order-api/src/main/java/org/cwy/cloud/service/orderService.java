package org.cwy.cloud.service;

import org.cwy.cloud.model.DTO.afterSalesDTO;
import org.cwy.cloud.model.DTO.orderDTO;
import org.cwy.cloud.model.DTO.orderMsgDTO;
import org.cwy.cloud.model.DTO.orderPage;
import org.cwy.cloud.model.PO.orderMsgPO;

import java.util.List;

public interface orderService {
    Integer getOrderId(Integer userId, Integer goodsId, Integer buyNub);

    Integer addOrder(orderDTO orderData) throws InterruptedException;


    List<orderMsgPO> getOrder(orderPage page);

    void deleteOrder(Integer id);

    void editOrder(orderMsgDTO orderMsg);

    void afterSales(afterSalesDTO afterSales);
}
