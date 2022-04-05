
package vip.xiaonuo.main.modular.tbworkerorderexception.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.tbworkerorderexception.entity.TbWorkerOrderException;
import vip.xiaonuo.main.modular.tbworkerorderexception.param.TbWorkerOrderExceptionParam;

import java.util.HashMap;
import java.util.List;

/**
 * 生产异常反馈service接口
 *
 * @author 邾茂星
 * @date 2022-01-20 09:23:33
 */
public interface TbWorkerOrderExceptionService extends IService<TbWorkerOrderException> {

    /**
     * 查询生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    PageResult<TbWorkerOrderException> page(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam);

    /**
     * 生产异常反馈列表
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    List<TbWorkerOrderException> list(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam);

    /**
     * 添加生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    Long add(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam);

    /**
     * 删除生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    void delete(List<TbWorkerOrderExceptionParam> tbWorkerOrderExceptionParamList);

    /**
     * 编辑生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    void edit(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam);

    /**
     * 查看生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    TbWorkerOrderException detail(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam);

    /**
     * 导出生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    void export(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam);

    /**
     * @param tbWorkerOrderExceptionParam
     * @return int
     * @Description: 处理异常
     * @author 邾茂星
     * @date 2022/1/20 11:23
     */
    int handlingException(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam);

    /**
     * @param userId
     * @Description: 生产异常反馈_生产异常类型数量查询
     * @author 邾茂星
     * @date 2022-2-14 12:59:24
     */
    HashMap<String, Object> getCount(Long userId);

    /**
     * @param workOrderId 工单id
     * @param type        类型：0=未处理 1=已处理 2=全部
     * @return int
     * @Description: 查询异常数量
     * @author 邾茂星
     * @date 2022/3/8 15:49
     */
    int getExceptionNumber(Long workOrderId, Integer type);
}
