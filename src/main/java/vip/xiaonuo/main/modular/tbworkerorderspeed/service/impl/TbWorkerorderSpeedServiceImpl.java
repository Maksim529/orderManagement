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
package vip.xiaonuo.main.modular.tbworkerorderspeed.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.tbworkerorderspeed.entity.TbWorkerorderSpeed;
import vip.xiaonuo.main.modular.tbworkerorderspeed.enums.TbWorkerorderSpeedExceptionEnum;
import vip.xiaonuo.main.modular.tbworkerorderspeed.mapper.TbWorkerorderSpeedMapper;
import vip.xiaonuo.main.modular.tbworkerorderspeed.param.TbWorkerorderSpeedParam;
import vip.xiaonuo.main.modular.tbworkerorderspeed.service.TbWorkerorderSpeedService;

import java.util.List;

/**
 * 生产进度service接口实现类
 *
 * @author maksim
 * @date 2022-01-19 16:04:54
 */
@Service
public class TbWorkerorderSpeedServiceImpl extends ServiceImpl<TbWorkerorderSpeedMapper, TbWorkerorderSpeed> implements TbWorkerorderSpeedService {

    @Override
    public PageResult<TbWorkerorderSpeed> page(TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
        QueryWrapper<TbWorkerorderSpeed> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbWorkerorderSpeedParam)) {
            // 根据工单id 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderSpeedParam.getWorkOrderId())) {
                queryWrapper.eq("a.id", tbWorkerorderSpeedParam.getWorkOrderId());
            }
            // 根据工单号 查询
            if (ObjectUtil.isNotEmpty(tbWorkerorderSpeedParam.getWorkOrderNo())) {
                queryWrapper.like("b.work_order_no", tbWorkerorderSpeedParam.getWorkOrderNo());
            }
        }
        queryWrapper.orderByDesc("a.id");
        return new PageResult<>(baseMapper.findPage(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbWorkerorderSpeed> list(TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
        return this.list();
    }

    @Override
    public void add(TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
        TbWorkerorderSpeed tbWorkerorderSpeed = new TbWorkerorderSpeed();
        BeanUtil.copyProperties(tbWorkerorderSpeedParam, tbWorkerorderSpeed);
        this.save(tbWorkerorderSpeed);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbWorkerorderSpeedParam> tbWorkerorderSpeedParamList) {
        tbWorkerorderSpeedParamList.forEach(tbWorkerorderSpeedParam -> {
            this.removeById(tbWorkerorderSpeedParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
        TbWorkerorderSpeed tbWorkerorderSpeed = this.queryTbWorkerorderSpeed(tbWorkerorderSpeedParam);
        BeanUtil.copyProperties(tbWorkerorderSpeedParam, tbWorkerorderSpeed);
        this.updateById(tbWorkerorderSpeed);
    }

    @Override
    public TbWorkerorderSpeed detail(TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
        return this.queryTbWorkerorderSpeed(tbWorkerorderSpeedParam);
    }

    /**
     * 获取生产进度
     *
     * @author maksim
     * @date 2022-01-19 16:04:54
     */
    private TbWorkerorderSpeed queryTbWorkerorderSpeed(TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
        TbWorkerorderSpeed tbWorkerorderSpeed = this.getById(tbWorkerorderSpeedParam.getId());
        if (ObjectUtil.isNull(tbWorkerorderSpeed)) {
            throw new ServiceException(TbWorkerorderSpeedExceptionEnum.NOT_EXIST);
        }
        return tbWorkerorderSpeed;
    }

    @Override
    public void export(TbWorkerorderSpeedParam tbWorkerorderSpeedParam) {
        List<TbWorkerorderSpeed> list = this.list(tbWorkerorderSpeedParam);
        PoiUtil.exportExcelWithStream("SnowyTbWorkerorderSpeed.xls", TbWorkerorderSpeed.class, list);
    }

    @Override
    public String getStatusDesc(Long orderId, Integer type) {
        String desc = "";
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("a.id", orderId);
        switch (type) {
            case 1:
                //齐套时间
                queryWrapper.select("DATE_FORMAT(c.qt_date, '%Y-%m-%d')");
                queryWrapper.orderByDesc("c.qt_date");
                desc = baseMapper.getStatusDesc(queryWrapper);
                break;
            case 2:
                //裁剪，实裁数量
                queryWrapper.select("DATE_FORMAT(c.cut_completion, '%Y-%m-%d')");
                queryWrapper.orderByDesc("c.cut_completion");
                desc = baseMapper.getStatusDesc(queryWrapper);
                break;
            case 3:
                //上线时间
                queryWrapper.select("DATE_FORMAT(c.online_time, '%Y-%m-%d')");
                queryWrapper.orderByDesc("c.online_time");
                desc = baseMapper.getStatusDesc(queryWrapper);
                break;
            case 4:
                //缝制完成
                queryWrapper.select("DATE_FORMAT(c.sewing_completion, '%Y-%m-%d')");
                queryWrapper.orderByDesc("c.sewing_completion");
                desc = baseMapper.getStatusDesc(queryWrapper);
                break;
            case 5:
                //质检
                queryWrapper.select("DATE_FORMAT(c.total_inspection, '%Y-%m-%d')");
                queryWrapper.orderByDesc("c.total_inspection");
                desc = baseMapper.getStatusDesc(queryWrapper);
                break;
            case 6:
                //发货
                queryWrapper.select("DATE_FORMAT(c.packing_delivery, '%Y-%m-%d')");
                queryWrapper.orderByDesc("c.packing_delivery");
                desc = baseMapper.getStatusDesc(queryWrapper);
                break;
            default:
                break;
        }
        return desc;
    }

    @Override
    public Integer getStatusNum(Long orderId, Integer type) {
        int result = 0;
        QueryWrapper<TbWorkerorderSpeed> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        if (type == 1) {
            //实裁数量
            queryWrapper.select("sum(cut_num)");
        } else if (type == 2) {
            //缝制数量
            queryWrapper.select("sum(sewing_completion_num)");
        }
        result = baseMapper.getStatusNum(queryWrapper);
        return result;
    }
}
