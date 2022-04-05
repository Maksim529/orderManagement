
package vip.xiaonuo.main.modular.sysConfig.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.api.auth.entity.SysUser;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.sysConfig.entity.Factory;
import vip.xiaonuo.main.modular.sysConfig.entity.TbFactoryMerchandiser;
import vip.xiaonuo.main.modular.sysConfig.enums.FactoryExceptionEnum;
import vip.xiaonuo.main.modular.sysConfig.mapper.FactoryMapper;
import vip.xiaonuo.main.modular.sysConfig.param.FactoryParam;
import vip.xiaonuo.main.modular.sysConfig.service.FactoryService;
import vip.xiaonuo.main.modular.sysConfig.service.TbFactoryMerchandiserService;
import vip.xiaonuo.main.modular.user.service.SysUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 工厂信息service接口实现类
 *
 * @author 邾茂星
 * @date 2022-01-10 16:37:50
 */
@Service
public class FactoryServiceImpl extends ServiceImpl<FactoryMapper, Factory> implements FactoryService {
    @Autowired
    private TbFactoryMerchandiserService tbFactoryMerchandiserService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public PageResult<Factory> page(FactoryParam factoryParam) {
        QueryWrapper<Factory> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(factoryParam)) {

            // 根据工厂名 查询
            if (ObjectUtil.isNotEmpty(factoryParam.getName())) {
                queryWrapper.lambda().like(Factory::getName, factoryParam.getName());
            }
            // 根据联系人 查询
            if (ObjectUtil.isNotEmpty(factoryParam.getOwner())) {
                queryWrapper.lambda().like(Factory::getOwner, factoryParam.getOwner());
            }
            // 根据联系人电话 查询
            if (ObjectUtil.isNotEmpty(factoryParam.getOwnerTel())) {
                queryWrapper.lambda().like(Factory::getOwnerTel, factoryParam.getOwnerTel());
            }
            // 根据公司代码 查询
            if (ObjectUtil.isNotEmpty(factoryParam.getCode())) {
                queryWrapper.lambda().like(Factory::getCode, factoryParam.getCode());
            }
            // 根据地址 查询
            if (ObjectUtil.isNotEmpty(factoryParam.getAddress())) {
                queryWrapper.lambda().like(Factory::getAddress, factoryParam.getAddress());
            }
            // 根据状态（1、合作中 2、停止合作 3、潜在客户） 查询
            if (ObjectUtil.isNotEmpty(factoryParam.getStatus())) {
                queryWrapper.lambda().eq(Factory::getStatus, factoryParam.getStatus());
            }
        }
        Page<Factory> page = this.page(PageFactory.defaultPage(), queryWrapper);
        List<Factory> records = page.getRecords();
        if(ObjectUtil.isNotEmpty(records)){
            for (Factory factory : records) {
                Long id = factory.getId();
                //跟单员信息
                LambdaQueryWrapper<TbFactoryMerchandiser> qw = new LambdaQueryWrapper<>();
                qw.eq(TbFactoryMerchandiser::getFactoryId, id);
                List<TbFactoryMerchandiser> list = tbFactoryMerchandiserService.list(qw);
                List<Long> userIdList = new ArrayList<>();
                if(ObjectUtil.isNotEmpty(list)){
                    for (TbFactoryMerchandiser tbFactoryMerchandiser : list) {
                        Long userId = tbFactoryMerchandiser.getUserId();
                        userIdList.add(userId);
                    }
                }
                factory.setMerchandiserList(list);
                factory.setUserIdList(userIdList);
            }
        }
        return new PageResult<>(page);
    }

    @Override
    public List<Factory> list(FactoryParam factoryParam) {
        return this.list();
    }

    @Override
    public void add(FactoryParam factoryParam) {
        Factory factory = new Factory();
        List<Long> userIdList = factoryParam.getUserIdList();
        BeanUtil.copyProperties(factoryParam, factory);
        this.save(factory);
        Long id = factory.getId();
        //保存跟单员
        for (Long userId : userIdList) {
            SysUser user = sysUserService.getById(userId);
            TbFactoryMerchandiser tbFactoryMerchandiser = new TbFactoryMerchandiser();
            tbFactoryMerchandiser.setFactoryId(id);
            tbFactoryMerchandiser.setUserId(userId);
            if(user != null){
                tbFactoryMerchandiser.setUserName(user.getName());
            }
            tbFactoryMerchandiserService.save(tbFactoryMerchandiser);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FactoryParam> factoryParamList) {
        factoryParamList.forEach(factoryParam -> {
            this.removeById(factoryParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FactoryParam factoryParam) {
        Long id = factoryParam.getId();
        Factory factory = this.queryFactory(factoryParam);
        BeanUtil.copyProperties(factoryParam, factory);
        this.updateById(factory);
        List<Long> userIdList = factoryParam.getUserIdList();
        //删除原绑定的跟单员
        LambdaQueryWrapper<TbFactoryMerchandiser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbFactoryMerchandiser::getFactoryId, id);
        tbFactoryMerchandiserService.remove(queryWrapper);
        //添加
        for (Long userId : userIdList) {
            SysUser user = sysUserService.getById(userId);
            TbFactoryMerchandiser tbFactoryMerchandiser = new TbFactoryMerchandiser();
            tbFactoryMerchandiser.setFactoryId(id);
            tbFactoryMerchandiser.setUserId(userId);
            if(user != null){
                tbFactoryMerchandiser.setUserName(user.getName());
            }
            tbFactoryMerchandiserService.save(tbFactoryMerchandiser);
        }
    }

    @Override
    public Factory detail(FactoryParam factoryParam) {
        return this.queryFactory(factoryParam);
    }

    /**
     * 获取工厂信息
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    private Factory queryFactory(FactoryParam factoryParam) {
        Long id = factoryParam.getId();
        Factory factory = this.getById(id);
        if (ObjectUtil.isNull(factory)) {
            throw new ServiceException(FactoryExceptionEnum.NOT_EXIST);
        }
        //跟单员信息
        LambdaQueryWrapper<TbFactoryMerchandiser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbFactoryMerchandiser::getFactoryId, id);
        List<TbFactoryMerchandiser> list = tbFactoryMerchandiserService.list(queryWrapper);
        List<Long> userIdList = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(list)){
            for (TbFactoryMerchandiser tbFactoryMerchandiser : list) {
                Long userId = tbFactoryMerchandiser.getUserId();
                userIdList.add(userId);
            }
        }
        factory.setMerchandiserList(list);
        factory.setUserIdList(userIdList);
        return factory;
    }

    @Override
    public void export(FactoryParam factoryParam) {
        List<Factory> list = this.list(factoryParam);
        PoiUtil.exportExcelWithStream("工厂信息.xls", Factory.class, list);
    }

}
