
package vip.xiaonuo.main.modular.sysConfig.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonStatusEnum;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.sysConfig.entity.TbFactoryMerchandiser;
import vip.xiaonuo.main.modular.sysConfig.enums.TbFactoryMerchandiserExceptionEnum;
import vip.xiaonuo.main.modular.sysConfig.mapper.TbFactoryMerchandiserMapper;
import vip.xiaonuo.main.modular.sysConfig.param.TbFactoryMerchandiserParam;
import vip.xiaonuo.main.modular.sysConfig.service.TbFactoryMerchandiserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 工厂跟单员service接口实现类
 *
 * @author xwx
 * @date 2022-03-22 13:27:53
 */
@Service
public class TbFactoryMerchandiserServiceImpl extends ServiceImpl<TbFactoryMerchandiserMapper, TbFactoryMerchandiser> implements TbFactoryMerchandiserService {

    @Override
    public PageResult<TbFactoryMerchandiser> page(TbFactoryMerchandiserParam tbFactoryMerchandiserParam) {
        QueryWrapper<TbFactoryMerchandiser> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbFactoryMerchandiserParam)) {

            // 根据工厂id 查询
            if (ObjectUtil.isNotEmpty(tbFactoryMerchandiserParam.getFactoryId())) {
                queryWrapper.lambda().eq(TbFactoryMerchandiser::getFactoryId, tbFactoryMerchandiserParam.getFactoryId());
            }
            // 根据跟单员id 查询
            if (ObjectUtil.isNotEmpty(tbFactoryMerchandiserParam.getUserId())) {
                queryWrapper.lambda().eq(TbFactoryMerchandiser::getUserId, tbFactoryMerchandiserParam.getUserId());
            }
            // 根据跟单员名称 查询
            if (ObjectUtil.isNotEmpty(tbFactoryMerchandiserParam.getUserName())) {
                queryWrapper.lambda().eq(TbFactoryMerchandiser::getUserName, tbFactoryMerchandiserParam.getUserName());
            }
            // 根据状态：0=正常；1=停用；2=删除 查询
            if (ObjectUtil.isNotEmpty(tbFactoryMerchandiserParam.getStatus())) {
                queryWrapper.lambda().eq(TbFactoryMerchandiser::getStatus, tbFactoryMerchandiserParam.getStatus());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<TbFactoryMerchandiser> list(TbFactoryMerchandiserParam tbFactoryMerchandiserParam) {
        return this.list();
    }

    @Override
    public void add(TbFactoryMerchandiserParam tbFactoryMerchandiserParam) {
        TbFactoryMerchandiser tbFactoryMerchandiser = new TbFactoryMerchandiser();
        BeanUtil.copyProperties(tbFactoryMerchandiserParam, tbFactoryMerchandiser);
        this.save(tbFactoryMerchandiser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbFactoryMerchandiserParam> tbFactoryMerchandiserParamList) {
        tbFactoryMerchandiserParamList.forEach(tbFactoryMerchandiserParam -> {
            this.removeById(tbFactoryMerchandiserParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbFactoryMerchandiserParam tbFactoryMerchandiserParam) {
        TbFactoryMerchandiser tbFactoryMerchandiser = this.queryTbFactoryMerchandiser(tbFactoryMerchandiserParam);
        BeanUtil.copyProperties(tbFactoryMerchandiserParam, tbFactoryMerchandiser);
        this.updateById(tbFactoryMerchandiser);
    }

    @Override
    public TbFactoryMerchandiser detail(TbFactoryMerchandiserParam tbFactoryMerchandiserParam) {
        return this.queryTbFactoryMerchandiser(tbFactoryMerchandiserParam);
    }

    /**
     * 获取工厂跟单员
     *
     * @author xwx
     * @date 2022-03-22 13:27:53
     */
    private TbFactoryMerchandiser queryTbFactoryMerchandiser(TbFactoryMerchandiserParam tbFactoryMerchandiserParam) {
        TbFactoryMerchandiser tbFactoryMerchandiser = this.getById(tbFactoryMerchandiserParam.getId());
        if (ObjectUtil.isNull(tbFactoryMerchandiser)) {
            throw new ServiceException(TbFactoryMerchandiserExceptionEnum.NOT_EXIST);
        }
        return tbFactoryMerchandiser;
    }

    @Override
    public void export(TbFactoryMerchandiserParam tbFactoryMerchandiserParam) {
        List<TbFactoryMerchandiser> list = this.list(tbFactoryMerchandiserParam);
        PoiUtil.exportExcelWithStream("SnowyTbFactoryMerchandiser.xls", TbFactoryMerchandiser.class, list);
    }

    /**
     * @param factoryId
     * @return List<String>
     * @Description: 根据工厂id，查询跟单员工号
     * @author 邾茂星
     * @date 2022/3/22 15:23
     */
    @Override
    public List<String> getJobNumList(Long factoryId) {
        if (factoryId != null) {
            return baseMapper.getJobNumList(factoryId);
        }
        return null;
    }

    /**
     * @param userId
     * @return List<Long>
     * @Description: 根据userID查询所跟单的工厂id
     * @author 邾茂星
     * @date 2022/3/23 9:17
     */
    @Override
    public List<Long> getFactoryIdListByUserId(Long userId) {
        List<Long> factoryIdList = new ArrayList<>();
        if (userId != null) {
            QueryWrapper<TbFactoryMerchandiser> queryWrapper = new QueryWrapper();
            queryWrapper.select("DISTINCT factory_id");
            queryWrapper.eq("user_id", userId).eq("status", CommonStatusEnum.ENABLE);
            List<Object> objects = baseMapper.selectObjs(queryWrapper);
            if (ObjectUtil.isNotEmpty(objects)) {
                objects.forEach(object -> {
                    factoryIdList.add((Long) object);
                });
            }
        }
        return factoryIdList;
    }
}
