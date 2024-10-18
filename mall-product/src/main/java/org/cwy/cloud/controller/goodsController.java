package org.cwy.cloud.controller;


import jakarta.annotation.Resource;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.DTO.goodsPageDTO;
import org.cwy.cloud.model.PO.goodsPO;
import org.cwy.cloud.model.VO.goodsVO;
import org.cwy.cloud.service.goodsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goodsApi")
public class goodsController {

    @Resource
    private goodsService goodsservice;

    @GetMapping("/goods")
    public CommonResult<List<goodsVO>> GetGoods(goodsPageDTO goodsPageDTO){
        List<goodsVO> goods = goodsservice.GetGoodsAll(goodsPageDTO);
        return CommonResult.success(goods);
    }
    @GetMapping("/goods/{goodsId}")
    public CommonResult getGoodsById(@PathVariable("goodsId") Integer goodsId){
        Map<String, Object> goods = goodsservice.GetGoodsById(goodsId);
        return CommonResult.success(goods);
    }
    @GetMapping("/getGoodsById")
    public CommonResult getGoodsByIds(@RequestParam Integer goodsId){
        Map<String, Object> goods = goodsservice.GetGoodsById(goodsId);
        return CommonResult.success(goods);
    }
    @GetMapping("/storeGoods")
    CommonResult storeGoods(goodsPageDTO goodsPage){
        Map<String, Object> goods = goodsservice.GetGoodsByStoreId(goodsPage);
        return CommonResult.success(goods);
    }

    @GetMapping("/getLikeStoreNewGoods")
    CommonResult getLikeStoreNewGoods(goodsPageDTO goodsPage){
        Map<String, Object> goods = goodsservice.getLikeStoreNewGoods(goodsPage);
        return CommonResult.success(goods);
    }

}
