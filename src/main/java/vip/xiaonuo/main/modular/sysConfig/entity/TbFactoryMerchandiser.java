
package vip.xiaonuo.main.modular.sysConfig.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 工厂跟单员
 *
 * @author xwx
 * @date 2022-03-22 13:27:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_factory_merchandiser")
public class TbFactoryMerchandiser extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 工厂id
     */
    @Excel(name = "工厂id")
    private Long factoryId;

    /**
     * 跟单员id
     */
    @Excel(name = "跟单员id")
    private Long userId;

    /**
     * 跟单员名称
     */
    @Excel(name = "跟单员名称")
    private String userName;

    /**
     * 状态：0=正常；1=停用；2=删除
     */
    @Excel(name = "状态：0=正常；1=停用；2=删除")
    private Integer status;

}
