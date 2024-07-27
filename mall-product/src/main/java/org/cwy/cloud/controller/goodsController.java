package org.cwy.cloud.controller;


import jakarta.annotation.Resource;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.PO.goodsPO;
import org.cwy.cloud.model.VO.goodsVO;
import org.cwy.cloud.service.goodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goodsApi")
public class goodsController {

    @Resource
    private goodsService goodsservice;

    @GetMapping("/getGoods")
    public CommonResult<List<goodsVO>> GetGoods(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        List<goodsVO> goods = goodsservice.GetGoodsAll(page, pageSize);
        return CommonResult.success(goods);
    }
    @GetMapping("/getGoodsById")
    public CommonResult getGoodsById(@RequestParam(value = "goosdId", defaultValue = "1") Integer goosdId){
        goodsVO goods = goodsservice.GetGoodsById(goosdId);
        return CommonResult.success(goods);
    }

}
