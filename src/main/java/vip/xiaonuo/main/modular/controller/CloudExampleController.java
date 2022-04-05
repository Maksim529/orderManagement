
package vip.xiaonuo.main.modular.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.modular.consumer.CloudSampleRestApiConsumer;

/**
 * sprindcloud-config示例接口
 *
 * @author dongxiayu
 * @date 2020/12/28 19:09
 */
@RefreshScope
@RestController
@RequestMapping("/oauth")
@Api(tags = "示例")
public class CloudExampleController {

    @Value("${snowy.main.config.test:snowy-main-test-local}")
    private String configTest;

    @Autowired
    private CloudSampleRestApiConsumer cloudSampleRestApiConsumer;

    @GetMapping("/config/test")
    public ResponseData configTest() {
        return new SuccessResponseData(configTest);
    }

    @GetMapping("/sample/config/test")
    public ResponseData sampleConfigTest() {
        return ResponseData.success(cloudSampleRestApiConsumer.configTest());
    }

    @GetMapping("/sample/getBySampleName")
    public ResponseData getBySampleName(String sampleName){
        return ResponseData.success(cloudSampleRestApiConsumer.getBySampleName(sampleName));
    }

}
