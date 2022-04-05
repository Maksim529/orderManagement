
package vip.xiaonuo.main.modular.tbcustomerorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.tbcustomerorder.dto.TbCustomerOrderDTO;
import vip.xiaonuo.main.modular.tbcustomerorder.dto.TbOrderDistributeDTO;
import vip.xiaonuo.main.modular.tbcustomerorder.entity.TbCustomerOrder;
import vip.xiaonuo.main.modular.tbcustomerorder.entity.TbCustomerOrderDetail;
import vip.xiaonuo.main.modular.tbcustomerorder.param.TbCustomerOrderParam;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.CustomerOrderRollInfoVO;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.TbCustomerOrderContractVO;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.TbCustomerOrderVO;
import vip.xiaonuo.main.modular.tbfactoryorderdetail.vo.WorkOrderDetailVO;

import java.util.List;
import java.util.Map;

/**
 * 生产订单service接口
 *
 * @author 邾茂星
 * @date 2022-01-12 08:59:51
 */
public interface TbCustomerOrderService extends IService<TbCustomerOrder> {

    /**
     * 查询生产订单
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    PageResult<TbCustomerOrder> page(TbCustomerOrderParam tbCustomerOrderParam);

    /**
     * 分页查询订单和工单列表
     *
     * @param tbCustomerOrderParam
     * @return
     * @author wan
     * @date 2022-1-15 13:40:40
     */
    PageResult<TbCustomerOrderVO> findOrderPage(TbCustomerOrderParam tbCustomerOrderParam);

    PageResult<TbCustomerOrderVO> findPage(TbCustomerOrderParam tbCustomerOrderParam);

    /**
     * @param tbCustomerOrderContractVO
     * @return PageResult<TbCustomerOrderContractVO>
     * @Description: 销售合同列表
     * @author 邾茂星
     * @date 2022/1/19 11:08
     */
    PageResult<TbCustomerOrderContractVO> salesContractPage(TbCustomerOrderContractVO tbCustomerOrderContractVO);

    /**
     * 生产订单列表
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    List<TbCustomerOrder> list(TbCustomerOrderParam tbCustomerOrderParam);

    /**
     * 添加生产订单
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    void add(TbCustomerOrderDTO tbCustomerOrderDTO);

    /**
     * 删除生产订单
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    void delete(List<TbCustomerOrderParam> tbCustomerOrderParamList);

    /**
     * 编辑生产订单
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    void edit(TbCustomerOrderDTO tbCustomerOrderDTO);

    /**
     * 查看生产订单
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    TbCustomerOrder detail(TbCustomerOrderParam tbCustomerOrderParam);

    /**
     * 导出生产订单
     *
     * @author 邾茂星
     * @date 2022-01-12 08:59:51
     */
    void export(TbCustomerOrderParam tbCustomerOrderParam);

    /**
     * @param customeerId
     * @return String
     * @Description: 生成订单号
     * @author 邾茂星
     * @date 2022/1/12 9:29
     */
    String createOrderNo(Long customeerId);

    /**
     * 上传文件
     *
     * @param files
     * @param orderNo
     * @param type
     * @return
     */
    int uploadFile(MultipartFile[] files, String orderNo, Integer type);

    Long publish(TbCustomerOrderDTO tbCustomerOrderDTO, MultipartFile file);

    /**
     * 分配订单生成工单
     *
     * @param dto
     */
    void distribute(TbOrderDistributeDTO dto);

    /**
     * 分配订单生成工单,先获取订单详情
     *
     * @param orderId
     * @return
     */
    List<TbCustomerOrderDetail> getDistributeDetail(Long orderId);

    int uploadPublishPhoto(Long id, MultipartFile uploadPhoto);

    /**
     * @param customeerId
     * @param status
     * @return int
     * @Description: 统计数量
     * @author 邾茂星
     * @date 2022/1/14 14:59
     */
    int countNumber(Long customeerId, Integer status);

    /**
     * @param id
     * @return TbCustomerOrderVO
     * @Description: id查询
     * @author 邾茂星
     * @date 2022/1/15 10:44
     */
    TbCustomerOrderVO getDetailById(Long id);

    /**
     * @param id
     * @param status
     * @return int
     * @Description: 保存订单状态
     * @author 邾茂星
     * @date 2022/1/17 10:56
     */
    int saveStatus(Long id, Integer status);

    Map<String, Integer> totalCounts(Long customerId, Long accountId);

    /**
     * @return List<CustomerOrderRollInfoVO>
     * @Description: 订单_滚动信息
     * @author 邾茂星
     * @date 2022/1/17 13:22
     */
    List<CustomerOrderRollInfoVO> findRollInfo(Integer type);

    /**
     * @param orderId
     * @return List<String>
     * @Description: 查看合同附件路径
     * @author 邾茂星
     * @date 2022/1/19 12:06
     */
    List<String> checkContractFilePath(Long orderId, Integer type);

    /**
     * @param orderId
     * @return List<WorkOrderDetailVO>
     * @Description: 查看订单，裁剪信息
     * @author 邾茂星
     * @date 2022/3/15 16:01
     */
    List<WorkOrderDetailVO> checkTailorInfo(Long orderId);

    /**
     * @param orderId
     * @return Map<Object>
     * @Description: 查看订单状态步骤图
     * @author 邾茂星
     * @date 2022/3/16 9:10
     */
    Map<String, Object> checkStatusStep(Long orderId);

    /**
     * @Description: 获取订单附件URL
     * @author 邾茂星
     * @date 2022/3/28 16:21
     * @param orderId
     * @param businessType
     * @param fileType
     * @return List<String>
     */
    List<String> orderFileUrlList(Long orderId, Integer businessType, Integer fileType);
}
