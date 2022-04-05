package vip.xiaonuo.main.modular.sysConfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.sys.modular.file.entity.SysFileInfo;

/**
 * @author 邾茂星
 * @Description: 文件 service
 * @date 2022/1/20 10:25
 */
public interface SysFileService {

    int uploadFile(MultipartFile[] files, String businessValue, Integer type);
}
