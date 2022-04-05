
package vip.xiaonuo.main.modular.sysConfig.entity;

import com.baomidou.mybatisplus.annotation.*;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;
import vip.xiaonuo.common.pojo.base.node.BaseTreeNode;

/**
 * 品类
 *
 * @author 邾茂星
 * @date 2022-01-10 13:13:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_product_type")
public class ProductType extends BaseEntity implements BaseTreeNode {

    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 类名
     */
    @Excel(name = "类名")
    private String typeName;

    /**
     * 父id
     */
    @Excel(name = "父id")
    private Long pid;

    /**
     * 品类尺码配置
     */
    @Excel(name = "品类尺码配置")
    private Long categorySize;

    @Excel(name = "深度")
    private Integer depth;

    /**
     * 子节点（表中不存在，用于构造树）
     */
    @TableField(exist = false)
    private List children;

    @Override
    public void setChildren(List children) {
        this.children = children;
    }

    /**
     * @Description: 尺码组名称
     * @author 邾茂星
     * @date 2022/1/12 15:04
     */
    @TableField(exist = false)
    private String cateName;
}
