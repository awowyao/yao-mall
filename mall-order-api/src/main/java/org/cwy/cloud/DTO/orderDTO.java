package org.cwy.cloud.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class orderDTO {
    private Integer oid;
    private Integer buyUserId;
    private Integer orderMsgId;
    private Integer goodsId;
    private Integer returnableId;
    private Integer couponsId;
    private String returnableText;
}
