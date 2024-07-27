package org.cwy.cloud.DTO;

public class CouponsDTO {
    Integer couponsId;
    Integer userId;
    public CouponsDTO(){

    }
    public CouponsDTO(Integer couponsId, Integer userId) {
        this.couponsId = couponsId;
        this.userId = userId;
    }

    public Integer getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(Integer couponsId) {
        this.couponsId = couponsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CouponsDTO{" +
                "couponsId=" + couponsId +
                ", userId=" + userId +
                '}';
    }


}
