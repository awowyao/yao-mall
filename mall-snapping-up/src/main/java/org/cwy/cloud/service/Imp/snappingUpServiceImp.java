package org.cwy.cloud.service.Imp;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.cwy.cloud.feign.goodsFeign;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.mapper.skuMapper;
import org.cwy.cloud.mapper.snappingUpMapper;
import org.cwy.cloud.model.DTO.editSnappingUpDTO;
import org.cwy.cloud.model.DTO.snappingUpDTO;
import org.cwy.cloud.model.DTO.snappingUpOrderDTO;
import org.cwy.cloud.model.DTO.snappingUpPageDTO;
import org.cwy.cloud.model.PO.goodsSkuPO;
import org.cwy.cloud.model.PO.snappingUpPO;
import org.cwy.cloud.result.MyAssert;
import org.cwy.cloud.service.snappingUpService;
import org.cwy.cloud.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.cwy.cloud.model.snappingUpStatic.RedisSnappingUpKey;
import static org.cwy.cloud.model.snappingUpStatic.snappingUpStockKey;

@Service
public class snappingUpServiceImp implements snappingUpService {
    @Resource
    private snappingUpMapper snappingUpMapper;

    @Resource
    private uniqidFeign uniqidFeign;
    @Resource
    private goodsFeign goodsFeign;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private skuMapper skuMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public Integer addSnappingUp(snappingUpDTO snappingUp) {
        snappingUpPO data = new snappingUpPO();
        BeanUtils.copyProperties(snappingUp, data);
        if (goodsFeign.subtractInventory(snappingUp.getGid(), snappingUp.getStockNumber())==1){
            data.setId(uniqidFeign.GetId(1));
            data.setStockLimit(snappingUp.getLimit());

            redisUtil.execute(new SessionCallback<List<Object>>(){
                @Override
                public List<Object> execute(RedisOperations operations) throws DataAccessException {
                    operations.multi();
                    redisUtil.setBean(RedisSnappingUpKey + snappingUp.getGid(), data);
                    redisUtil.set(snappingUpStockKey + snappingUp.getGid(), snappingUp.getStockNumber());
                    return redisUtil.exec();
                }
            });

//            Map<Object,Object> goodsData = (Map<Object, Object>) goodsFeign.getGoodsByIds(snappingUp.getGid()).getData();
//            BoundHashOperations<String, Object, Object> orderMsgHash = redisTemplate.boundHashOps("snappingUpData_" + snappingUp.getGid());

//            orderMsgHash.put("price", snappingUp.getPrice());
//            orderMsgHash.put("store", (Integer)goodsData.get("store"));
//            orderMsgHash.put("id", (Integer)goodsData.get("id"));
//            orderMsgHash.put("title", goodsData.get("title").toString());
//            orderMsgHash.put("stock_limit", snappingUp.getLimit());

            return snappingUpMapper.insert(data);
        }
        return 0;


    }

    @Override
    public Integer addSnappingUpOrder(snappingUpOrderDTO snappingUpOrder) {
        return null;
    }

    @Override
    public List<snappingUpPO> GetSnappingUpList(snappingUpPageDTO snappingUpPage) {
        LambdaQueryWrapper<snappingUpPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Page<snappingUpPO> page = new Page<>(snappingUpPage.getPage(), snappingUpPage.getPageSize());
        IPage<snappingUpPO> ISnappingPage = snappingUpMapper.selectPage(page, lambdaQueryWrapper);
        MyAssert.notEmpty(ISnappingPage.getRecords(), "查询未空");
        return ISnappingPage.getRecords();
    }

    @Override
    public boolean editSnappingUpById(editSnappingUpDTO snapping) {
        snappingUpPO snappingUpPO = new snappingUpPO();
        BeanUtils.copyProperties(snapping, snappingUpPO);
        snappingUpMapper.updateById(snappingUpPO);
        return false;
    }

    @Override
    public Map<String, Object> getSnappingUp(snappingUpPageDTO snappingUpPageDTO) {
        LambdaQueryWrapper<snappingUpPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.le(snappingUpPO::getBeginTime, snappingUpPageDTO.getBeginTime());
//        lambdaQueryWrapper.ge(snappingUpPO::getEndTime, snappingUpPageDTO.getEndTime());
        Page<snappingUpPO> page = new Page<>(snappingUpPageDTO.getPage(), snappingUpPageDTO.getPageSize(), true);
        IPage<snappingUpPO> iPage = snappingUpMapper.selectPage(page, lambdaQueryWrapper);
        Map<String, Object> snappingMap = new HashMap<>();
        snappingMap.put("data", iPage.getRecords());
        snappingMap.put("total", iPage.getTotal());
        snappingMap.put("pages", iPage.getPages());
        return snappingMap;
    }

    @Override
    public Map<String, Object> getSnappingUpById(Integer snappingId) {
        Map<String, Object> snappingUpMap = new HashMap<>();
        snappingUpPO snappingUp = snappingUpMapper.selectById(snappingId);
        Map<String, Object> goodsMap = (Map<String, Object>) goodsFeign.getGoodsByIds(snappingUp.getGid()).getData();
        snappingUpMap.put("snappingData", snappingUp);
        snappingUpMap.put("goodsData", goodsMap);
        return snappingUpMap;
    }

    @Override
    public Double getSkuPrice(Integer skuId) {
        goodsSkuPO sku = skuMapper.selectById(skuId);
        return sku.getPrice();
    }
}
