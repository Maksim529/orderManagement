
package vip.xiaonuo.main.modular.sysConfig.service;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.sysConfig.entity.ProductType;
import vip.xiaonuo.main.modular.sysConfig.param.ProductTypeParam;

import java.util.List;

/**
 * 品类service接口
 *
 * @author 邾茂星
 * @date 2022-01-10 13:13:25
 */
public interface ProductTypeService extends IService<ProductType> {

    /**
     * 查询品类
     *
     * @author 邾茂星
     * @date 2022-01-10 13:13:25
     */
    PageResult<ProductType> page(ProductTypeParam productTypeParam);

    /**
     * 品类列表
     *
     * @author 邾茂星
     * @date 2022-01-10 13:13:25
     */
    List<ProductType> list(ProductTypeParam productTypeParam);

    /**
     * 添加品类
     *
     * @author 邾茂星
     * @date 2022-01-10 13:13:25
     */
    void add(ProductTypeParam productTypeParam);

    /**
     * 删除品类
     *
     * @author 邾茂星
     * @date 2022-01-10 13:13:25
     */
    void delete(List<ProductTypeParam> productTypeParamList);

    /**
     * 编辑品类
     *
     * @author 邾茂星
     * @date 2022-01-10 13:13:25
     */
    void edit(ProductTypeParam productTypeParam);

    /**
     * 查看品类
     *
     * @author 邾茂星
     * @date 2022-01-10 13:13:25
     */
     ProductType detail(ProductTypeParam productTypeParam);

    /**
     * 导出品类
     *
     * @author 邾茂星
     * @date 2022-01-10 13:13:25
     */
     void export(ProductTypeParam productTypeParam);

     /**
      * @Description: pid查询
      * @author 邾茂星
      * @date 2022/1/13 9:24
      * @param pid
      * @return List<Dict>
      */
     List<Dict> findListByPid(Long pid);

     /**
      * @Description: 查询品类名称：女装-裙装-连衣裙
      * @author 邾茂星
      * @date 2022/1/18 9:03
      * @param id
      * @return String
      */
     String getNamesById(Long id, String name);

    /**
     * @Description: 校验品类名称
     * @author 邾茂星
     * @date 2022/1/21 16:56
     * @param pid
     * @param typeName
     * @return int
     */
    int checkTypeName(Long pid, String typeName);

    /**
     * @Description: 查询种类名称
     * @author 邾茂星
     * @date 2022/1/21 17:33
     * @return Dict
     */
    List<Dict> findSortList(Integer depth);

}
