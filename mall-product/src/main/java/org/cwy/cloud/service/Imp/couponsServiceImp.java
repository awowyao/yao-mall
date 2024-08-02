package org.cwy.cloud.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.mapper.couponsMapper;
import org.cwy.cloud.model.DTO.CouponsDTO;
import org.cwy.cloud.model.DTO.couponsPageDTO;
import org.cwy.cloud.model.DTO.editCouponsDTO;
import org.cwy.cloud.model.PO.couponsPO;
import org.cwy.cloud.result.MyAssert;
import org.cwy.cloud.service.couponsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/29 13:13
 */
@Service
public class couponsServiceImp implements couponsService {
    @Resource
    private couponsMapper couponsMapper;

    @Resource
    private uniqidFeign uniqidFeign;
    @Override
    public List<couponsPO> getCoupons(couponsPageDTO couponsPage) {
        LambdaQueryWrapper<couponsPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Page<couponsPO> Page = new Page<>(couponsPage.getPage(), couponsPage.getPageSize());
        IPage<couponsPO> IPage = couponsMapper.selectPage(Page, lambdaQueryWrapper);
        MyAssert.notEmpty(IPage.getRecords(), "查询无内容");
        return IPage.getRecords();
    }

    @Override
    @Transactional
    public void addCoupons(CouponsDTO coupons) {
        couponsPO couponsPO = new couponsPO();
        couponsPO.setId(uniqidFeign.GetId(1));
        BeanUtils.copyProperties(coupons, couponsPO);
        MyAssert.isZero(couponsMapper.insert(couponsPO));

        if (coupons.getGoodsList() != null) {
            for (Integer goodsId: coupons.getGoodsList()) {
                couponsMapper.addGoodsAndCoupons(uniqidFeign.GetId(1), goodsId, couponsPO.getId());
            }
        }

    }

    @Override
    public void editCoupons(editCouponsDTO couponsDTO) {
        couponsPO couponsPO = new couponsPO();
        BeanUtils.copyProperties(couponsDTO, couponsPO);
        MyAssert.isZero(couponsMapper.updateById(couponsPO));
    }
    @Transactional
    public void editCouponsGoodsList(editCouponsDTO couponsDTO) {
        if (couponsDTO.getGoodsList()!=null) {
            for (Integer goodsId: couponsDTO.getGoodsList()) {
                couponsMapper.addGoodsAndCoupons(uniqidFeign.GetId(1), goodsId, couponsDTO.getId());
            }
        }
    }
}
