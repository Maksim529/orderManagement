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
package vip.xiaonuo.main.modular.tborderevaluation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.tbcustomerorder.entity.TbCustomerOrder;
import vip.xiaonuo.main.modular.tbcustomerorder.service.TbCustomerOrderService;
import vip.xiaonuo.main.modular.tborderevaluation.entity.TbOrderEvaluation;
import vip.xiaonuo.main.modular.tborderevaluation.enums.TbOrderEvaluationExceptionEnum;
import vip.xiaonuo.main.modular.tborderevaluation.mapper.TbOrderEvaluationMapper;
import vip.xiaonuo.main.modular.tborderevaluation.param.TbOrderEvaluationParam;
import vip.xiaonuo.main.modular.tborderevaluation.service.TbOrderEvaluationService;

import java.util.List;

/**
 * 订单评价service接口实现类
 *
 * @author maksim
 * @date 2022-01-11 17:12:53
 */
@Service
public class TbOrderEvaluationServiceImpl extends ServiceImpl<TbOrderEvaluationMapper, TbOrderEvaluation> implements TbOrderEvaluationService {
    @Autowired
    private TbCustomerOrderService tbCustomerOrderService;

    @Override
    public PageResult<TbOrderEvaluation> page(TbOrderEvaluationParam tbOrderEvaluationParam) {
        QueryWrapper<TbOrderEvaluation> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbOrderEvaluationParam)) {

//            // 根据物流 查询
//            if (ObjectUtil.isNotEmpty(tbOrderEvaluationParam.getLogistics())) {
//                queryWrapper.lambda().eq(TbOrderEvaluation::getLogistics, tbOrderEvaluationParam.getLogistics());
//            }
//            // 根据订单id 查询
//            if (ObjectUtil.isNotEmpty(tbOrderEvaluationParam.getOrderId())) {
//                queryWrapper.lambda().eq(TbOrderEvaluation::getOrderId, tbOrderEvaluationParam.getOrderId());
//            }
//            // 根据其他 查询
//            if (ObjectUtil.isNotEmpty(tbOrderEvaluationParam.getOther())) {
//                queryWrapper.lambda().eq(TbOrderEvaluation::getOther, tbOrderEvaluationParam.getOther());
//            }
//            // 根据时效 查询
//            if (ObjectUtil.isNotEmpty(tbOrderEvaluationParam.getPrescription())) {
//                queryWrapper.lambda().eq(TbOrderEvaluation::getPrescription, tbOrderEvaluationParam.getPrescription());
//            }
//            // 根据品质 查询
//            if (ObjectUtil.isNotEmpty(tbOrderEvaluationParam.getQuality())) {
//                queryWrapper.lambda().eq(TbOrderEvaluation::getQuality, tbOrderEvaluationParam.getQuality());
//            }
//            // 根据服务态度 查询
//            if (ObjectUtil.isNotEmpty(tbOrderEvaluationParam.getServiceAttitude())) {
//                queryWrapper.lambda().eq(TbOrderEvaluation::getServiceAttitude, tbOrderEvaluationParam.getServiceAttitude());
//            }
            // 根据订单id 查询
            if (ObjectUtil.isNotEmpty(tbOrderEvaluationParam.getOrderId())) {
                queryWrapper.eq("a.id", tbOrderEvaluationParam.getOrderId());
            }
            // 根据订单号 查询
            if (ObjectUtil.isNotEmpty(tbOrderEvaluationParam.getOrderNo())) {
                queryWrapper.like("b.order_no", tbOrderEvaluationParam.getOrderNo());
            }
        }
        queryWrapper.orderByDesc("a.id");
        return new PageResult<>(baseMapper.findPage(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbOrderEvaluation> list(TbOrderEvaluationParam tbOrderEvaluationParam) {
        return this.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(TbOrderEvaluationParam tbOrderEvaluationParam) {
        TbOrderEvaluation tbOrderEvaluation = new TbOrderEvaluation();
        BeanUtil.copyProperties(tbOrderEvaluationParam, tbOrderEvaluation);
        this.save(tbOrderEvaluation);
        //修改生产订单 状态 = 已拼分
        Long orderId = tbOrderEvaluation.getOrderId();
        if(orderId != null){
            TbCustomerOrder customerOrder = tbCustomerOrderService.getById(orderId);
            if(customerOrder != null && customerOrder.getStatus() == 5){
                customerOrder.setStatus(7);
                tbCustomerOrderService.updateById(customerOrder);
            }else {
                throw new ServiceException(ResponseData.DEFAULT_ERROR_CODE, "订单状态未关单，不可评分！");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbOrderEvaluationParam> tbOrderEvaluationParamList) {
        tbOrderEvaluationParamList.forEach(tbOrderEvaluationParam -> {
            this.removeById(tbOrderEvaluationParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbOrderEvaluationParam tbOrderEvaluationParam) {
        TbOrderEvaluation tbOrderEvaluation = this.queryTbOrderEvaluation(tbOrderEvaluationParam);
        BeanUtil.copyProperties(tbOrderEvaluationParam, tbOrderEvaluation);
        this.updateById(tbOrderEvaluation);
    }

    @Override
    public TbOrderEvaluation detail(TbOrderEvaluationParam tbOrderEvaluationParam) {
        return this.queryTbOrderEvaluation(tbOrderEvaluationParam);
    }

    /**
     * 获取订单评价
     *
     * @author maksim
     * @date 2022-01-11 17:12:53
     */
    private TbOrderEvaluation queryTbOrderEvaluation(TbOrderEvaluationParam tbOrderEvaluationParam) {
        TbOrderEvaluation tbOrderEvaluation = this.getById(tbOrderEvaluationParam.getId());
        if (ObjectUtil.isNull(tbOrderEvaluation)) {
            throw new ServiceException(TbOrderEvaluationExceptionEnum.NOT_EXIST);
        }
        return tbOrderEvaluation;
    }

    @Override
    public void export(TbOrderEvaluationParam tbOrderEvaluationParam) {
        List<TbOrderEvaluation> list = this.list(tbOrderEvaluationParam);
        PoiUtil.exportExcelWithStream("SnowyTbOrderEvaluation.xls", TbOrderEvaluation.class, list);
    }

    /**
     * @Description: 查看订单评价
     * @author 邾茂星
     * @date 2022/2/11 15:41
     * @param orderId
     * @return TbOrderEvaluation
     */
    @Override
    public TbOrderEvaluation getByOrderId(Long orderId) {
        TbOrderEvaluation tbOrderEvaluation = new TbOrderEvaluation();
        if(orderId != null){
            QueryWrapper<TbOrderEvaluation> queryWrapper = new QueryWrapper<>();
            // 根据订单id 查询
            queryWrapper.eq("a.order_id", orderId);
            queryWrapper.orderByDesc("a.id");
            Page<TbOrderEvaluation> page = baseMapper.findPage(PageFactory.defaultPage(), queryWrapper);
            if(page != null && CollUtil.isNotEmpty(page.getRecords())){
                tbOrderEvaluation = page.getRecords().get(0);
            }
        }
        return tbOrderEvaluation;
    }
}
