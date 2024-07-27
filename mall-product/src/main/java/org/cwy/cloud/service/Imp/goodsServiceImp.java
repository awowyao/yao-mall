package org.cwy.cloud.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.cwy.cloud.common.MyAssert;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.mapper.couponsMapper;
import org.cwy.cloud.mapper.goodsMapper;
import org.cwy.cloud.model.DTO.addGoodsDTO;
import org.cwy.cloud.model.DTO.editGoodsDTO;
import org.cwy.cloud.model.DTO.goodsDTO;
import org.cwy.cloud.model.DTO.goodsPageDTO;
import org.cwy.cloud.model.PO.couponsPO;
import org.cwy.cloud.model.PO.goodsPO;
import org.cwy.cloud.model.VO.goodsVO;
import org.cwy.cloud.service.goodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.cwy.cloud.common.goodsCommon.GOODS_DELETE;
import static org.cwy.cloud.common.goodsCommon.GOODS_UP;


@Service
public class goodsServiceImp implements goodsService {
    @Resource
    private goodsMapper goodsmapper;

    @Resource
    private couponsMapper couponsMapper;

    @Resource
    private uniqidFeign uniqidFeign;
    @Override
    public List<goodsVO> GetGoodsAll(Integer page, Integer pageSize) {

        LambdaQueryWrapper<goodsPO> Weapper = new LambdaQueryWrapper<>();
//        Weapper.eq(goodsPO::getId, 1);

        List<goodsPO> goods = goodsmapper.selectList(Weapper);
        List<goodsVO> goodsVoList = new ArrayList<goodsVO>();
        for(goodsPO i: goods) {
            goodsVO goodsVo = new goodsVO();
            BeanUtils.copyProperties(i, goodsVo);
            goodsVoList.add(goodsVo);
        }
        return goodsVoList;
    }

    @Override
    public goodsVO GetGoodsById(Integer goodsId) {
        goodsPO data = goodsmapper.selectById(goodsId);
        goodsVO goods = new goodsVO();
        BeanUtils.copyProperties(data, goods);
        return goods;
    }

    @Override
    public int subtractInventory(Integer goodsId, Integer buyNub) {
        LambdaUpdateWrapper<goodsPO> updata = new LambdaUpdateWrapper<>();
        updata.setSql("inventory = inventory-"+buyNub);
        updata.eq(goodsPO::getId,goodsId);
        updata.ge(goodsPO::getInventory, buyNub);
        return goodsmapper.update(null, updata);
    }

    @Override
    public couponsPO getCouponsById(Integer couponsId) {
        couponsPO couponsData = couponsMapper.selectById(couponsId);
        return couponsData;
    }

    @Override
    public List<goodsPO> getGoods(goodsPageDTO pageDTO) {
        Map<String, Object> ret = new HashMap<>();
        LambdaQueryWrapper<goodsPO> goodsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Page<goodsPO> userPage = new Page<>(pageDTO.getPage(), pageDTO.getPageSize());
        IPage<goodsPO> userIPage = goodsmapper.selectPage(userPage , goodsLambdaQueryWrapper);
        ret.put("Total", userIPage.getTotal());
        ret.put("Pages", userIPage.getPages());
        ret.put("Data", userIPage.getRecords());
        Assert.notEmpty(userIPage.getRecords(), "查询无结果");
//        System.out.println(userIPage.getRecords());
//        System.out.println("总页数： "+userIPage.getPages());
//        System.out.println("总记录数： "+userIPage.getTotal());
        return userIPage.getRecords();
    }

    @Override
    public boolean addGoods(addGoodsDTO goods) {
        goodsPO goodsData = new goodsPO();
        goodsData.setId(uniqidFeign.GetId(1));
        goodsData.setInventory(goods.getInventory());
        goodsData.setPrice(goods.getPrice());
        goodsData.setTitle(goods.getTitle());
        goodsData.setGoodsType(goods.getGoodsType());
        goodsData.setSynopsis(goods.getSynopsis());
        goodsData.setPhoto(goods.getPhoto());
        goodsData.setGoodsStatue(GOODS_UP);
        goodsData.setStoreId(1);
        goodsmapper.insert(goodsData);
        return true;
    }

    @Override
    public void deleteGoods(Integer goodsId) {
        LambdaUpdateWrapper<goodsPO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(goodsPO::getId, goodsId);
        lambdaUpdateWrapper.set(goodsPO::getGoodsStatue, GOODS_DELETE);
        goodsmapper.update(lambdaUpdateWrapper);
    }

    @Override
    public void editGoods(editGoodsDTO goods) {
        goodsPO goodsData = new goodsPO();
        BeanUtils.copyProperties(goods, goodsData);
        MyAssert.isZero(goodsmapper.updateById(goodsData));
    }
}
