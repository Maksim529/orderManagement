
package vip.xiaonuo.main.modular.sizeconfig.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 尺码配置
 *
 * @author 邾茂星
 * @date 2022-01-11 09:49:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_size_config")
public class SizeConfig extends BaseEntity {

    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 尺码组名称
     */
    @Excel(name = "尺码组名称")
    private String cateName;

    /**
     * 尺码信息
     */
    @Excel(name = "尺码信息")
    private String sizeInfor;

    /**
     * 是否默认
     */
    @Excel(name = "是否默认")
    private Integer isDefault;

}
