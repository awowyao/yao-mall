package org.cwy.cloud.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.cwy.cloud.model.DTO.orderDTO;
import org.cwy.cloud.model.PO.orderMsgPO;
import org.cwy.cloud.model.PO.orderPO;
import org.cwy.cloud.model.PO.returnablePO;
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

        LambdaQueryWrapper<orderMsgPO> query = new LambdaQueryWrapper<>();
        query.eq(orderMsgPO::getOmId, orderData.getOrderMsgId());
        orderMsgPO IOrderData = orderMsgMapper.selectOne(query);
        if (IOrderData.getOrderStatue() == 1) {
            LambdaUpdateWrapper<orderMsgPO> updata = new LambdaUpdateWrapper<>();
            updata.set(orderMsgPO::getOrderStatue, 3);
            if (orderData.getReturnableText() != null){
                int addR = addReturnable(orderData.getReturnableText());
                updata.set(orderMsgPO::getReturnableId, addR);
            }else {
                updata.set(orderMsgPO::getReturnableId, orderData.getReturnableId());
            }
            updata.eq(orderMsgPO::getOmId, IOrderData.getOmId());
            return orderMsgMapper.update(null, updata);
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
