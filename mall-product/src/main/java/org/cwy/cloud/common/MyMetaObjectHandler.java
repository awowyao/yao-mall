package org.cwy.cloud.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Primary
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
//        System.out.println("创建时间");
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
//        this.setFieldValByName("updateUser", "", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        System.out.println("更新时间");
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
//        this.setFieldValByName("updateUser", "", metaObject);
    }
}
