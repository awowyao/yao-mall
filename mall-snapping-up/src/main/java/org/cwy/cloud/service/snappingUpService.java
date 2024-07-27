package org.cwy.cloud.service;

import org.cwy.cloud.model.DTO.snappingUpDTO;
import org.cwy.cloud.model.DTO.snappingUpOrderDTO;

public interface snappingUpService {
    Integer addSnappingUp(snappingUpDTO snappingUp);

    Integer addSnappingUpOrder(snappingUpOrderDTO snappingUpOrder);
}
