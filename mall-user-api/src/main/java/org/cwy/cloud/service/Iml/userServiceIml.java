package org.cwy.cloud.service.Iml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;

import org.cwy.cloud.DTO.CouponsDTO;
import org.cwy.cloud.mapper.userMapper;

import org.cwy.cloud.modle.PO.addressPO;
import org.cwy.cloud.service.userService;
import org.springframework.stereotype.Service;

@Service
public class userServiceIml implements userService {

    @Resource
    private userMapper userMapper;
    @Override
    public addressPO GetAddress(Integer id) {
        return null;
    }

    @Override
    public int useCoupons(CouponsDTO coupons) {
        int state = userMapper.useCoupons(coupons);
        return state;
    }
}
