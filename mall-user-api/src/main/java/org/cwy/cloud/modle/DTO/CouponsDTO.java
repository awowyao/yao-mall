package org.cwy.cloud.modle.DTO;

public class CouponsDTO {
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

    Integer couponsId;
    Integer userId;
}
