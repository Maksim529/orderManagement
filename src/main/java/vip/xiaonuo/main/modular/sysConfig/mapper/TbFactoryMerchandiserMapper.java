
package vip.xiaonuo.main.modular.sysConfig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.main.modular.sysConfig.entity.TbFactoryMerchandiser;

import java.util.List;

/**
 * 工厂跟单员
 *
 * @author xwx
 * @date 2022-03-22 13:27:53
 */
public interface TbFactoryMerchandiserMapper extends BaseMapper<TbFactoryMerchandiser> {

    /**
     * @Description: 根据工厂id，查询跟单员工号
     * @author 邾茂星
     * @date 2022/3/22 15:29
     * @param factoryId
     * @return List<String>
     */
    List<String> getJobNumList(@Param("factoryId") Long factoryId);

}
