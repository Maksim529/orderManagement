
package vip.xiaonuo.main.modular.sysConfig.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.main.modular.sysConfig.entity.ProductType;
import vip.xiaonuo.main.modular.sysConfig.param.ProductTypeParam;

import java.util.List;

/**
 * 品类
 *
 * @author 邾茂星
 * @date 2022-01-10 13:13:25
 */
public interface ProductTypeMapper extends BaseMapper<ProductType> {

    List<ProductType> findList(ProductTypeParam productTypeParam);

    /**
     * 获取分页列表
     *
     * @param page         分页参数
     * @param queryWrapper 查询参数
     */
    Page<ProductType> page(@Param("page") Page page, @Param("ew") QueryWrapper queryWrapper);
}
