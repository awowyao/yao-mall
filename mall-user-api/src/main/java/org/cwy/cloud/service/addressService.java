package org.cwy.cloud.service;

import org.cwy.cloud.modle.DTO.addressDTO;
import org.cwy.cloud.modle.DTO.addressPageDTO;
import org.cwy.cloud.modle.PO.addressPO;

import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/1 16:02
 */
public interface addressService {
    void addAddress(addressDTO requestData);

    List<addressPO> getAddress(addressPageDTO addressPage);

    void editAddress(addressDTO requestData);
}
