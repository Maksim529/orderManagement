
package vip.xiaonuo.main.modular.tbworkorder.service;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.dingding.VO.DingOrderVO;
import vip.xiaonuo.main.modular.tbworkorder.dto.WorkOrderReportedDTO;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkOrder;
import vip.xiaonuo.main.modular.tbworkorder.param.TbWorkOrderParam;
import vip.xiaonuo.main.modular.tbworkorder.vo.TbWorkOrderInfoVO;
import vip.xiaonuo.main.modular.tbworkorder.vo.TbWorkOrderVO;
import vip.xiaonuo.sys.modular.file.entity.SysFileInfo;

import java.util.List;
import java.util.Map;

/**
 * 工厂工单service接口
 *
 * @author wjc
 * @date 2022-01-13 14:29:32
 */
public interface TbWorkOrderService extends IService<TbWorkOrder> {

    /**
     * 查询工厂工单
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    PageResult<TbWorkOrder> page(TbWorkOrderParam tbWorkOrderParam);

    /**
     * 工厂工单列表
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    List<TbWorkOrder> list(TbWorkOrderParam tbWorkOrderParam);

    /**
     * 添加工厂工单
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    void add(TbWorkOrderParam tbWorkOrderParam);

    /**
     * 删除工厂工单
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    void delete(List<TbWorkOrderParam> tbWorkOrderParamList);

    /**
     * 编辑工厂工单
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
    void edit(TbWorkOrderParam tbWorkOrderParam);

    /**
     * 查看工厂工单
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
     TbWorkOrder detail(TbWorkOrderParam tbWorkOrderParam);

    /**
     * 导出工厂工单
     *
     * @author wjc
     * @date 2022-01-13 14:29:32
     */
     void export(TbWorkOrderParam tbWorkOrderParam);

    /**
     * 根据订单ID返回工单VO
     * @author wjc
     * @date 2022-01-13 14:29:32
     * @param orderId
     * @return
     */
    List<TbWorkOrderVO> getWorkOrderDataByOrderId(Long orderId);

    /**
     * 查询当前跟单员下所负责的所有工单的不同状态的数量
     * @author wjc
     * @date 2022-1-18 09:47:43
     * @return
     */
    Map<Integer,Integer> countByUser();

    /**
     * 根据跟单员以及跟单数量查询工单信息分页
     * @return
     */
    PageResult<DingOrderVO> pageByStatus(Integer status);

    /**
     * 工单提交转入生产
     * @author wjc
     * @date 2022-1-19 08:48:05
     * @param workOrderId
     */
    void commit(Long workOrderId);

    /**
     * @Description: 查询工单信息
     * @author 邾茂星
     * @date 2022/1/19 15:25
     * @param tbWorkOrderInfoVO
     * @return TbWorkOrderInfoVO
     */
    PageResult<TbWorkOrderInfoVO> searchOrder(TbWorkOrderInfoVO tbWorkOrderInfoVO);


    /**
     * @Description: 上传工单附件
     * @author wjc
     * @date 2022-1-20 09:02:28
     * @param files
     * @param id
     * @param type
     * @return TbWorkOrderInfoVO
     */
    void uploadFile(MultipartFile[] files, Long id, Integer type);

    /**
     * 通过订单Id获取所有该订单的合同文件信息
     * @param id
     * @return
     */
    List<SysFileInfo> getContractPath(Long id);

    /**
     * 完成生产工单并结单
     * @param workOrderId
     * @return
     */
    void finish(Long workOrderId);

    /**
     * @Description: 发送短信通知
     * @author 邾茂星
     * @date 2022/3/7 10:20
     * @param dict status=7 发货
     */
    void sendSMS(Dict dict);

    /**
     * @Description: 保存-报工
     * @author 邾茂星
     * @date 2022/3/14 14:52
     * @param dto
     * @return Long
     */
    Long saveReported(WorkOrderReportedDTO dto);
}
