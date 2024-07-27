package org.cwy.cloud.model.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class snappingUpOrderDTO implements Serializable {
    snappingUpOrderDTO(){}
    private Integer sid;
    private Integer buyNub;
    private Integer buyUserId;
    private Integer orderMsgId;
    private Integer goodsId;
    private Integer returnableId;
    private Integer couponsId;
    private String returnableText;
}
