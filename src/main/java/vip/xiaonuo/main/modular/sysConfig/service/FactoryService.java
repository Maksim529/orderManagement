
package vip.xiaonuo.main.modular.sysConfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.sysConfig.entity.Factory;
import vip.xiaonuo.main.modular.sysConfig.param.FactoryParam;

import java.util.List;

/**
 * 工厂信息service接口
 *
 * @author 邾茂星
 * @date 2022-01-10 16:37:50
 */
public interface FactoryService extends IService<Factory> {

    /**
     * 查询工厂信息
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    PageResult<Factory> page(FactoryParam factoryParam);

    /**
     * 工厂信息列表
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    List<Factory> list(FactoryParam factoryParam);

    /**
     * 添加工厂信息
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    void add(FactoryParam factoryParam);

    /**
     * 删除工厂信息
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    void delete(List<FactoryParam> factoryParamList);

    /**
     * 编辑工厂信息
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
    void edit(FactoryParam factoryParam);

    /**
     * 查看工厂信息
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
     Factory detail(FactoryParam factoryParam);

    /**
     * 导出工厂信息
     *
     * @author 邾茂星
     * @date 2022-01-10 16:37:50
     */
     void export(FactoryParam factoryParam);

}
