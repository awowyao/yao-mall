package org.cwy.cloud.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CouponsDTO implements Serializable {
    private Integer couponsId;
    private Integer userId;
    private Integer id;
//    public CouponsDTO(){
//
//    }
    public CouponsDTO(Integer couponsId, Integer userId) {
        this.couponsId = couponsId;
        this.userId = userId;
    }



}
