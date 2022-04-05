
package vip.xiaonuo.main.modular.sysConfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.sysConfig.entity.TbFactoryMerchandiser;
import vip.xiaonuo.main.modular.sysConfig.param.TbFactoryMerchandiserParam;

import java.util.List;

/**
 * 工厂跟单员service接口
 *
 * @author xwx
 * @date 2022-03-22 13:27:53
 */
public interface TbFactoryMerchandiserService extends IService<TbFactoryMerchandiser> {

    /**
     * 查询工厂跟单员
     *
     * @author xwx
     * @date 2022-03-22 13:27:53
     */
    PageResult<TbFactoryMerchandiser> page(TbFactoryMerchandiserParam tbFactoryMerchandiserParam);

    /**
     * 工厂跟单员列表
     *
     * @author xwx
     * @date 2022-03-22 13:27:53
     */
    List<TbFactoryMerchandiser> list(TbFactoryMerchandiserParam tbFactoryMerchandiserParam);

    /**
     * 添加工厂跟单员
     *
     * @author xwx
     * @date 2022-03-22 13:27:53
     */
    void add(TbFactoryMerchandiserParam tbFactoryMerchandiserParam);

    /**
     * 删除工厂跟单员
     *
     * @author xwx
     * @date 2022-03-22 13:27:53
     */
    void delete(List<TbFactoryMerchandiserParam> tbFactoryMerchandiserParamList);

    /**
     * 编辑工厂跟单员
     *
     * @author xwx
     * @date 2022-03-22 13:27:53
     */
    void edit(TbFactoryMerchandiserParam tbFactoryMerchandiserParam);

    /**
     * 查看工厂跟单员
     *
     * @author xwx
     * @date 2022-03-22 13:27:53
     */
     TbFactoryMerchandiser detail(TbFactoryMerchandiserParam tbFactoryMerchandiserParam);

    /**
     * 导出工厂跟单员
     *
     * @author xwx
     * @date 2022-03-22 13:27:53
     */
     void export(TbFactoryMerchandiserParam tbFactoryMerchandiserParam);

     /**
      * @Description: 根据工厂id，查询跟单员工号
      * @author 邾茂星
      * @date 2022/3/22 15:22
      * @param factoryId
      * @return List<String>
      */
     List<String> getJobNumList(Long factoryId);

     /**
      * @Description: 根据userID查询所跟单的工厂id
      * @author 邾茂星
      * @date 2022/3/23 9:18
      * @param userId
      * @return List<Long>
      */
     List<Long> getFactoryIdListByUserId(Long userId);

}
