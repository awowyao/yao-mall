package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.DTO.afterSalesDTO;
import org.cwy.cloud.model.DTO.orderMsgDTO;
import org.cwy.cloud.model.DTO.orderPage;
import org.cwy.cloud.model.PO.orderMsgPO;
import org.cwy.cloud.service.orderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: 商家订单管理模块
 * @date 2024/7/27 16:18
 */
@RestController
@RequestMapping("/storeOrderApi")
public class storeController {

    @Resource
    private orderService orderService;

    @GetMapping("/order")
    public CommonResult getOrder(orderPage page) {
        List<orderMsgPO> data = orderService.getOrder(page);
        return CommonResult.success(data);
    }

    @DeleteMapping ("/order")
    public CommonResult deleteOrder(Integer id) {
        orderService.deleteOrder(id);
        return CommonResult.deleteSuccess();
    }

    @PutMapping ("/order")
    public CommonResult editOrder(@RequestBody orderMsgDTO orderMsg) {
        orderService.editOrder(orderMsg);
        return CommonResult.editSuccess();
    }

    @PostMapping("/afterSales")
    public CommonResult afterSales(@RequestBody afterSalesDTO afterSales) {
        orderService.afterSales(afterSales);
        return CommonResult.editSuccess();
    }
}
