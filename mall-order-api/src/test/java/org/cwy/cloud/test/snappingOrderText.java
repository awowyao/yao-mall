package org.cwy.cloud.test;

import jakarta.annotation.Resource;
import org.cwy.cloud.model.PO.orderPO;
import org.cwy.cloud.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class snappingOrderText {
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//    @Resource
//    private RedisTemplate<Object, Object> redisHashTemplate;
    @Resource
    private RedisUtil RedisUtil;
    @Test
    public void RedisTest() {
        orderPO A = new orderPO();
        A.setOid(1);
        A.setState(1);
//        BoundHashOperations Hash = redisTemplate.boundHashOps("2");
//        Hash.put("Oid",A.getOid());
//        Hash.put("State",A.getState());

//        System.out.println(Hash.entries());
    }

    @Test
    public void BeanRedisTest() {
        orderPO A = new orderPO();
        A.setOid(1);
        A.setState(123);
        orderPO a = RedisUtil.ToBean("order", orderPO.class);
        System.out.println(a);
//        RedisUtil.setString("qqqq", "12333");
//        RedisUtil.setBean("order" ,A);
//        redisHashTemplate.opsForHash().put("myCache",A.getOid(),"654654");
//        System.out.println(redisHashTemplate.opsForHash().get("myCache", A.getOid()));

    }
}
