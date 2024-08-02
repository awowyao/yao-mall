package org.cwy.cloud.modle.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import org.cwy.cloud.modle.DTO.addressDTO;

import java.util.Date;

@Data
@TableName("address_table")
public class addressPO {
    private Integer id;
    private Integer uid;
    private String province;
    private String city;
    private String district;
    private String detailed;
    @Version
    private Integer version;
    private Date createTime;
    private Date updateTime;

    public static addressPO AddAddressPO(addressDTO address) {
        addressPO newAddressPo = new addressPO();
        newAddressPo.setCity(address.getCity());
        newAddressPo.setProvince(address.getProvince());
        newAddressPo.setDetailed(address.getDetailed());
        newAddressPo.setDistrict(address.getDistrict());
        return newAddressPo;
    }

    public static addressPO editAddressPO(addressDTO address) {
        addressPO newAddressPo = new addressPO();
        newAddressPo.setCity(address.getCity());
        newAddressPo.setProvince(address.getProvince());
        newAddressPo.setDetailed(address.getDetailed());
        newAddressPo.setDistrict(address.getDistrict());
        newAddressPo.setId(address.getId());
        newAddressPo.setVersion(address.getVersion());
        return newAddressPo;
    }
    public addressPO() {
        setVersion(0);
    }
}
