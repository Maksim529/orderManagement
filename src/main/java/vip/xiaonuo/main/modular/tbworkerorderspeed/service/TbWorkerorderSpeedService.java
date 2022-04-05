/*
Copyright [2020] [https://www.xiaonuo.vip]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Snowy源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/xiaonuobase/snowy-cloud
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/xiaonuobase/snowy-cloud
6.若您的项目无法满足以上几点，可申请商业授权，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.main.modular.tbworkerorderspeed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.tbworkerorderspeed.entity.TbWorkerorderSpeed;
import vip.xiaonuo.main.modular.tbworkerorderspeed.param.TbWorkerorderSpeedParam;
import java.util.List;

/**
 * 生产进度service接口
 *
 * @author maksim
 * @date 2022-01-13 10:35:23
 */
public interface TbWorkerorderSpeedService extends IService<TbWorkerorderSpeed> {

    /**
     * 查询生产进度
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
    PageResult<TbWorkerorderSpeed> page(TbWorkerorderSpeedParam tbWorkerorderSpeedParam);

    /**
     * 生产进度列表
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
    List<TbWorkerorderSpeed> list(TbWorkerorderSpeedParam tbWorkerorderSpeedParam);

    /**
     * 添加生产进度
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
    void add(TbWorkerorderSpeedParam tbWorkerorderSpeedParam);

    /**
     * 删除生产进度
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
    void delete(List<TbWorkerorderSpeedParam> tbWorkerorderSpeedParamList);

    /**
     * 编辑生产进度
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
    void edit(TbWorkerorderSpeedParam tbWorkerorderSpeedParam);

    /**
     * 查看生产进度
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
     TbWorkerorderSpeed detail(TbWorkerorderSpeedParam tbWorkerorderSpeedParam);

    /**
     * 导出生产进度
     *
     * @author maksim
     * @date 2022-01-13 10:35:23
     */
     void export(TbWorkerorderSpeedParam tbWorkerorderSpeedParam);

    /**
     * @Description: 查询订单各状态，步骤描述
     * @author 邾茂星
     * @date 2022/3/16 16:43
     * @param orderId
     * @param type
     * @return String
     */
    String getStatusDesc(Long orderId, Integer type);

    /**
     * @Description: 查询订单状态，已报工数量
     * @author 邾茂星
     * @date 2022/3/17 13:04
     * @param orderId
     * @param type
     * @return Integer
     */
    Integer getStatusNum(Long orderId, Integer type);
}
