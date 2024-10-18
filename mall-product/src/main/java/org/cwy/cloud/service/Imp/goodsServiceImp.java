package org.cwy.cloud.service.Imp;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.cwy.cloud.common.MyAssert;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.feign.userFeign;
import org.cwy.cloud.mapper.couponsMapper;
import org.cwy.cloud.mapper.goodsMapper;
import org.cwy.cloud.mapper.skuMapper;
import org.cwy.cloud.model.DTO.addGoodsDTO;
import org.cwy.cloud.model.DTO.editGoodsDTO;
import org.cwy.cloud.model.DTO.goodsPageDTO;
import org.cwy.cloud.model.PO.couponsPO;
import org.cwy.cloud.model.PO.goodsPO;
import org.cwy.cloud.model.VO.goodsVO;
import org.cwy.cloud.service.goodsService;
import org.cwy.cloud.util.RedisUtil;
import org.cwy.cloud.utils.AuthTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.cwy.cloud.common.goodsCommon.GOODS_DELETE;
import static org.cwy.cloud.common.goodsCommon.GOODS_UP;
import static org.cwy.cloud.model.productStatic.UserLikeBox;


@Service
public class goodsServiceImp implements goodsService {
    @Resource
    private goodsMapper goodsmapper;

    @Resource
    private couponsMapper couponsMapper;

    @Resource
    private uniqidFeign uniqidFeign;
    @Resource
    private skuMapper sukMapper;
    @Resource
    private userFeign userFeign;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    private Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public List<goodsVO> GetGoodsAll(goodsPageDTO goodsPage) {

        LambdaQueryWrapper<goodsPO> Weapper = new LambdaQueryWrapper<>();
//        Weapper.eq(goodsPO::getId, 1);
        Page<goodsPO> page = new Page<>(goodsPage.getPage(), goodsPage.getPageSize());
        IPage<goodsPO> IGoodsPage = goodsmapper.selectPage(page, Weapper);
        List<goodsVO> goodsVoList = new ArrayList<goodsVO>();
        for(goodsPO i: IGoodsPage.getRecords()) {
            goodsVO goodsVo = new goodsVO();
            BeanUtils.copyProperties(i, goodsVo);
            goodsVoList.add(goodsVo);
        }
        return goodsVoList;
    }

    @Override
    public Map<String, Object> GetGoodsById(Integer goodsId) {
        Map<String, Object> R = new HashMap<>();
        goodsPO data = goodsmapper.selectById(goodsId);
        List<couponsPO> couponsPOList = couponsMapper.getCouponsByGoodsId(goodsId);
        R.put("couponsList", couponsPOList);
        goodsVO goods = new goodsVO();

        BeanUtils.copyProperties(data, goods);
        R.put("data", goods);
        return R;
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
    @Transactional
    public boolean addGoods(addGoodsDTO goods) throws ExecutionException, InterruptedException {
        goodsPO goodsData = new goodsPO();
        Integer goodsId = uniqidFeign.GetId(1);
        goodsData.setId(goodsId);
        goodsData.setInventory(goods.getInventory());
        if (goods.getPrice() > 0) {
            goodsData.setPrice(goods.getPrice());
        }else {
            goodsData.setPrice(0);
        }

        if (goods.getSkuType() == 0) {
            goodsData.setPrice(-1);
        }
        goodsData.setTitle(goods.getTitle());
        goodsData.setGoodsType(goods.getGoodsType());
        goodsData.setSynopsis(goods.getSynopsis());
        goodsData.setPhoto(goods.getPhoto());
        goodsData.setGoodsStatue(GOODS_UP);
//        AuthTokenUtil.getUserId();
        goodsData.setStoreId(1);
        List<Integer> userId =  userFeign.storeGetFens(1);
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(()->{
            goodsmapper.insert(goodsData);
            return null;
        }, threadPoolExecutor);

//        CompletableFuture.anyOf(completableFuture).thenRun(()->{
//            if (goods.getSuk1() != null) {
//                CompletableFuture.supplyAsync(() -> {
//                    goods.getSuk1().forEach(suk -> {
//                        Integer s = sukMapper.addSukOne(uniqidFeign.GetId(1), goodsId, suk);
//                    });
//                    return null;
//                }, threadPoolExecutor);
//            }
//
//            if (goods.getSuk2() != null) {
//                CompletableFuture.supplyAsync(()->{
//                    goods.getSuk2().forEach(suk -> {
//                        Integer s = sukMapper.addSukT(uniqidFeign.GetId(1), goodsId, suk);
//                    });
//                    return null;
//                }, threadPoolExecutor);
//            }
//        }).join();

        userId.forEach(id ->{
            redisUtil.setZSet(UserLikeBox+id, goodsData.getId(), System.currentTimeMillis());
        });

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

    @Override
    public Map<String, Object> GetGoodsByStoreId(goodsPageDTO page) {
        Map<String, Object> goodsMap = new HashMap<>();
        LambdaUpdateWrapper<goodsPO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(goodsPO::getStoreId, page.getStoreId());
        Page<goodsPO> goodsPage = new Page<>(page.getPage(), page.getPageSize());
        IPage<goodsPO> goodsPOIPage = goodsmapper.selectPage(goodsPage, lambdaUpdateWrapper);
        MyAssert.notEmpty(goodsPOIPage.getRecords(), "查询为空");
        goodsMap.put("data", goodsPOIPage.getRecords());
        goodsMap.put("total", goodsPOIPage.getTotal());
        goodsMap.put("pages", goodsPOIPage.getPages());
        return goodsMap;
    }

    @Override
    public couponsPO checkCouponsById(Integer gid, Integer couponsId) {
        Integer i = couponsMapper.checkCouponsById(gid, couponsId);
        if (i>0) {
            couponsPO couponsData = couponsMapper.selectById(couponsId);
            return couponsData;
        }
        return null;
    }

    @Override
    public Map<String, Object> getLikeStoreNewGoods(goodsPageDTO goodsPage) {
        Map<String, Object> tokenAttributes = AuthTokenUtil.getTokenAttributes();
        String UserId = (String) tokenAttributes.get("id");
        MyAssert.notEmpty(tokenAttributes, "123");
        Set<Object> goodsIdSet = redisUtil.getZSet(UserLikeBox + UserId);
        List<goodsPO> goodsPOList = new ArrayList<>();
        goodsIdSet.forEach(id -> {
            goodsPOList.add(
                    goodsmapper.selectById(Integer.valueOf(id.toString()))
            );
        });
        List<goodsVO> goodsVOS = goodsPOList.stream().map(goodsPO ->
            BeanUtil.copyProperties(goodsPO, goodsVO.class)
        ).toList();

        Map<String, Object> rMap = new HashMap<>();
        rMap.put("data", goodsVOS);
        return rMap;
    }
}
