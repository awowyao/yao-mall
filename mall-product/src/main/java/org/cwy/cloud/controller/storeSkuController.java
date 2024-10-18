package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.DTO.SkuSettingDTO;
import org.cwy.cloud.model.DTO.addSkuDTO;
import org.cwy.cloud.model.VO.skuVO;
import org.cwy.cloud.service.skuService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: 商家管理sku
 * @date 2024/9/2 15:15
 */
@RestController
@RequestMapping("/storeSkuApi")
public class storeSkuController {
    @Resource
    private skuService skuService;

    @GetMapping("/sku")
    public CommonResult<List<skuVO>> getSku(Integer gid) {
        List<skuVO> skuData = skuService.getSku(gid);
        return CommonResult.success(skuData);
    }

    @PostMapping("/sku")
    public CommonResult addSku(@RequestBody addSkuDTO sku) {
        boolean skuData = skuService.addSku(sku);
        return CommonResult.addSuccess();
    }
    @PutMapping("/sku")
    public CommonResult edicSku(@RequestBody addSkuDTO sku) {
        boolean skuData = skuService.addSku(sku);
        return CommonResult.editSuccess();
    }
    
    /** 
     * @description: 删除sku
     * @param: sku 
     * @return: org.cwy.cloud.common.api.CommonResult 
     * @author yao
     * @date: 2024/9/3 14:29
     */
    @DeleteMapping ("/sku")
    public CommonResult DeleteSku(@RequestBody addSkuDTO sku) {
        boolean skuData = skuService.addSku(sku);
        return CommonResult.deleteSuccess();
    }
    
    /**
     * @description:  设置各个sku的价格
     * @param: sku
     * @return: org.cwy.cloud.common.api.CommonResult
     * @author yao
     * @date: 2024/9/3 14:28
     */
    @PostMapping("/settingPrice")
    public CommonResult settingPrice(@RequestBody SkuSettingDTO.skuSettingDTOS sku) {
        boolean skuData = skuService.settingPrice(sku);
        return CommonResult.addSuccess();
    }
}
