package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.model.DTO.editSnappingUpDTO;
import org.cwy.cloud.model.DTO.snappingUpDTO;
import org.cwy.cloud.model.DTO.snappingUpPageDTO;
import org.cwy.cloud.model.PO.snappingUpPO;
import org.cwy.cloud.service.snappingUpService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/29 15:15
 */
@RestController
@RequestMapping("/storeSnappingApi")
public class storeSnappingUpController {
    @Resource
    private snappingUpService snappingUpService;

    @GetMapping("/snapping")
    public CommonResult<List<snappingUpPO>> getSnappingUp(snappingUpPageDTO snappingUpPage) {
        List<snappingUpPO> data = snappingUpService.GetSnappingUpList(snappingUpPage);
        return CommonResult.success(data);
    }

    @PostMapping("/snapping")
    public CommonResult<String> addSnappingUp(@RequestBody snappingUpDTO snappingUp) {
        Integer state = snappingUpService.addSnappingUp(snappingUp);
        if (state ==1){
            return CommonResult.success("1");
        }
        return CommonResult.failed();
    }

    @DeleteMapping("/snapping")
    public CommonResult deleteSnappingUp(Integer id) {

        return CommonResult.deleteSuccess();
    }

    @PutMapping("/snapping")
    public CommonResult putSnappingUp(@RequestBody editSnappingUpDTO snapping) {
        boolean statue = snappingUpService.editSnappingUpById(snapping);
        return CommonResult.editSuccess();
    }
}
