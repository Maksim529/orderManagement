
package vip.xiaonuo.main.modular.tbcustomeraccount.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import vip.xiaonuo.common.consts.CommonConstant;
import vip.xiaonuo.common.enums.CommonStatusEnum;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.tbcustomer.entity.TbCustomer;
import vip.xiaonuo.main.modular.tbcustomer.service.TbCustomerService;
import vip.xiaonuo.main.modular.tbcustomeraccount.entity.TbCustomerAccount;
import vip.xiaonuo.main.modular.tbcustomeraccount.enums.TbCustomerAccountExceptionEnum;
import vip.xiaonuo.main.modular.tbcustomeraccount.mapper.TbCustomerAccountMapper;
import vip.xiaonuo.main.modular.tbcustomeraccount.param.TbCustomerAccountParam;
import vip.xiaonuo.main.modular.tbcustomeraccount.service.TbCustomerAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户账号service接口实现类
 *
 * @author 邾茂星
 * @date 2022-01-11 09:49:03
 */
@Service
public class TbCustomerAccountServiceImpl extends ServiceImpl<TbCustomerAccountMapper, TbCustomerAccount> implements TbCustomerAccountService {
    @Autowired
    private TbCustomerService tbCustomerService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public PageResult<TbCustomerAccount> page(TbCustomerAccountParam tbCustomerAccountParam) {
        QueryWrapper<TbCustomerAccount> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbCustomerAccountParam)) {

            // 根据账号名 查询
            if (ObjectUtil.isNotEmpty(tbCustomerAccountParam.getAccount())) {
                queryWrapper.eq("a.account", tbCustomerAccountParam.getAccount());
            }
            // 根据联系人 查询
            if (ObjectUtil.isNotEmpty(tbCustomerAccountParam.getOwner())) {
                queryWrapper.like("a.owner", tbCustomerAccountParam.getOwner());
            }
            // 根据联系人电话 查询
            if (ObjectUtil.isNotEmpty(tbCustomerAccountParam.getOwnerTel())) {
                queryWrapper.like("a.owner_tel", tbCustomerAccountParam.getOwnerTel());
            }
            // 根据状态（1、使用中 2、停止使用 3、注销） 查询
            if (ObjectUtil.isNotEmpty(tbCustomerAccountParam.getStatus())) {
                queryWrapper.eq("a.status", tbCustomerAccountParam.getStatus());
            }
            // 根据所属客户 查询
            if (ObjectUtil.isNotEmpty(tbCustomerAccountParam.getCustomerId())) {
                queryWrapper.eq("a.customer_id", tbCustomerAccountParam.getCustomerId());
            }
            // 根据openid 查询
            if (ObjectUtil.isNotEmpty(tbCustomerAccountParam.getOpenid())) {
                queryWrapper.eq("a.openid", tbCustomerAccountParam.getOpenid());
            }
            if (ObjectUtil.isNotEmpty(tbCustomerAccountParam.getCustomerName())) {
                queryWrapper.like("b.name", tbCustomerAccountParam.getCustomerName());
            }
        }
        queryWrapper.orderByDesc("a.id");
        return new PageResult<>(baseMapper.findPage(PageFactory.defaultPage(), queryWrapper));
    }
    @Override
    //模糊搜索
    public PageResult<TbCustomerAccount> search(TbCustomerAccountParam tbCustomerAccountParam) {
        //QueryWrapper<TbCustomerAccount> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<TbCustomerAccount> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(tbCustomerAccountParam)) {
            // 根据账号名 查询
            if (ObjectUtil.isNotEmpty(tbCustomerAccountParam.getSearchValue())) {
                queryWrapper.like (TbCustomerAccount::getAccount, tbCustomerAccountParam.getSearchValue())
                .or().like(TbCustomerAccount::getOwner, tbCustomerAccountParam.getSearchValue())
                        .or().like(TbCustomerAccount::getOwnerTel, tbCustomerAccountParam.getSearchValue())
                ;
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbCustomerAccount> list(TbCustomerAccountParam tbCustomerAccountParam) {
        return this.list();
    }

    @Override
    public void add(TbCustomerAccountParam tbCustomerAccountParam) {
        TbCustomerAccount tbCustomerAccount = new TbCustomerAccount();
        BeanUtil.copyProperties(tbCustomerAccountParam, tbCustomerAccount);
        boolean checkPhone = checkPhone(tbCustomerAccount.getOwnerTel());
        if(checkPhone){
            //设置code
            String customerCode = this.createCustomerCode();
            tbCustomerAccount.setCode(customerCode);
            this.save(tbCustomerAccount);
            Long customerId = tbCustomerAccountParam.getCustomerId();
            if(customerId != null){
                TbCustomer tbCustomer = tbCustomerService.getById(customerId);
                if(tbCustomer != null){
                    tbCustomerAccount.setCustomerName(tbCustomer.getName());
                }
            }
            //添加Redis缓存
            String redisKey = "customerAccount:"+tbCustomerAccount.getId();
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(tbCustomerAccount), Duration.ofDays(1));
        }else {
            throw new ServiceException(500, "电话号码已存在！");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbCustomerAccountParam> tbCustomerAccountParamList) {
        tbCustomerAccountParamList.forEach(tbCustomerAccountParam -> {
            this.removeById(tbCustomerAccountParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbCustomerAccountParam tbCustomerAccountParam) {
        boolean checkPhone = true;
        TbCustomerAccount tbCustomerAccount = this.queryTbCustomerAccount(tbCustomerAccountParam);
        BeanUtil.copyProperties(tbCustomerAccountParam, tbCustomerAccount);
        TbCustomerAccount customerAccount = this.getById(tbCustomerAccount.getId());
        if(customerAccount != null ){
            String ownerTel = customerAccount.getOwnerTel();
            String newOwnerTel = tbCustomerAccount.getOwnerTel();
            if(!ownerTel.equals(newOwnerTel)){
                checkPhone = checkPhone(newOwnerTel);
            }
        }
        if(checkPhone){
            this.updateById(tbCustomerAccount);
            Long customerId = tbCustomerAccountParam.getCustomerId();
            if(customerId != null){
                TbCustomer tbCustomer = tbCustomerService.getById(customerId);
                if(tbCustomer != null){
                    tbCustomerAccount.setCustomerName(tbCustomer.getName());
                }
            }
            //更新Redis
            String redisKey = "customerAccount:"+tbCustomerAccount.getId();
            redisTemplate.delete(redisKey);
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(tbCustomerAccount), Duration.ofDays(1));
        }else {
            throw new ServiceException(500, "电话号码已存在！");
        }
    }

    @Override
    public TbCustomerAccount detail(TbCustomerAccountParam tbCustomerAccountParam) {
        TbCustomerAccount customerAccount = this.queryTbCustomerAccount(tbCustomerAccountParam);
        Long customerId = customerAccount.getCustomerId();
        if(customerId != null){
            TbCustomer tbCustomer = tbCustomerService.getById(customerId);
            if(tbCustomer != null){
                customerAccount.setCustomerName(tbCustomer.getName());
            }
        }
        return customerAccount;
    }

    /**
     * 获取客户账号
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:03
     */
    private TbCustomerAccount queryTbCustomerAccount(TbCustomerAccountParam tbCustomerAccountParam) {
        TbCustomerAccount tbCustomerAccount = this.getById(tbCustomerAccountParam.getId());
        if (ObjectUtil.isNull(tbCustomerAccount)) {
            throw new ServiceException(TbCustomerAccountExceptionEnum.NOT_EXIST);
        }
        return tbCustomerAccount;
    }

    @Override
    public void export(TbCustomerAccountParam tbCustomerAccountParam) {
        List<TbCustomerAccount> list = this.list(tbCustomerAccountParam);
        PoiUtil.exportExcelWithStream("SnowyTbCustomerAccount.xls", TbCustomerAccount.class, list);
    }

    /**
     * @Description: 校验电话号
     * @author 邾茂星
     * @date 2022/2/16 16:46
     * @param ownerTel
     * @return boolean
     */
    private boolean checkPhone(String ownerTel){
        boolean isPhone = false;
        if(StrUtil.isNotBlank(ownerTel)){
            LambdaQueryWrapper<TbCustomerAccount> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TbCustomerAccount::getOwnerTel, ownerTel);
            int count = this.count(queryWrapper);
            if(count == 0){
                isPhone = true;
            }
        }
        return isPhone;
    }

    /**
     * @Description: 创建客户编码
     * @author 邾茂星
     * @date 2022/3/8 9:50
     * @return String
     */
    @Override
    public synchronized String createCustomerCode() {
        String code = "";
        LambdaQueryWrapper<TbCustomerAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNotNull(TbCustomerAccount::getCode);
        int count = this.count(queryWrapper) + 1001;
        String strNum = String.valueOf(count);
        if (strNum.length() < 5) {
            strNum = String.format("%05d", count);
        }
        code = "V" + strNum;
        return code;
    }

    /**
     * @Description: 根据账号id，查询所属公司id
     * @author 邾茂星
     * @date 2022/3/24 15:32
     * @param accountId
     * @return Long
     */
    @Override
    public Long getCustomerIdByAccountId(Long accountId) {
        Long customerId = null;
        LambdaQueryWrapper<TbCustomerAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbCustomerAccount::getId, accountId);
        queryWrapper.select(TbCustomerAccount::getCustomerId);
        TbCustomerAccount tbCustomerAccount = getOne(queryWrapper);
        if(tbCustomerAccount != null){
            customerId = tbCustomerAccount.getCustomerId();
        }
        return customerId;
    }

    @Override
    public TbCustomerAccount getById(Long id) {
        String redisKey = "customerAccount:" + id;
        TbCustomerAccount customerAccount = new TbCustomerAccount();
        Object obj = redisTemplate.opsForValue().get(redisKey);
        if(obj != null){
            customerAccount = JSON.parseObject(obj.toString(), TbCustomerAccount.class);
        }else {
            LambdaQueryWrapper<TbCustomerAccount> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TbCustomerAccount::getId, id);
            customerAccount = getOne(queryWrapper);
            if(customerAccount != null){
                Long customerId = customerAccount.getCustomerId();
                TbCustomer tbCustomer = tbCustomerService.getById(customerId);
                if(tbCustomer != null){
                    customerAccount.setCustomerName(tbCustomer.getName());
                }
                redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(customerAccount), Duration.ofDays(1));
            }
        }
        return customerAccount;
    }

    @Override
    public boolean delRedisKey(Long id) {
        String redisKey = "customerAccount:" + id;
        return redisTemplate.delete(redisKey);
    }

    @Override
    public List<TbCustomerAccount> getByCustomerId(Long customerId) {
        List<TbCustomerAccount> list = new ArrayList<>();
        if(customerId != null){
            LambdaQueryWrapper<TbCustomerAccount> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TbCustomerAccount::getCustomerId, customerId)
                    .eq(TbCustomerAccount::getStatus, 1);
            list = baseMapper.selectList(queryWrapper);
        }
        return list;
    }
}
