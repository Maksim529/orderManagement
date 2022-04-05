
package vip.xiaonuo.main.modular.sizeconfig.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.xiaonuo.common.consts.CommonConstant;
import vip.xiaonuo.common.enums.CommonStatusEnum;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.sizeconfig.entity.SizeConfig;
import vip.xiaonuo.main.modular.sizeconfig.enums.SizeConfigExceptionEnum;
import vip.xiaonuo.main.modular.sizeconfig.mapper.SizeConfigMapper;
import vip.xiaonuo.main.modular.sizeconfig.param.SizeConfigParam;
import vip.xiaonuo.main.modular.sizeconfig.service.SizeConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 尺码配置service接口实现类
 *
 * @author 邾茂星
 * @date 2022-01-11 09:49:59
 */
@Service
public class SizeConfigServiceImpl extends ServiceImpl<SizeConfigMapper, SizeConfig> implements SizeConfigService {

    @Override
    public PageResult<SizeConfig> page(SizeConfigParam sizeConfigParam) {
        QueryWrapper<SizeConfig> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(sizeConfigParam)) {

            // 根据尺码组名称 查询
            if (ObjectUtil.isNotEmpty(sizeConfigParam.getCateName())) {
                queryWrapper.lambda().like(SizeConfig::getCateName, sizeConfigParam.getCateName());
            }
            // 根据尺码信息 查询
            if (ObjectUtil.isNotEmpty(sizeConfigParam.getSizeInfor())) {
                queryWrapper.lambda().like(SizeConfig::getSizeInfor, sizeConfigParam.getSizeInfor());
            }
            // 根据是否默认 查询
            if (ObjectUtil.isNotEmpty(sizeConfigParam.getIsDefault())) {
                queryWrapper.lambda().eq(SizeConfig::getIsDefault, sizeConfigParam.getIsDefault());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<SizeConfig> list(SizeConfigParam sizeConfigParam) {
        QueryWrapper<SizeConfig> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(sizeConfigParam)) {
            // 根据尺码组名称 查询
            if (ObjectUtil.isNotEmpty(sizeConfigParam.getCateName())) {
                queryWrapper.lambda().eq(SizeConfig::getCateName, sizeConfigParam.getCateName());
            }
            // 根据是否默认 查询
            if (ObjectUtil.isNotEmpty(sizeConfigParam.getIsDefault())) {
                queryWrapper.lambda().eq(SizeConfig::getIsDefault, sizeConfigParam.getIsDefault());
            }
            if (ObjectUtil.isNotEmpty(sizeConfigParam.getSearchValue())) {
                queryWrapper.lambda().like(SizeConfig::getCateName, sizeConfigParam.getSearchValue());
            }
        }
        return this.list(queryWrapper);
    }

    @Override
    public void add(SizeConfigParam sizeConfigParam) {
        SizeConfig sizeConfig = new SizeConfig();
        BeanUtil.copyProperties(sizeConfigParam, sizeConfig);
        this.save(sizeConfig);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SizeConfigParam> sizeConfigParamList) {
        sizeConfigParamList.forEach(sizeConfigParam -> {
            this.removeById(sizeConfigParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SizeConfigParam sizeConfigParam) {
        SizeConfig sizeConfig = this.querySizeConfig(sizeConfigParam);
        BeanUtil.copyProperties(sizeConfigParam, sizeConfig);
        this.updateById(sizeConfig);
    }

    @Override
    public SizeConfig detail(SizeConfigParam sizeConfigParam) {
        return this.querySizeConfig(sizeConfigParam);
    }

    /**
     * 获取尺码配置
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    private SizeConfig querySizeConfig(SizeConfigParam sizeConfigParam) {
        SizeConfig sizeConfig = this.getById(sizeConfigParam.getId());
        if (ObjectUtil.isNull(sizeConfig)) {
            throw new ServiceException(SizeConfigExceptionEnum.NOT_EXIST);
        }
        return sizeConfig;
    }

    @Override
    public void export(SizeConfigParam sizeConfigParam) {
        List<SizeConfig> list = this.list(sizeConfigParam);
        PoiUtil.exportExcelWithStream("SnowySizeConfig.xls", SizeConfig.class, list);
    }

    /**
     * @Description: 校验尺码组名称是否重名
     * @author 邾茂星
     * @date 2022/1/11 11:23
     * @param cateName
     * @return int
     */
    @Override
    public int checkCateName(String cateName) {
        int resultNum = 0;
        if(StrUtil.isNotBlank(cateName)){
            QueryWrapper<SizeConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SizeConfig::getCateName, cateName);
            List<SizeConfig> list = this.list(queryWrapper);
            if(CollUtil.isNotEmpty(list)){
                resultNum = list.size();
            }
        }
        return resultNum;
    }

    /**
     * @Description: 设为默认
     * @author 邾茂星
     * @date 2022/1/11 11:23
     * @param id
     * @return int
     */
    @Override
    public int saveDefault(Long id) {
        int resultNum = 0;
        if(id != null){
            SizeConfig sizeConfig = this.getById(id);
            if(sizeConfig != null){
                SizeConfig config = new SizeConfig();
                config.setIsDefault(0);
                UpdateWrapper<SizeConfig> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("is_default",1);
                this.update(config, updateWrapper);

                sizeConfig.setIsDefault(1);
                this.updateById(sizeConfig);
                resultNum++;
            }
        }
        return resultNum;
    }

    /**
     * @Description: 查询尺码信息
     * @author 邾茂星
     * @date 2022/1/13 10:19
     * @param id
     * @return List<String>
     */
    @Override
    public List<String> getSizeInforList(Long id) {
        String sizeInfor = "";
        if(id != null){
            SizeConfig sizeConfig = this.getById(id);
            if(sizeConfig != null){
                sizeInfor = sizeConfig.getSizeInfor();
            }
        }
        if(StrUtil.isBlank(sizeInfor)){
            //查询默认尺码组
            QueryWrapper<SizeConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("id","cate_name","size_infor");
            queryWrapper.eq("is_default",1);
            SizeConfig sizeConfig = this.getOne(queryWrapper);
            if(sizeConfig != null){
                sizeInfor = sizeConfig.getSizeInfor();
            }
        }
        List<String> list = new ArrayList<>();
        if(StrUtil.isNotBlank(sizeInfor)){
            String[] split = sizeInfor.split(",");
            list = Arrays.asList(split);
            //去重
            list = list.stream().distinct().collect(Collectors.toList());
        }
        return list;
    }
}
