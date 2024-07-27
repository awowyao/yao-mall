package org.cwy.cloud.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.cwy.cloud.DTO.orderDTO;
import org.cwy.cloud.PO.orderPO;
import org.cwy.cloud.PO.returnablePO;
import org.cwy.cloud.feign.goodsFeign;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.mapper.orderMapper;
import org.cwy.cloud.mapper.orderMsgMapper;
import org.cwy.cloud.mapper.returnableMapper;
import org.cwy.cloud.service.returnableService;
import org.springframework.stereotype.Service;

@Service
public class returnableServiceIml implements returnableService {
    @Resource
    private orderMapper orderMapper;
    @Resource
    private orderMsgMapper orderMsgMapper;
    @Resource
    private uniqidFeign uniqidFeign;
    @Resource
    private returnableMapper returnableMapper;

    @Override
    public int requestReturnable(orderDTO orderData) {
        LambdaQueryWrapper<orderPO> query = new LambdaQueryWrapper<>();
        query.eq(orderPO::getOid, orderData.getOid());
        orderPO IOrderData = orderMapper.selectOne(query);
        if (IOrderData.getState() == 2) {
            LambdaUpdateWrapper<orderPO> updata = new LambdaUpdateWrapper<>();
            updata.set(orderPO::getState, 3);
            if (orderData.getReturnableText() != null){
                int addR = addReturnable(orderData.getReturnableText());
                updata.set(orderPO::getReturnable_id, addR);
            }else {
                updata.set(orderPO::getReturnable_id, orderData.getReturnableId());
            }
            updata.eq(orderPO::getOid, orderData.getOid());
            return orderMapper.update(null, updata);
        }
        return 0;
    }

    public int addReturnable(String returnableText, boolean IfDefault) {
        returnablePO returnable = new returnablePO();
        returnable.setIfDefault(1);
        returnable.setId(uniqidFeign.GetId(1));
        returnable.setReason(returnableText);
        if (returnableMapper.insert(returnable)>0)
            return returnable.getId();
        return 0;
    }
    public int addReturnable(String returnableText) {
        returnablePO returnable = new returnablePO();
        returnable.setIfDefault(0);
        returnable.setId(uniqidFeign.GetId(1));
        returnable.setReason(returnableText);
        if (returnableMapper.insert(returnable)>0)
            return returnable.getId();
        return 0;
    }
}
