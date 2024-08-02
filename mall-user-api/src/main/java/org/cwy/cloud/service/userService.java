package org.cwy.cloud.service;


import org.cwy.cloud.DTO.CouponsDTO;
import org.cwy.cloud.DTO.commonCouponsPO;
import org.cwy.cloud.modle.PO.addressPO;

import java.util.List;
import java.util.Map;

public interface userService {
    addressPO GetAddress(Integer id);

    int useCoupons(CouponsDTO coupons);

    void getCoupons(CouponsDTO coupons);

    List<Map<String, Object>> getCouponsList();
}
