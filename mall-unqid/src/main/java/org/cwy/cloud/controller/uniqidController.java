package org.cwy.cloud.controller;

import jakarta.annotation.Resource;
import org.cwy.cloud.sevise.segmentSevice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class uniqidController {
    @Resource
    private segmentSevice segmentSevice;

    @GetMapping("/GetId")
    public int GetId(@RequestParam(value="tag", defaultValue = "1") Integer tag) throws InterruptedException {
        return segmentSevice.getId(tag);

    }
}
