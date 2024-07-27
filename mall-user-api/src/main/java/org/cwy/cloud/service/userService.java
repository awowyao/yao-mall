package org.cwy.cloud.service;


import org.cwy.cloud.DTO.CouponsDTO;
import org.cwy.cloud.modle.PO.addressPO;

public interface userService {
    addressPO GetAddress(Integer id);

    int useCoupons(CouponsDTO coupons);
}
