package org.cwy.cloud.service;

import org.cwy.cloud.model.DTO.CouponsDTO;
import org.cwy.cloud.model.DTO.couponsPageDTO;
import org.cwy.cloud.model.DTO.editCouponsDTO;
import org.cwy.cloud.model.PO.couponsPO;

import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/7/29 13:13
 */
public interface couponsService {
    List<couponsPO> getCoupons(couponsPageDTO couponsPage);

    void addCoupons(CouponsDTO coupons);

    void editCoupons(editCouponsDTO couponsDTO);
}
