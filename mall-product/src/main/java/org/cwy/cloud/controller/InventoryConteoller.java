package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.service.goodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryConteoller {
    @Resource
    private goodsService goodsService;

    @GetMapping("/subtractInventory")
    public int subtractInventory(@RequestParam Integer goodsId,
                                 @RequestParam Integer buyNub) {
        return goodsService.subtractInventory(goodsId, buyNub);
    }

}
