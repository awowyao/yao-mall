package org.cwy.cloud.service.Impl;

import jakarta.annotation.Resource;

import org.cwy.cloud.DTO.CouponsDTO;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.feign.couponsFeign;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.mapper.userMapper;

import org.cwy.cloud.modle.PO.addressPO;
import org.cwy.cloud.service.userService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class userServiceImpl implements userService {

    @Resource
    private userMapper userMapper;
    @Resource
    private uniqidFeign uniqidFeign;
    @Resource
    private couponsFeign couponsFeign;
    @Override
    public addressPO GetAddress(Integer id) {
        return null;
    }

    @Override
    public int useCoupons(CouponsDTO coupons) {
        int state = userMapper.useCoupons(coupons);
        return state;
    }

    @Override
    public void getCoupons(CouponsDTO coupons) {
        coupons.setId(uniqidFeign.GetId(1));
        System.out.println(coupons);
        Integer i =userMapper.insertCoupons(coupons);
    }

    @Override
    public List<Map<String, Object>> getCouponsList() {
        List<Integer> cidList = userMapper.getCouponsListByUid(1);
        List<Map<String, Object>> rData = new ArrayList<>();
        for (Integer cid: cidList) {
            CommonResult data = couponsFeign.getCouponsById(cid);
            rData.add((Map<String, Object>)data.getData());
        }

        return rData;
    }
}
