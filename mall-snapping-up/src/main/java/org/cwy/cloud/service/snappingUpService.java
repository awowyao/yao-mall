package org.cwy.cloud.service;

import org.cwy.cloud.model.DTO.editSnappingUpDTO;
import org.cwy.cloud.model.DTO.snappingUpDTO;
import org.cwy.cloud.model.DTO.snappingUpOrderDTO;
import org.cwy.cloud.model.DTO.snappingUpPageDTO;
import org.cwy.cloud.model.PO.snappingUpPO;

import java.util.List;
import java.util.Map;

public interface snappingUpService {
    Integer addSnappingUp(snappingUpDTO snappingUp);

    Integer addSnappingUpOrder(snappingUpOrderDTO snappingUpOrder);

    List<snappingUpPO> GetSnappingUpList(snappingUpPageDTO snappingUpPage);

    boolean editSnappingUpById(editSnappingUpDTO snapping);

    Map<String, Object> getSnappingUp(snappingUpPageDTO snappingUpPageDTO);

    Map<String, Object> getSnappingUpById(Integer snappingId);

    Double getSkuPrice(Integer skuId);
}
