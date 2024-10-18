package org.cwy.cloud.service.Impl;

import jakarta.annotation.Resource;

import org.cwy.cloud.DTO.CouponsDTO;
import org.cwy.cloud.common.api.CommonResult;
import org.cwy.cloud.feign.couponsFeign;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.mapper.userMapper;

import org.cwy.cloud.modle.PO.addressPO;
import org.cwy.cloud.service.userService;
import org.cwy.cloud.util.RedisUtil;
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

    @Resource
    private RedisUtil redisUtil;
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

    @Override
    public Integer concernStore(Integer userId, Integer storeId) {
        redisUtil.setZSet("like_"+userId, storeId, System.currentTimeMillis());
        return userMapper.concernStore(uniqidFeign.GetId(1) ,userId, storeId);
    }

    @Override
    public List<Integer> storeGetFens(Integer storeId) {
        return userMapper.storeGetFens(storeId);
    }
}
