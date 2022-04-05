
package vip.xiaonuo.main.modular.tbcustomerask.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.common.vo.TbCustomerAskVO;
import vip.xiaonuo.main.modular.tbcustomerask.entity.TbCustomerAsk;
import vip.xiaonuo.main.modular.tbcustomerask.param.TbCustomerAskParam;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.CustomerOrderRollInfoVO;

import java.util.List;

/**
 * 客户询价单service接口
 *
 * @author 邾茂星
 * @date 2022-01-10 15:02:32
 */
public interface TbCustomerAskService extends IService<TbCustomerAsk> {

    /**
     * 查询客户询价单
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
    PageResult<TbCustomerAskVO> page(TbCustomerAskParam tbCustomerAskParam);

    /**
     * 客户询价单列表
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
    List<TbCustomerAsk> list(TbCustomerAskParam tbCustomerAskParam);

    /**
     * 添加客户询价单
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
    void add(TbCustomerAskParam tbCustomerAskParam);

    /**
     * 删除客户询价单
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
    void delete(List<TbCustomerAskParam> tbCustomerAskParamList);

    /**
     * 编辑客户询价单
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
    void edit(TbCustomerAskParam tbCustomerAskParam);

    /**
     * 查看客户询价单
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
     TbCustomerAsk detail(TbCustomerAskParam tbCustomerAskParam);

    /**
     * 导出客户询价单
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
     void export(TbCustomerAskParam tbCustomerAskParam);

    Long publish(TbCustomerAskParam tbCustomerAskParam, MultipartFile stylePhoto, MultipartFile[] uploadPhotos);

    PageResult<TbCustomerAskVO> queryMyCustomerAsks(int askStatus, Long accountId,int pageNo,int pageSize);

    int uploadPublishPhoto(Long id, MultipartFile uploadPhoto);

    /**
     * @Description: 询价单滚动展示
     * @author 邾茂星
     * @date 2022/1/18 9:29
     * @return List<CustomerOrderRollInfoVO>
     */
    List<CustomerOrderRollInfoVO> findRollInfo();

    List<String> picInfos(Long id);

    /**
     * @Description: 统计数量
     * @author 邾茂星
     * @date 2022/3/24 14:05
     * @return int
     */
    int countNumber(QueryWrapper queryWrapper);
}
