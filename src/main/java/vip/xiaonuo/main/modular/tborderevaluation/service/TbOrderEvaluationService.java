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
package vip.xiaonuo.main.modular.tborderevaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.tborderevaluation.entity.TbOrderEvaluation;
import vip.xiaonuo.main.modular.tborderevaluation.param.TbOrderEvaluationParam;
import java.util.List;

/**
 * 订单评价service接口
 *
 * @author maksim
 * @date 2022-01-11 17:12:53
 */
public interface TbOrderEvaluationService extends IService<TbOrderEvaluation> {

    /**
     * 查询订单评价
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
    PageResult<TbOrderEvaluation> page(TbOrderEvaluationParam tbOrderEvaluationParam);

    /**
     * 订单评价列表
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
    List<TbOrderEvaluation> list(TbOrderEvaluationParam tbOrderEvaluationParam);

    /**
     * 添加订单评价
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
    void add(TbOrderEvaluationParam tbOrderEvaluationParam);

    /**
     * 删除订单评价
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
    void delete(List<TbOrderEvaluationParam> tbOrderEvaluationParamList);

    /**
     * 编辑订单评价
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
    void edit(TbOrderEvaluationParam tbOrderEvaluationParam);

    /**
     * 查看订单评价
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
     TbOrderEvaluation detail(TbOrderEvaluationParam tbOrderEvaluationParam);

    /**
     * 导出订单评价
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
     void export(TbOrderEvaluationParam tbOrderEvaluationParam);

    /**
     * @Description: 查看订单评价
     * @author 邾茂星
     * @date 2022/2/11 15:40
     * @param orderId
     * @return TbOrderEvaluation
     */
    TbOrderEvaluation getByOrderId( Long orderId);

}
