package vip.xiaonuo.main.modular.sysConfig.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.consts.SymbolConstant;
import vip.xiaonuo.main.modular.sysConfig.service.SysFileService;
import vip.xiaonuo.main.modular.tbcustomerorder.service.TbCustomerOrderService;
import vip.xiaonuo.sys.modular.file.entity.SysFileInfo;
import vip.xiaonuo.sys.modular.file.service.SysFileInfoService;

/**
 * @author 邾茂星
 * @Description: 文件 serviceImpl
 * @date 2022/1/20 10:27
 */
@Service
public class SysFileServiceImpl implements SysFileService {
    @Autowired
    private SysFileInfoService sysFileInfoService;
    @Autowired
    private TbCustomerOrderService tbCustomerOrderService;

    /**
     * @param files
     * @param businessValue
     * @param type
     * @return int
     * @Description: 文件上传
     * @author 邾茂星
     * @date 2022/1/20 10:29
     */
    @Override
    public int uploadFile(MultipartFile[] files, String businessValue, Integer type) {
        int resultNumber = 0;
        if (files != null && StrUtil.isNotBlank(businessValue) && type != null) {
            for (MultipartFile file : files) {
                // 获取文件原始名称
                String originalFilename = file.getOriginalFilename();
                // 获取文件后缀
                String fileSuffix = null;
                if (ObjectUtil.isNotEmpty(originalFilename)) {
                    fileSuffix = StrUtil.subAfter(originalFilename, SymbolConstant.PERIOD, true);
                }
                SysFileInfo sysFileInfo = new SysFileInfo();
                sysFileInfo.setBusinessDataId(Long.parseLong(businessValue));
                sysFileInfo.setBusinessType(type);
                sysFileInfoService.uploadFile(file, sysFileInfo);
                resultNumber++;
            }
        }
        return resultNumber;
    }
}
