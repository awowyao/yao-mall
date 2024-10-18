package org.cwy.cloud.service.Imp;

import com.alibaba.nacos.shaded.com.google.gson.JsonObject;
import jakarta.annotation.Resource;


import org.cwy.cloud.feign.AuthcentFeign;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.model.UserAuto;
import org.cwy.cloud.service.authService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class authServiceImp implements authService {
    @Autowired
    @Lazy
    private AuthcentFeign centerFeign;
    @Resource
    @Lazy
    private uniqidFeign uniqidFeign;

    @Override
    public Integer getUserMsg() {

        CompletableFuture<Integer> F = CompletableFuture.supplyAsync(
                ()->uniqidFeign.GetId(1));
        CompletableFuture<JsonObject> U = CompletableFuture.supplyAsync(
                ()->centerFeign.getUserAuto());
        try {
            System.out.println(U.get(1, TimeUnit.MINUTES));
            return F.get(1, TimeUnit.MINUTES);
        }catch (Exception ex){
            System.out.println(ex);
            return 2;
        }

//        System.out.println();
//        return uniqidFeign.GetId(1);
    }
}
