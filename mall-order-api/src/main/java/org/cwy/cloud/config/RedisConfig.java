package org.cwy.cloud.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

//    RedisConfig(){
//        //打开autotype功能,需要强转的类一次添加其后
//        ParserConfig.getGlobalInstance()
//                .addAccept("org.cwy.cloud.model.PO.orderPO");
//    }

//    @Bean
//    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
//        RedisTemplate<Object,Object> template =new RedisTemplate<>();
//        //template .setConnectionFactory(redisConnectionFactory);
//        FastJsonRedisSerializer<Object> fastJsonRedisSerializer=new FastJsonRedisSerializer<>(Object.class);
//        template.setValueSerializer(fastJsonRedisSerializer);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(fastJsonRedisSerializer);
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        return template;
//    }


}

