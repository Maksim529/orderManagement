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
package vip.xiaonuo.main.modular.tbcontract.service.impl;

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
import vip.xiaonuo.main.modular.tbcontract.entity.TbContract;
import vip.xiaonuo.main.modular.tbcontract.enums.TbContractExceptionEnum;
import vip.xiaonuo.main.modular.tbcontract.mapper.TbContractMapper;
import vip.xiaonuo.main.modular.tbcontract.param.TbContractParam;
import vip.xiaonuo.main.modular.tbcontract.service.TbContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 合同信息service接口实现类
 *
 * @author maksim
 * @date 2022-01-11 17:03:53
 */
@Service
public class TbContractServiceImpl extends ServiceImpl<TbContractMapper, TbContract> implements TbContractService {

    @Override
    public PageResult<TbContract> page(TbContractParam tbContractParam) {
        QueryWrapper<TbContract> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbContractParam)) {

            // 根据订单号 查询
            if (ObjectUtil.isNotEmpty(tbContractParam.getContractNo())) {
                queryWrapper.lambda().eq(TbContract::getContractNo, tbContractParam.getContractNo());
            }
            // 根据所属客户 查询
            if (ObjectUtil.isNotEmpty(tbContractParam.getCustomerId())) {
                queryWrapper.lambda().eq(TbContract::getCustomerId, tbContractParam.getCustomerId());
            }
            // 根据订单id 查询
            if (ObjectUtil.isNotEmpty(tbContractParam.getOrderId())) {
                queryWrapper.lambda().eq(TbContract::getOrderId, tbContractParam.getOrderId());
            }
            // 根据 查询
            if (ObjectUtil.isNotEmpty(tbContractParam.getService())) {
                queryWrapper.lambda().eq(TbContract::getService, tbContractParam.getService());
            }
            // 根据 查询
            if (ObjectUtil.isNotEmpty(tbContractParam.getSyscreated())) {
                queryWrapper.lambda().eq(TbContract::getSyscreated, tbContractParam.getSyscreated());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbContract> list(TbContractParam tbContractParam) {
        return this.list();
    }

    @Override
    public void add(TbContractParam tbContractParam) {
        TbContract tbContract = new TbContract();
        BeanUtil.copyProperties(tbContractParam, tbContract);
        this.save(tbContract);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbContractParam> tbContractParamList) {
        tbContractParamList.forEach(tbContractParam -> {
            this.removeById(tbContractParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbContractParam tbContractParam) {
        TbContract tbContract = this.queryTbContract(tbContractParam);
        BeanUtil.copyProperties(tbContractParam, tbContract);
        this.updateById(tbContract);
    }

    @Override
    public TbContract detail(TbContractParam tbContractParam) {
        return this.queryTbContract(tbContractParam);
    }

    /**
     * 获取合同信息
     *
     * @author maksim
     * @date 2022-01-11 17:03:53
     */
    private TbContract queryTbContract(TbContractParam tbContractParam) {
        TbContract tbContract = this.getById(tbContractParam.getId());
        if (ObjectUtil.isNull(tbContract)) {
            throw new ServiceException(TbContractExceptionEnum.NOT_EXIST);
        }
        return tbContract;
    }

    @Override
    public void export(TbContractParam tbContractParam) {
        List<TbContract> list = this.list(tbContractParam);
        PoiUtil.exportExcelWithStream("SnowyTbContract.xls", TbContract.class, list);
    }

}
