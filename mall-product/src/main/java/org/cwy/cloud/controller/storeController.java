package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Update;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.DTO.addGoodsDTO;
import org.cwy.cloud.model.DTO.editGoodsDTO;
import org.cwy.cloud.model.DTO.goodsDTO;
import org.cwy.cloud.model.DTO.goodsPageDTO;
import org.cwy.cloud.model.PO.goodsPO;
import org.cwy.cloud.service.goodsService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: 商家端的商品模块
 * @date 2024/7/26 15:24
 */

@RestController
@RequestMapping("/storeApi")
public class storeController {
    @Resource
    private goodsService goodsService;


    @GetMapping ("/goods")
    public CommonResult<List<goodsPO>> getGoods(goodsPageDTO pageDTO){
        List<goodsPO> data = goodsService.getGoods(pageDTO);
        return CommonResult.success(data);
    }

    @PostMapping("/goods")
    public CommonResult addGoods(@RequestBody addGoodsDTO goods){
        boolean statue = goodsService.addGoods(goods);
        return CommonResult.addSuccess();
    }

    @PutMapping("/goods")
    public CommonResult editGoods(@RequestBody editGoodsDTO goods){
        goodsService.editGoods(goods);
        System.out.println(123123);
        return CommonResult.editSuccess();
    }

    @DeleteMapping ("/goods")
    public CommonResult deleteGoods(Integer goodsId){
        goodsService.deleteGoods(goodsId);
        return CommonResult.deleteSuccess();
    }
}
