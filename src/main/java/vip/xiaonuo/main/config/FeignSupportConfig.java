
package vip.xiaonuo.main.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.xiaonuo.main.core.inteceptor.FeignRequestInterceptor;

/**
 * Feign配置注册（全局）
 *
 * @author dongxiayu
 * @date : 2021/3/29 15:04
 **/
@Configuration
public class FeignSupportConfig {

    /**
     * feign请求拦截器
     *
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new FeignRequestInterceptor();
    }

}
