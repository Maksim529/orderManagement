
package vip.xiaonuo.main.modular.sysConfig.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vip.xiaonuo.common.pojo.base.entity.BaseEntity;

import java.util.List;

/**
 * 工厂信息
 *
 * @author 邾茂星
 * @date 2022-01-10 16:37:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_factory")
public class Factory extends BaseEntity {

    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 工厂名
     */
    @Excel(name = "工厂名")
    private String name;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String owner;

    /**
     * 联系人电话
     */
    @Excel(name = "联系人电话")
    private String ownerTel;

    /**
     * 公司代码
     */
    @Excel(name = "公司代码")
    private String code;

    /**
     * 跟单员id
     */
    @Excel(name = "跟单员id")
    private Long orderUserId;

    /**
     * 跟单员名称
     */
    @Excel(name = "跟单员名称")
    private String orderUserName;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String address;

    /**
     * 状态（1、合作中 2、停止合作 3、潜在客户）
     */
    @Excel(name = "状态（1、合作中 2、停止合作 3、潜在客户）")
    private Integer status;

    @ApiModelProperty("跟单员信息list")
    @TableField(exist = false)
    private List<TbFactoryMerchandiser> merchandiserList;

    @ApiModelProperty("跟单员idList")
    @TableField(exist = false)
    private List<Long> userIdList;

}
