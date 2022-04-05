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
package vip.xiaonuo.main.modular.tbworkorderevaluation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import vip.xiaonuo.common.consts.CommonConstant;
import vip.xiaonuo.common.enums.CommonStatusEnum;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkOrder;
import vip.xiaonuo.main.modular.tbworkorder.service.TbWorkOrderService;
import vip.xiaonuo.main.modular.tbworkorderevaluation.entity.TbWorkOrderEvaluation;
import vip.xiaonuo.main.modular.tbworkorderevaluation.enums.TbWorkOrderEvaluationExceptionEnum;
import vip.xiaonuo.main.modular.tbworkorderevaluation.mapper.TbWorkOrderEvaluationMapper;
import vip.xiaonuo.main.modular.tbworkorderevaluation.param.TbWorkOrderEvaluationParam;
import vip.xiaonuo.main.modular.tbworkorderevaluation.service.TbWorkOrderEvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 工单评价service接口实现类
 *
 * @author maksim
 * @date 2022-01-11 17:17:47
 */
@Service
public class TbWorkOrderEvaluationServiceImpl extends ServiceImpl<TbWorkOrderEvaluationMapper, TbWorkOrderEvaluation> implements TbWorkOrderEvaluationService {
    @Autowired
    private TbWorkOrderService tbWorkOrderService;

    @Override
    public PageResult<TbWorkOrderEvaluation> page(TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
        QueryWrapper<TbWorkOrderEvaluation> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbWorkOrderEvaluationParam)) {

//            // 根据物流 查询
//            if (ObjectUtil.isNotEmpty(tbWorkOrderEvaluationParam.getLogistics())) {
//                queryWrapper.lambda().eq(TbWorkOrderEvaluation::getLogistics, tbWorkOrderEvaluationParam.getLogistics());
//            }
//            // 根据其他 查询
//            if (ObjectUtil.isNotEmpty(tbWorkOrderEvaluationParam.getOther())) {
//                queryWrapper.lambda().eq(TbWorkOrderEvaluation::getOther, tbWorkOrderEvaluationParam.getOther());
//            }
//            // 根据时效 查询
//            if (ObjectUtil.isNotEmpty(tbWorkOrderEvaluationParam.getPrescription())) {
//                queryWrapper.lambda().eq(TbWorkOrderEvaluation::getPrescription, tbWorkOrderEvaluationParam.getPrescription());
//            }
//            // 根据品质 查询
//            if (ObjectUtil.isNotEmpty(tbWorkOrderEvaluationParam.getQuality())) {
//                queryWrapper.lambda().eq(TbWorkOrderEvaluation::getQuality, tbWorkOrderEvaluationParam.getQuality());
//            }
//            // 根据服务态度 查询
//            if (ObjectUtil.isNotEmpty(tbWorkOrderEvaluationParam.getServiceAttitude())) {
//                queryWrapper.lambda().eq(TbWorkOrderEvaluation::getServiceAttitude, tbWorkOrderEvaluationParam.getServiceAttitude());
//            }
//            // 根据订单id 查询
//            if (ObjectUtil.isNotEmpty(tbWorkOrderEvaluationParam.getWorkerOrderId())) {
//                queryWrapper.lambda().eq(TbWorkOrderEvaluation::getWorkerOrderId, tbWorkOrderEvaluationParam.getWorkerOrderId());
//            }
            // 根据订单id 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderEvaluationParam.getWorkerOrderId())) {
                queryWrapper.eq("a.id", tbWorkOrderEvaluationParam.getWorkerOrderId());
            }
            // 根据订单号 查询
            if (ObjectUtil.isNotEmpty(tbWorkOrderEvaluationParam.getWorkOrderNo())) {
                queryWrapper.like("b.work_order_no", tbWorkOrderEvaluationParam.getWorkOrderNo());
            }
        }
        queryWrapper.orderByDesc("a.id");
        return new PageResult<>(baseMapper.findPage(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbWorkOrderEvaluation> list(TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
        return this.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
        TbWorkOrderEvaluation tbWorkOrderEvaluation = new TbWorkOrderEvaluation();
        BeanUtil.copyProperties(tbWorkOrderEvaluationParam, tbWorkOrderEvaluation);
        this.save(tbWorkOrderEvaluation);
        //修改 生产工单已拼分
        Long workerOrderId = tbWorkOrderEvaluation.getWorkerOrderId();
        if(workerOrderId != null){
            TbWorkOrder tbWorkOrder = tbWorkOrderService.getById(workerOrderId);
            if(tbWorkOrder != null && tbWorkOrder.getStatus() == 80){
                tbWorkOrder.setStatus(90);
                tbWorkOrderService.updateById(tbWorkOrder);
            }else {
                throw new ServiceException(ResponseData.DEFAULT_ERROR_CODE, "工单状态未结单，不可拼分！");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbWorkOrderEvaluationParam> tbWorkOrderEvaluationParamList) {
        tbWorkOrderEvaluationParamList.forEach(tbWorkOrderEvaluationParam -> {
            this.removeById(tbWorkOrderEvaluationParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
        TbWorkOrderEvaluation tbWorkOrderEvaluation = this.queryTbWorkOrderEvaluation(tbWorkOrderEvaluationParam);
        BeanUtil.copyProperties(tbWorkOrderEvaluationParam, tbWorkOrderEvaluation);
        this.updateById(tbWorkOrderEvaluation);
    }

    @Override
    public TbWorkOrderEvaluation detail(TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
        return this.queryTbWorkOrderEvaluation(tbWorkOrderEvaluationParam);
    }

    /**
     * 获取工单评价
     *
     * @author maksim
     * @date 2022-01-11 17:17:47
     */
    private TbWorkOrderEvaluation queryTbWorkOrderEvaluation(TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
        TbWorkOrderEvaluation tbWorkOrderEvaluation = this.getById(tbWorkOrderEvaluationParam.getId());
        if (ObjectUtil.isNull(tbWorkOrderEvaluation)) {
            throw new ServiceException(TbWorkOrderEvaluationExceptionEnum.NOT_EXIST);
        }
        return tbWorkOrderEvaluation;
    }

    @Override
    public void export(TbWorkOrderEvaluationParam tbWorkOrderEvaluationParam) {
        List<TbWorkOrderEvaluation> list = this.list(tbWorkOrderEvaluationParam);
        PoiUtil.exportExcelWithStream("SnowyTbWorkOrderEvaluation.xls", TbWorkOrderEvaluation.class, list);
    }

}
