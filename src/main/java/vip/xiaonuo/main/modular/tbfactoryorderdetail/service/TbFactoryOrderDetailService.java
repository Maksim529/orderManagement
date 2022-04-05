
package vip.xiaonuo.main.modular.tbfactoryorderdetail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.entity.TbFactoryOrderDetail;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.param.TbFactoryOrderDetailParam;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.vo.WorkOrderAmountInfoVO;

import java.util.List;

/**
 * 工单明细service接口
 *
 * @author wjc
 * @date 2022-01-13 13:35:25
 */
public interface TbFactoryOrderDetailService extends IService<TbFactoryOrderDetail> {

    /**
     * 查询工单明细
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    PageResult<TbFactoryOrderDetail> page(TbFactoryOrderDetailParam tbFactoryOrderDetailParam);

    /**
     * 工单明细列表
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    List<TbFactoryOrderDetail> list(TbFactoryOrderDetailParam tbFactoryOrderDetailParam);

    /**
     * 添加工单明细
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    void add(TbFactoryOrderDetailParam tbFactoryOrderDetailParam);

    /**
     * 删除工单明细
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    void delete(List<TbFactoryOrderDetailParam> tbFactoryOrderDetailParamList);

    /**
     * 编辑工单明细
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
    void edit(TbFactoryOrderDetailParam tbFactoryOrderDetailParam);

    /**
     * 查看工单明细
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
     TbFactoryOrderDetail detail(TbFactoryOrderDetailParam tbFactoryOrderDetailParam);

    /**
     * 导出工单明细
     *
     * @author wjc
     * @date 2022-01-13 13:35:25
     */
     void export(TbFactoryOrderDetailParam tbFactoryOrderDetailParam);

     /**
      * @Description: 查询工单数量明细
      * @author 邾茂星
      * @date 2022/3/14 9:39
      * @param workOrderId
      * @return WorkOrderAmountInfoVO
      */
     WorkOrderAmountInfoVO getAmountInfo(Long workOrderId);
}
