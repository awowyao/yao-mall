package org.cwy.cloud.service;

import org.cwy.cloud.DTO.CommonPage;
import org.cwy.cloud.model.DTO.cartDTO;

import java.util.Map;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/27 16:25
 */
public interface carService {
    Map<String, Object> getCar(CommonPage page);

    boolean addCar(cartDTO data);

    boolean EditCart(cartDTO data);

    boolean DeleteCart(cartDTO data);
}
