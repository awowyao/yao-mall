package org.cwy.cloud.test;

import jakarta.annotation.Resource;
import org.cwy.cloud.PO.orderPO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

@SpringBootTest
public class snappingOrderText {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    public void RedisTest() {
        orderPO A = new orderPO();
        A.setOid(1);
        A.setState(1);
        BoundHashOperations Hash = redisTemplate.boundHashOps("2");
        Hash.put("Oid",A.getOid());
        Hash.put("State",A.getState());

        System.out.println(Hash.entries());
    }
}
