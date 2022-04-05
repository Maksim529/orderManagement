
package vip.xiaonuo.main.modular.tbworkorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.tbworkorder.entity.TbWorkerorderReportedDetail;
import vip.xiaonuo.main.modular.tbworkorder.param.TbWorkerorderReportedDetailParam;
import java.util.List;

/**
 * 报工明细service接口
 *
 * @author xwx
 * @date 2022-03-14 14:23:40
 */
public interface TbWorkerorderReportedDetailService extends IService<TbWorkerorderReportedDetail> {

    /**
     * 查询报工明细
     *
     * @author xwx
     * @date 2022-03-14 14:23:40
     */
    PageResult<TbWorkerorderReportedDetail> page(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam);

    /**
     * 报工明细列表
     *
     * @author xwx
     * @date 2022-03-14 14:23:40
     */
    List<TbWorkerorderReportedDetail> list(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam);

    /**
     * 添加报工明细
     *
     * @author xwx
     * @date 2022-03-14 14:23:40
     */
    void add(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam);

    /**
     * 删除报工明细
     *
     * @author xwx
     * @date 2022-03-14 14:23:40
     */
    void delete(List<TbWorkerorderReportedDetailParam> tbWorkerorderReportedDetailParamList);

    /**
     * 编辑报工明细
     *
     * @author xwx
     * @date 2022-03-14 14:23:40
     */
    void edit(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam);

    /**
     * 查看报工明细
     *
     * @author xwx
     * @date 2022-03-14 14:23:40
     */
     TbWorkerorderReportedDetail detail(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam);

    /**
     * 导出报工明细
     *
     * @author xwx
     * @date 2022-03-14 14:23:40
     */
     void export(TbWorkerorderReportedDetailParam tbWorkerorderReportedDetailParam);

}
