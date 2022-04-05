
package vip.xiaonuo.main.modular.tbpubliseprice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.core.util.SMSUtil;
import vip.xiaonuo.main.modular.sysConfig.entity.ProductType;
import vip.xiaonuo.main.modular.sysConfig.service.ProductTypeService;
import vip.xiaonuo.main.modular.tbcustomeraccount.entity.TbCustomerAccount;
import vip.xiaonuo.main.modular.tbcustomeraccount.service.TbCustomerAccountService;
import vip.xiaonuo.main.modular.tbcustomerask.entity.TbCustomerAsk;
import vip.xiaonuo.main.modular.tbcustomerask.service.TbCustomerAskService;
import vip.xiaonuo.main.modular.tbpubliseprice.entity.TbPublisePrice;
import vip.xiaonuo.main.modular.tbpubliseprice.enums.TbPublisePriceExceptionEnum;
import vip.xiaonuo.main.modular.tbpubliseprice.mapper.TbPublisePriceMapper;
import vip.xiaonuo.main.modular.tbpubliseprice.param.TbPublisePriceParam;
import vip.xiaonuo.main.modular.tbpubliseprice.service.TbPublisePriceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报价记录service接口实现类
 *
 * @author 邾茂星
 * @date 2022-01-11 10:11:12
 */
@Service
public class TbPublisePriceServiceImpl extends ServiceImpl<TbPublisePriceMapper, TbPublisePrice> implements TbPublisePriceService {

    @Autowired
    private TbCustomerAskService tbCustomerAskService;
    @Autowired
    private TbCustomerAccountService tbCustomerAccountService;
    @Autowired
    private ProductTypeService productTypeService;

    @Override
    public PageResult<TbPublisePrice> page(TbPublisePriceParam tbPublisePriceParam) {
        QueryWrapper<TbPublisePrice> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbPublisePriceParam)) {

            // 根据询价单id 查询
            if (ObjectUtil.isNotEmpty(tbPublisePriceParam.getAskOrderId())) {
                queryWrapper.lambda().eq(TbPublisePrice::getAskOrderId, tbPublisePriceParam.getAskOrderId());
            }
            // 根据服务方报价 查询
            if (ObjectUtil.isNotEmpty(tbPublisePriceParam.getServicePrice())) {
                queryWrapper.lambda().eq(TbPublisePrice::getServicePrice, tbPublisePriceParam.getServicePrice());
            }
            // 根据报价人 查询
            if (ObjectUtil.isNotEmpty(tbPublisePriceParam.getPublicPriceUser())) {
                queryWrapper.lambda().eq(TbPublisePrice::getPublicPriceUser, tbPublisePriceParam.getPublicPriceUser());
            }
            // 根据报价次 查询
            if (ObjectUtil.isNotEmpty(tbPublisePriceParam.getPublicPriceTime())) {
                queryWrapper.lambda().eq(TbPublisePrice::getPublicPriceTime, tbPublisePriceParam.getPublicPriceTime());
            }
            // 根据备注 查询
            if (ObjectUtil.isNotEmpty(tbPublisePriceParam.getNote())) {
                queryWrapper.lambda().eq(TbPublisePrice::getNote, tbPublisePriceParam.getNote());
            }
            // 根据所属客户 查询
            if (ObjectUtil.isNotEmpty(tbPublisePriceParam.getCustomerId())) {
                queryWrapper.lambda().eq(TbPublisePrice::getCustomerId, tbPublisePriceParam.getCustomerId());
            }
            // 根据是否后台创建 查询
            if (ObjectUtil.isNotEmpty(tbPublisePriceParam.getSyscreated())) {
                queryWrapper.lambda().eq(TbPublisePrice::getSyscreated, tbPublisePriceParam.getSyscreated());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbPublisePrice> list(TbPublisePriceParam tbPublisePriceParam) {
        return this.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(TbPublisePriceParam tbPublisePriceParam) {
        Long askOrderId = tbPublisePriceParam.getAskOrderId();
        TbPublisePrice tbPublisePrice = new TbPublisePrice();
        BeanUtil.copyProperties(tbPublisePriceParam, tbPublisePrice);
        this.save(tbPublisePrice);
        //修改询价单状态
        TbCustomerAsk tbCustomerAsk = new TbCustomerAsk();
        tbCustomerAsk.setId(askOrderId);
        tbCustomerAsk.setAskStatus(2000);
        tbCustomerAskService.updateById(tbCustomerAsk);
        //发送短信通知 开启线程
        ThreadUtil.execute(() -> sendSMS(askOrderId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbPublisePriceParam> tbPublisePriceParamList) {
        tbPublisePriceParamList.forEach(tbPublisePriceParam -> {
            this.removeById(tbPublisePriceParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbPublisePriceParam tbPublisePriceParam) {
        TbPublisePrice tbPublisePrice = this.queryTbPublisePrice(tbPublisePriceParam);
        BeanUtil.copyProperties(tbPublisePriceParam, tbPublisePrice);
        this.updateById(tbPublisePrice);
    }

    @Override
    public TbPublisePrice detail(TbPublisePriceParam tbPublisePriceParam) {
        return this.queryTbPublisePrice(tbPublisePriceParam);
    }

    /**
     * 获取报价记录
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    private TbPublisePrice queryTbPublisePrice(TbPublisePriceParam tbPublisePriceParam) {
        TbPublisePrice tbPublisePrice = this.getById(tbPublisePriceParam.getId());
        if (ObjectUtil.isNull(tbPublisePrice)) {
            throw new ServiceException(TbPublisePriceExceptionEnum.NOT_EXIST);
        }
        return tbPublisePrice;
    }

    @Override
    public void export(TbPublisePriceParam tbPublisePriceParam) {
        List<TbPublisePrice> list = this.list(tbPublisePriceParam);
        PoiUtil.exportExcelWithStream("SnowyTbPublisePrice.xls", TbPublisePrice.class, list);
    }

    /**
     * @param askOrderId
     * @Description: 发送客户短信通知
     * @author 邾茂星
     * @date 2022/3/7 9:27
     */
    public void sendSMS(Long askOrderId) {
        TbCustomerAsk customerAsk = tbCustomerAskService.getById(askOrderId);
        Long categoryid = customerAsk.getCategory();
        String askOrderNo = customerAsk.getAskOrderNo();
        Long accountId = customerAsk.getAccountId();
        ProductType productType = productTypeService.getById(categoryid);
        String typeName = productType.getTypeName();
        TbCustomerAccount customerAccount = tbCustomerAccountService.getById(accountId);
        String ownerTel = customerAccount.getOwnerTel();
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> map = new HashMap<>();
        map.put("to", ownerTel);
        list.add(map);
        String content = StrUtil.format("尊敬的顾客：您的询价单【{}】业务员已报价，请前往微信小程序'尚加智造'中查看。感谢您的支持！", typeName);
        SMSUtil.sendSjSMS(list, content);
    }

}
