
package vip.xiaonuo.main.modular.sysConfig.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.factory.TreeBuildFactory;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.sysConfig.entity.ProductType;
import vip.xiaonuo.main.modular.sysConfig.enums.ProductTypeExceptionEnum;
import vip.xiaonuo.main.modular.sysConfig.mapper.ProductTypeMapper;
import vip.xiaonuo.main.modular.sysConfig.param.ProductTypeParam;
import vip.xiaonuo.main.modular.sysConfig.service.ProductTypeService;

import java.util.ArrayList;
import java.util.List;

/**
 * 品类service接口实现类
 *
 * @author 邾茂星
 * @date 2022-01-10 13:13:25
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {

    @Override
    public PageResult<ProductType> page(ProductTypeParam productTypeParam) {
        QueryWrapper<ProductType> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(productTypeParam)) {
            // 根据类名 查询
            if (ObjectUtil.isNotEmpty(productTypeParam.getTypeName())) {
                queryWrapper.lambda().eq(ProductType::getTypeName, productTypeParam.getTypeName());
            }
            // 根据父id 查询
            if (ObjectUtil.isNotEmpty(productTypeParam.getPid())) {
                queryWrapper.lambda().eq(ProductType::getPid, productTypeParam.getPid());
            }
            // 根据品类尺码配置 查询
            if (ObjectUtil.isNotEmpty(productTypeParam.getCategorySize())) {
                queryWrapper.lambda().eq(ProductType::getCategorySize, productTypeParam.getCategorySize());
            }
        }
        return new PageResult<>(baseMapper.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<ProductType> list(ProductTypeParam productTypeParam) {
        List<ProductType> list = baseMapper.findList(productTypeParam);
        //构建树
        List<ProductType> productTypes = new TreeBuildFactory<ProductType>().doTreeBuild(list);
        return productTypes;
    }

    @Override
    public void add(ProductTypeParam productTypeParam) {
        ProductType productType = new ProductType();
        BeanUtil.copyProperties(productTypeParam, productType);
        //设置深度值
        int depth = getDepth(1, productType.getPid());
        productType.setDepth(depth);
        this.save(productType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ProductTypeParam> productTypeParamList) {
        productTypeParamList.forEach(productTypeParam -> {
            this.removeById(productTypeParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ProductTypeParam productTypeParam) {
        ProductType productType = this.queryProductType(productTypeParam);
        BeanUtil.copyProperties(productTypeParam, productType);
        //设置深度值
        int depth = this.getDepth(1, productType.getPid());
        productType.setDepth(depth);
        this.updateById(productType);
    }

    @Override
    public ProductType detail(ProductTypeParam productTypeParam) {
        return this.queryProductType(productTypeParam);
    }

    /**
     * 获取品类
     *
     * @author 邾茂星
     * @date 2022-01-10 13:13:25
     */
    private ProductType queryProductType(ProductTypeParam productTypeParam) {
        ProductType productType = this.getById(productTypeParam.getId());
        if (ObjectUtil.isNull(productType)) {
            throw new ServiceException(ProductTypeExceptionEnum.NOT_EXIST);
        }
        return productType;
    }

    @Override
    public void export(ProductTypeParam productTypeParam) {
        List<ProductType> list = this.list(productTypeParam);
        PoiUtil.exportExcelWithStream("SnowyProductType.xls", ProductType.class, list);
    }

    /**
     * @param pid
     * @return int
     * @Description: 查询树形 深度值
     * @author 邾茂星
     * @date 2022/1/12 15:41
     */
    private Integer getDepth(Integer depth, Long pid) {
        if (pid == null || pid == 0L) {
            return depth;
        } else {
            depth++;
            ProductType productType = this.getById(pid);
            if (productType != null) {
                return getDepth(depth, productType.getPid());
            } else {
                return depth;
            }
        }
    }

    /**
     * @param pid
     * @return List<Dict>
     * @Description: pid查询
     * @author 邾茂星
     * @date 2022/1/13 9:25
     */
    @Override
    public List<Dict> findListByPid(Long pid) {
        List<Dict> dictList = new ArrayList<>();
        if (pid != null) {
            QueryWrapper<ProductType> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("id", "pid", "type_name", "category_size");
            queryWrapper.eq("pid", pid);
            List<ProductType> list = this.list(queryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                list.forEach(productType -> {
                    Dict dict = new Dict();
                    dict.set("id", productType.getId());
                    dict.set("pid", productType.getPid());
                    dict.set("typeName", productType.getTypeName());
                    dict.set("categorySize", productType.getCategorySize());
                    dictList.add(dict);
                });
            }
        }
        return dictList;
    }

    /**
     * @param id
     * @return String
     * @Description: 查询品类名称：女装-裙装-连衣裙
     * @author 邾茂星
     * @date 2022/1/18 9:03
     */
    @Override
    public String getNamesById(Long id, String name) {
        if (id != null && id != 0L) {
            ProductType productType = this.getById(id);
            if(ObjectUtil.isNotEmpty(productType)){
                if (StrUtil.isBlank(name)) {
                    name = productType.getTypeName();
                } else {
                    name = productType.getTypeName() + "-" + name;
                }
                Long pid = productType.getPid();
                return getNamesById(pid, name);
            }
        }
        return name;
    }

    @Override
    public int checkTypeName(Long pid, String typeName) {
        int resNumber = 1;
        if (pid != null && StrUtil.isNotBlank(typeName)) {
            QueryWrapper<ProductType> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(ProductType::getPid, pid);
            queryWrapper.lambda().eq(ProductType::getTypeName, typeName);
            resNumber = this.count(queryWrapper);
        }
        return resNumber;
    }

    @Override
    public List<Dict> findSortList(Integer depth) {
        List<Dict> dictList = new ArrayList<>();
        if (depth == null) {
            depth = 1;
        }
        QueryWrapper<ProductType> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id").lambda().eq(ProductType::getDepth, depth);
        queryWrapper.orderByAsc("pid");
        List<ProductType> list = this.list(queryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(type -> {
                Dict dict = new Dict();
                Long id = type.getId();
                String names = getNamesById(id, null);
                dict.put("id", id);
                dict.put("name", names);
                dictList.add(dict);
            });
        }
        return dictList;
    }
}
