
package vip.xiaonuo.main.modular.tbproducecontract.service.impl;

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
import vip.xiaonuo.main.modular.tbproducecontract.entity.TbProduceContract;
import vip.xiaonuo.main.modular.tbproducecontract.enums.TbProduceContractExceptionEnum;
import vip.xiaonuo.main.modular.tbproducecontract.mapper.TbProduceContractMapper;
import vip.xiaonuo.main.modular.tbproducecontract.param.TbProduceContractParam;
import vip.xiaonuo.main.modular.tbproducecontract.service.TbProduceContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 加工合同service接口实现类
 *
 * @author wjc
 * @date 2022-01-20 10:11:34
 */
@Service
public class TbProduceContractServiceImpl extends ServiceImpl<TbProduceContractMapper, TbProduceContract> implements TbProduceContractService {

    @Override
    public PageResult<TbProduceContract> page(TbProduceContractParam tbProduceContractParam) {
        QueryWrapper<TbProduceContract> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbProduceContractParam)) {

            // 根据订单号 查询
            if (ObjectUtil.isNotEmpty(tbProduceContractParam.getContractNo())) {
                queryWrapper.lambda().eq(TbProduceContract::getContractNo, tbProduceContractParam.getContractNo());
            }
            // 根据订单id 查询
            if (ObjectUtil.isNotEmpty(tbProduceContractParam.getWorkOrderId())) {
                queryWrapper.lambda().eq(TbProduceContract::getWorkOrderId, tbProduceContractParam.getWorkOrderId());
            }
            // 根据工厂方 查询
            if (ObjectUtil.isNotEmpty(tbProduceContractParam.getFactoryId())) {
                queryWrapper.lambda().eq(TbProduceContract::getFactoryId, tbProduceContractParam.getFactoryId());
            }
            // 根据乙方 查询
            if (ObjectUtil.isNotEmpty(tbProduceContractParam.getService())) {
                queryWrapper.lambda().eq(TbProduceContract::getService, tbProduceContractParam.getService());
            }
            // 根据 查询
            if (ObjectUtil.isNotEmpty(tbProduceContractParam.getSyscreated())) {
                queryWrapper.lambda().eq(TbProduceContract::getSyscreated, tbProduceContractParam.getSyscreated());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbProduceContract> list(TbProduceContractParam tbProduceContractParam) {
        return this.list();
    }

    @Override
    public void add(TbProduceContractParam tbProduceContractParam) {
        TbProduceContract tbProduceContract = new TbProduceContract();
        BeanUtil.copyProperties(tbProduceContractParam, tbProduceContract);
        this.save(tbProduceContract);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbProduceContractParam> tbProduceContractParamList) {
        tbProduceContractParamList.forEach(tbProduceContractParam -> {
            this.removeById(tbProduceContractParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbProduceContractParam tbProduceContractParam) {
        TbProduceContract tbProduceContract = this.queryTbProduceContract(tbProduceContractParam);
        BeanUtil.copyProperties(tbProduceContractParam, tbProduceContract);
        this.updateById(tbProduceContract);
    }

    @Override
    public TbProduceContract detail(TbProduceContractParam tbProduceContractParam) {
        return this.queryTbProduceContract(tbProduceContractParam);
    }

    /**
     * 获取加工合同
     *
     * @author wjc
     * @date 2022-01-20 10:11:34
     */
    private TbProduceContract queryTbProduceContract(TbProduceContractParam tbProduceContractParam) {
        TbProduceContract tbProduceContract = this.getById(tbProduceContractParam.getId());
        if (ObjectUtil.isNull(tbProduceContract)) {
            throw new ServiceException(TbProduceContractExceptionEnum.NOT_EXIST);
        }
        return tbProduceContract;
    }

    @Override
    public void export(TbProduceContractParam tbProduceContractParam) {
        List<TbProduceContract> list = this.list(tbProduceContractParam);
        PoiUtil.exportExcelWithStream("SnowyTbProduceContract.xls", TbProduceContract.class, list);
    }

}
