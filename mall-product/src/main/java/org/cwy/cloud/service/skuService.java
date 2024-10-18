package org.cwy.cloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.cwy.cloud.model.DTO.SkuSettingDTO;
import org.cwy.cloud.model.DTO.addSkuDTO;
import org.cwy.cloud.model.DTO.skuPage;
import org.cwy.cloud.model.VO.skuVO;
import org.cwy.cloud.model.pageVO;

import java.util.List;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/2 15:16
 */
public interface skuService {

    List<skuVO> getSku(Integer gid);

    boolean addSku(addSkuDTO sku);



    boolean settingPrice(SkuSettingDTO.skuSettingDTOS sku);

    ;
}
