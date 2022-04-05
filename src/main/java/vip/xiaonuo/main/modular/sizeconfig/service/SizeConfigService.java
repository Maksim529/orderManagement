
package vip.xiaonuo.main.modular.sizeconfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.sizeconfig.entity.SizeConfig;
import vip.xiaonuo.main.modular.sizeconfig.param.SizeConfigParam;
import java.util.List;

/**
 * 尺码配置service接口
 *
 * @author 邾茂星
 * @date 2022-01-11 09:49:59
 */
public interface SizeConfigService extends IService<SizeConfig> {

    /**
     * 查询尺码配置
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    PageResult<SizeConfig> page(SizeConfigParam sizeConfigParam);

    /**
     * 尺码配置列表
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    List<SizeConfig> list(SizeConfigParam sizeConfigParam);

    /**
     * 添加尺码配置
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    void add(SizeConfigParam sizeConfigParam);

    /**
     * 删除尺码配置
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    void delete(List<SizeConfigParam> sizeConfigParamList);

    /**
     * 编辑尺码配置
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
    void edit(SizeConfigParam sizeConfigParam);

    /**
     * 查看尺码配置
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
     SizeConfig detail(SizeConfigParam sizeConfigParam);

    /**
     * 导出尺码配置
     *
     * @author 邾茂星
     * @date 2022-01-11 09:49:59
     */
     void export(SizeConfigParam sizeConfigParam);

     /**
      * @Description: 校验尺码组名称是否重名
      * @author 邾茂星
      * @date 2022/1/11 11:22
      * @param cateName
      * @return int
      */
     int checkCateName(String cateName);

     /**
      * @Description: 设为默认
      * @author 邾茂星
      * @date 2022/1/11 11:23
      * @param id
      * @return int
      */
     int saveDefault(Long id);

     /**
      * @Description: 查询尺码信息
      * @author 邾茂星
      * @date 2022/1/13 10:19
      * @param id
      * @return List<String>
      */
     List<String> getSizeInforList(Long id);
}
