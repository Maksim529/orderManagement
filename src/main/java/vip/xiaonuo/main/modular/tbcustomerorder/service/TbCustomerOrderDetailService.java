
package vip.xiaonuo.main.modular.tbcustomerorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.tbcustomerorder.entity.TbCustomerOrderDetail;
import vip.xiaonuo.main.modular.tbcustomerorder.param.TbCustomerOrderDetailParam;
import java.util.List;

/**
 * 订单明细service接口
 *
 * @author 邾茂星
 * @date 2022-01-12 10:09:05
 */
public interface TbCustomerOrderDetailService extends IService<TbCustomerOrderDetail> {

    /**
     * 查询订单明细
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    PageResult<TbCustomerOrderDetail> page(TbCustomerOrderDetailParam tbCustomerOrderDetailParam);

    /**
     * 订单明细列表
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    List<TbCustomerOrderDetail> list(TbCustomerOrderDetailParam tbCustomerOrderDetailParam);

    /**
     * 添加订单明细
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    void add(TbCustomerOrderDetailParam tbCustomerOrderDetailParam);

    /**
     * 删除订单明细
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    void delete(List<TbCustomerOrderDetailParam> tbCustomerOrderDetailParamList);

    /**
     * 编辑订单明细
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
    void edit(TbCustomerOrderDetailParam tbCustomerOrderDetailParam);

    /**
     * 查看订单明细
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
     TbCustomerOrderDetail detail(TbCustomerOrderDetailParam tbCustomerOrderDetailParam);

    /**
     * 导出订单明细
     *
     * @author 邾茂星
     * @date 2022-01-12 10:09:05
     */
     void export(TbCustomerOrderDetailParam tbCustomerOrderDetailParam);

    void save(TbCustomerOrderDetailParam tbCustomerOrderDetailParam);

}
