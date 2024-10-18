package org.cwy.cloud.service.Impl;

import io.lettuce.core.ScriptOutputType;
import jakarta.annotation.Resource;
import org.cwy.cloud.DTO.CommonPage;
import org.cwy.cloud.constant.cartConstant;
import org.cwy.cloud.feign.goodsFeign;
import org.cwy.cloud.model.DTO.cartDTO;
import org.cwy.cloud.model.VO.carVO;
import org.cwy.cloud.result.MyAssert;
import org.cwy.cloud.service.carService;
import org.cwy.cloud.util.RedisUtil;
import org.cwy.cloud.utils.AuthTokenUtil;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.cwy.cloud.constant.cartConstant.*;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/27 16:25
 */

@Service
public class carServiceImpl implements carService {
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private goodsFeign goodsFeign;
    @Override
    public Map<String, Object> getCar(CommonPage page) {

        Map<String, Object> tokenAttributes = AuthTokenUtil.getTokenAttributes();
        assert tokenAttributes != null;
        int userId = Integer.parseInt(tokenAttributes.get("id").toString());
        Map<Object, Object> CarHaseAll = redisUtil.getHaseAll(cartRedis+userId);
        MyAssert.notEmpty(CarHaseAll, "购物车为空");

        List<carVO> carList = CarHaseAll.entrySet().stream().map(this::getCarData
        ).sorted(Comparator.comparingDouble(carVO::getCreateTime).reversed()).toList();

        Map<String, Object> rMap = new HashMap<>();
        rMap.put("data", carList);
        return rMap;
    }

    @Override
    public boolean addCar(cartDTO data) {
        Map<String, Object> tokenAttributes = AuthTokenUtil.getTokenAttributes();
        assert tokenAttributes != null;
        int userId = Integer.parseInt(tokenAttributes.get("id").toString());
        if (redisUtil.hasKeyHase(cartRedis+userId, data.getGid()+SPLIT+ data.getSuk())){
            String hase = redisUtil.getHase(cartRedis + userId, data.getGid() + SPLIT + data.getSuk());
            String[] split = hase.split(SPLIT);
            int buyNub = Integer.parseInt(split[cartConstant.buyNub]) + 1;
            redisUtil.setHase(cartRedis+userId, data.getGid()+SPLIT+ data.getSuk(), buyNub+SPLIT+System.currentTimeMillis());
            return true;
        }

        redisUtil.setHase(cartRedis+userId, data.getGid()+SPLIT+ data.getSuk(), defaultCartNub+SPLIT+System.currentTimeMillis());
        return true;
    }

    @Override
    public boolean EditCart(cartDTO data) {
        int userId = getUserId();
        if (redisUtil.hasKeyHase(cartRedis+userId, data.getGid()+SPLIT+ data.getSuk())){
            String hase = redisUtil.getHase(cartRedis + userId, data.getGid() + SPLIT + data.getSuk());
            String[] split = hase.split(SPLIT);
            int buyNub = Integer.parseInt(split[cartConstant.buyNub]);
            if (Objects.equals(data.getEditType(), cartAdd)) {
                buyNub += data.getNub();
            } else if (Objects.equals(data.getEditType(), cartMinus)) {
                if (data.getNub()<buyNub) {
                    buyNub -= data.getNub();
                }
                else {
                    buyNub = 1;
                }
            }
            redisUtil.setHase(cartRedis+userId, data.getGid()+SPLIT+ data.getSuk(), buyNub+SPLIT+System.currentTimeMillis());
            return true;
        }
        return false;
    }

    @Override
    public boolean DeleteCart(cartDTO data) {
        int userId = getUserId();
        if (redisUtil.hasKeyHase(cartRedis+userId, data.getGid()+SPLIT+ data.getSuk())){
            redisUtil.deleteKeyHase(cartRedis+userId, data.getGid()+SPLIT+ data.getSuk());

            return true;
        }
        return false;
    }

    public carVO getCarData(Map.Entry<Object, Object> car) {
        String[] carData = car.getKey().toString().split(SPLIT);
        String[] carValueList = car.getValue().toString().split(SPLIT);
        Integer gid = Integer.valueOf(carData[GID]);
        Integer suk = Integer.valueOf(carData[SUK]);
        Double createDate = Double.valueOf(carValueList[createTime]);
        Map<String, Object> goodsByIds = ((Map<String, Map<String, Object>>) goodsFeign.getGoodsByIds(gid).getData()).get("data");
        carVO cartData = new carVO();
        cartData.setNub(Integer.valueOf(carValueList[buyNub]));
        cartData.setGid((Integer) goodsByIds.get("id"));
        cartData.setTitle(goodsByIds.get("title").toString());
        cartData.setCover((String) goodsByIds.get("cover"));
        cartData.setCreateTime(createDate);
        cartData.setPrice((Double) goodsByIds.get("price"));
        cartData.setSuk(suk);
        cartData.setSukTitle("test");
        MyAssert.isTrue(true, "123456");
        return cartData;
    }

    public int getUserId() {
        Map<String, Object> tokenAttributes = AuthTokenUtil.getTokenAttributes();
        assert tokenAttributes != null;
        return Integer.parseInt(tokenAttributes.get("id").toString());
    }
}
