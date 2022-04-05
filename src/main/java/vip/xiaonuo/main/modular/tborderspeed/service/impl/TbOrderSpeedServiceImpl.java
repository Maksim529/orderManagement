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
package vip.xiaonuo.main.modular.tborderspeed.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.xiaonuo.common.consts.CommonConstant;
import vip.xiaonuo.common.enums.CommonStatusEnum;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.tborderspeed.entity.TbOrderSpeed;
import vip.xiaonuo.main.modular.tborderspeed.enums.TbOrderSpeedExceptionEnum;
import vip.xiaonuo.main.modular.tborderspeed.mapper.TbOrderSpeedMapper;
import vip.xiaonuo.main.modular.tborderspeed.param.TbOrderSpeedParam;
import vip.xiaonuo.main.modular.tborderspeed.service.TbOrderSpeedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 订单进度service接口实现类
 *
 * @author maksim
 * @date 2022-01-11 16:53:00
 */
@Service
public class TbOrderSpeedServiceImpl extends ServiceImpl<TbOrderSpeedMapper, TbOrderSpeed> implements TbOrderSpeedService {

    @Override
    public PageResult<TbOrderSpeed> page(TbOrderSpeedParam tbOrderSpeedParam) {
        QueryWrapper<TbOrderSpeed> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbOrderSpeedParam)) {

//            // 根据裁剪完成时间 查询
//            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getCutCompletion())) {
//                queryWrapper.lambda().eq(TbOrderSpeed::getCutCompletion, tbOrderSpeedParam.getCutCompletion());
//            }
//            // 根据实裁数量 查询
//            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getCutNum())) {
//                queryWrapper.lambda().eq(TbOrderSpeed::getCutNum, tbOrderSpeedParam.getCutNum());
//            }
//            // 根据组检时间 查询
//            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getGroupInspection())) {
//                queryWrapper.lambda().eq(TbOrderSpeed::getGroupInspection, tbOrderSpeedParam.getGroupInspection());
//            }
//            // 根据上线时间 查询
//            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getOnlineTime())) {
//                queryWrapper.lambda().eq(TbOrderSpeed::getOnlineTime, tbOrderSpeedParam.getOnlineTime());
//            }
//            // 根据订单id 查询
//            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getOrderId())) {
//                queryWrapper.lambda().eq(TbOrderSpeed::getOrderId, tbOrderSpeedParam.getOrderId());
//            }
//            // 根据装箱发货时间 查询
//            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getPackingDelivery())) {
//                queryWrapper.lambda().eq(TbOrderSpeed::getPackingDelivery, tbOrderSpeedParam.getPackingDelivery());
//            }
//            // 根据齐套日期 查询
//            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getQtDate())) {
//                queryWrapper.lambda().eq(TbOrderSpeed::getQtDate, tbOrderSpeedParam.getQtDate());
//            }
//            // 根据接单时间 查询
//            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getRecieveOrderTime())) {
//                queryWrapper.lambda().eq(TbOrderSpeed::getRecieveOrderTime, tbOrderSpeedParam.getRecieveOrderTime());
//            }
//            // 根据缝制完成时间 查询
//            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getSewingCompletion())) {
//                queryWrapper.lambda().eq(TbOrderSpeed::getSewingCompletion, tbOrderSpeedParam.getSewingCompletion());
//            }
//            // 根据缝制完成数量 查询
//            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getSewingCompletionNum())) {
//                queryWrapper.lambda().eq(TbOrderSpeed::getSewingCompletionNum, tbOrderSpeedParam.getSewingCompletionNum());
//            }
//            // 根据总检时间 查询
//            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getTotalInspection())) {
//                queryWrapper.lambda().eq(TbOrderSpeed::getTotalInspection, tbOrderSpeedParam.getTotalInspection());
//            }

            // 根据订单id 查询
            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getOrderId())) {
                queryWrapper.eq("a.id", tbOrderSpeedParam.getOrderId());
            }
            // 根据订单号 查询
            if (ObjectUtil.isNotEmpty(tbOrderSpeedParam.getOrderNo())) {
                queryWrapper.like("b.order_no", tbOrderSpeedParam.getOrderNo());
            }
        }
        queryWrapper.orderByDesc("a.id");
        return new PageResult<>(baseMapper.findPage(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbOrderSpeed> list(TbOrderSpeedParam tbOrderSpeedParam) {
        return this.list();
    }

    @Override
    public void add(TbOrderSpeedParam tbOrderSpeedParam) {
        TbOrderSpeed tbOrderSpeed = new TbOrderSpeed();
        BeanUtil.copyProperties(tbOrderSpeedParam, tbOrderSpeed);
        this.save(tbOrderSpeed);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbOrderSpeedParam> tbOrderSpeedParamList) {
        tbOrderSpeedParamList.forEach(tbOrderSpeedParam -> {
            this.removeById(tbOrderSpeedParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbOrderSpeedParam tbOrderSpeedParam) {
        TbOrderSpeed tbOrderSpeed = this.queryTbOrderSpeed(tbOrderSpeedParam);
        BeanUtil.copyProperties(tbOrderSpeedParam, tbOrderSpeed);
        this.updateById(tbOrderSpeed);
    }

    @Override
    public TbOrderSpeed detail(TbOrderSpeedParam tbOrderSpeedParam) {
        return this.queryTbOrderSpeed(tbOrderSpeedParam);
    }

    /**
     * 获取订单进度
     *
     * @author maksim
     * @date 2022-01-11 16:53:00
     */
    private TbOrderSpeed queryTbOrderSpeed(TbOrderSpeedParam tbOrderSpeedParam) {
        TbOrderSpeed tbOrderSpeed = this.getById(tbOrderSpeedParam.getId());
        if (ObjectUtil.isNull(tbOrderSpeed)) {
            throw new ServiceException(TbOrderSpeedExceptionEnum.NOT_EXIST);
        }
        return tbOrderSpeed;
    }

    @Override
    public void export(TbOrderSpeedParam tbOrderSpeedParam) {
        List<TbOrderSpeed> list = this.list(tbOrderSpeedParam);
        PoiUtil.exportExcelWithStream("SnowyTbOrderSpeed.xls", TbOrderSpeed.class, list);
    }

}
