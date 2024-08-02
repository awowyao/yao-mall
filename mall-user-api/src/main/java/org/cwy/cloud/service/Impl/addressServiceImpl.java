package org.cwy.cloud.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.mapper.addressMapper;
import org.cwy.cloud.modle.DTO.addressDTO;
import org.cwy.cloud.modle.DTO.addressPageDTO;
import org.cwy.cloud.modle.PO.addressPO;
import org.cwy.cloud.result.MyAssert;
import org.cwy.cloud.service.addressService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/1 16:00
 */

@Service
public class addressServiceImpl implements addressService {
    @Resource
    private uniqidFeign uniqidFeign;
    @Resource
    private addressMapper addressMapper;
    @Override
    public void addAddress(addressDTO requestData) {
        addressPO address = addressPO.AddAddressPO(requestData);
        address.setUid(1);
        address.setId(uniqidFeign.GetId(1));
        MyAssert.isZero(addressMapper.insert(address));
    }

    @Override
    public List<addressPO> getAddress(addressPageDTO addressPage) {
        LambdaQueryWrapper<addressPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(addressPO::getUid, 1);
        Page<addressPO> page = new Page<>(addressPage.getPage(), addressPage.getPageSize());
        IPage<addressPO> iPage = addressMapper.selectPage(page, lambdaQueryWrapper);
        MyAssert.notEmpty(iPage.getRecords(), "查询为空");
        return iPage.getRecords();
    }

    @Override
    public void editAddress(addressDTO requestData) {
        addressPO address = addressPO.editAddressPO(requestData);
        MyAssert.isZero(addressMapper.updateById(address));
    }
}
