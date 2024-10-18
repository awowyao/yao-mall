package org.cwy.cloud.service.Imp;

import jakarta.annotation.Resource;
import org.cwy.cloud.common.MyAssert;
import org.cwy.cloud.feign.uniqidFeign;
import org.cwy.cloud.mapper.skuMapper;
import org.cwy.cloud.model.DTO.SkuSettingDTO;
import org.cwy.cloud.model.DTO.addSkuDTO;
import org.cwy.cloud.model.PO.goodsSkuPO;
import org.cwy.cloud.model.PO.skuKeyPO;
import org.cwy.cloud.model.PO.skuValuePO;
import org.cwy.cloud.model.VO.skuVO;
import org.cwy.cloud.service.skuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/2 15:16
 */
@Service
public class skuServiceImpl implements skuService {
    @Resource
    private skuMapper skuMapper;

    @Resource
    private uniqidFeign uniqidFeign;
    @Override
    public List<skuVO> getSku(Integer gid) {

        List<skuKeyPO> skuKey = skuMapper.getSkuKeys(gid);
        List<skuVO> skuVOs = skuKey.stream().map(key-> {
            skuVO sku = new skuVO();
            List<skuValuePO> skuValuePOS = skuMapper.getSkuValues(key.getId());
            sku.setSkuKeyId(key.getId());
            sku.setSkuKey(key.getSkuKey());
            sku.setSkuValue(skuValuePOS);
            return sku;
        }).toList();

        return skuVOs;
    }

    @Override
    @Transactional
    public boolean addSku(addSkuDTO sku) {
        Integer keyId = uniqidFeign.GetId(1);
        Integer skuCount = skuMapper.getSkuCount(sku.getGid());
        if (skuCount>=3) {
            return false;
        }
        MyAssert.isZero(skuMapper.addSkuKey(keyId, sku.getGid(),1 ,sku.getSkuKey(), (skuCount+1)));

        sku.getSkuValue().forEach(value-> {
            MyAssert.isZero(skuMapper.addSkuValue(uniqidFeign.GetId(1),keyId, value));
        });

        return true;
    }

    @Override
    public boolean settingPrice(SkuSettingDTO.skuSettingDTOS skuList) {
        Integer storeId = 1;
        MyAssert.notEmpty(skuList.getSkuPriceList(), "设置价格为空");
        skuList.getSkuPriceList().forEach(sku -> {
            goodsSkuPO goodsSkuPO = new goodsSkuPO();
            goodsSkuPO.setId(uniqidFeign.GetId(1));
            goodsSkuPO.setGid(skuList.getGid());
            goodsSkuPO.setKeyO(sku.getSkuKeyOId());
            goodsSkuPO.setValueO(sku.getSkuValueOId());

            goodsSkuPO.setKeyT(sku.getSkuKeyTId());
            goodsSkuPO.setValueT(sku.getSkuValueTId());

            goodsSkuPO.setKeyS(sku.getSkuKeySId());
            goodsSkuPO.setValueS(sku.getSkuValueSId());

            goodsSkuPO.setStock(sku.getStock());
            goodsSkuPO.setPrice(sku.getPrice());
            skuMapper.insert(goodsSkuPO);
        });
        return true;
    }
}
