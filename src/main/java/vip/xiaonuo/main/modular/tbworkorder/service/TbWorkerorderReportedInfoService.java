
package vip.xiaonuo.main.modular.tbworkorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkerorderReportedInfo;
import vip.xiaonuo.main.modular.tbworkorder.param.TbWorkerorderReportedInfoParam;
import vip.xiaonuo.main.modular.tbworkorder.vo.WorkOrderReportedInfoVO;

import java.util.List;

/**
 * 报工记录service接口
 *
 * @author xwx
 * @date 2022-03-14 14:10:21
 */
public interface TbWorkerorderReportedInfoService extends IService<TbWorkerorderReportedInfo> {

    /**
     * 查询报工记录
     *
     * @author xwx
     * @date 2022-03-14 14:10:21
     */
    PageResult<TbWorkerorderReportedInfo> page(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam);

    /**
     * 报工记录列表
     *
     * @author xwx
     * @date 2022-03-14 14:10:21
     */
    List<TbWorkerorderReportedInfo> list(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam);

    /**
     * 添加报工记录
     *
     * @author xwx
     * @date 2022-03-14 14:10:21
     */
    void add(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam);

    /**
     * 删除报工记录
     *
     * @author xwx
     * @date 2022-03-14 14:10:21
     */
    void delete(List<TbWorkerorderReportedInfoParam> tbWorkerorderReportedInfoParamList);

    /**
     * 编辑报工记录
     *
     * @author xwx
     * @date 2022-03-14 14:10:21
     */
    void edit(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam);

    /**
     * 查看报工记录
     *
     * @author xwx
     * @date 2022-03-14 14:10:21
     */
    TbWorkerorderReportedInfo detail(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam);

    /**
     * 导出报工记录
     *
     * @author xwx
     * @date 2022-03-14 14:10:21
     */
    void export(TbWorkerorderReportedInfoParam tbWorkerorderReportedInfoParam);

    /**
     * @param workOrderId
     * @return List<WorkOrderReportedInfoVO>
     * @Description: 报工记录
     * @author 邾茂星
     * @date 2022/3/21 16:58
     */
    List<WorkOrderReportedInfoVO> getReportedInfoList(Long workOrderId, Integer type);
}
