package org.cwy.cloud.modle.DTO;

public class CouponsDTOa {
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

    Integer couponsId;
    Integer userId;
    Integer id;
    public Integer getId() {
        return id;
    }


    @Override
    public String toString() {
        return "CouponsDTO{" +
                "couponsId=" + couponsId +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }


}
