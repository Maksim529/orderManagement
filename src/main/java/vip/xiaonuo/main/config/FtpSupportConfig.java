package vip.xiaonuo.main.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author: 邾茂星
 * @Date:2022/1/12
 */
@Component
@Data
@RefreshScope
public class FtpSupportConfig {

    @Value("${ftpUrl}")
    private String ftpUrl;

    @Value("${ftpPort}")
    private int ftpPort;

    @Value("${ftpAcc}")
    private String ftpAcc;

    @Value("${ftpPwd}")
    private String ftpPwd;

    @Value("${picDir}")
    private String picDir;

    @Value("${file.local-path}")
    private String filePath;


}
