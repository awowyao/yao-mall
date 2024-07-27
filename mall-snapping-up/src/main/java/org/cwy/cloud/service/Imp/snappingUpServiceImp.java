package org.cwy.cloud.service.Imp;

import jakarta.annotation.Resource;
import org.cwy.cloud.feign.goodsFeign;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.mapper.snappingUpMapper;
import org.cwy.cloud.model.DTO.snappingUpDTO;
import org.cwy.cloud.model.DTO.snappingUpOrderDTO;
import org.cwy.cloud.model.PO.snappingUpPO;
import org.cwy.cloud.service.snappingUpService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class snappingUpServiceImp implements snappingUpService {
    @Resource
    private snappingUpMapper snappingUpMapper;

    @Resource
    private uniqidFeign uniqidFeign;
    @Resource
    private goodsFeign goodsFeign;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public Integer addSnappingUp(snappingUpDTO snappingUp) {
        snappingUpPO data = new snappingUpPO();
        BeanUtils.copyProperties(snappingUp, data);
        if (goodsFeign.subtractInventory(snappingUp.getGid(), snappingUp.getStockNumber())==1){
            data.setId(uniqidFeign.GetId(1));
            data.setStockLimit(snappingUp.getLimit());
            redisTemplate.opsForValue().set("snappingUpStock_" + snappingUp.getGid(), snappingUp.getStockNumber());
            Map<Object,Object> goodsData = (Map<Object, Object>) goodsFeign.getGoodsById(snappingUp.getGid()).getData();
            BoundHashOperations<String, Object, Object> orderMsgHash = redisTemplate.boundHashOps("snappingUpData_" + snappingUp.getGid());

            orderMsgHash.put("price", snappingUp.getPrice());
            orderMsgHash.put("store", (Integer)goodsData.get("store"));
            orderMsgHash.put("id", (Integer)goodsData.get("id"));
            orderMsgHash.put("title", goodsData.get("title").toString());
            orderMsgHash.put("stock_limit", snappingUp.getLimit());

            return snappingUpMapper.insert(data);
        }
        return 0;


    }

    @Override
    public Integer addSnappingUpOrder(snappingUpOrderDTO snappingUpOrder) {
        return null;
    }
}
