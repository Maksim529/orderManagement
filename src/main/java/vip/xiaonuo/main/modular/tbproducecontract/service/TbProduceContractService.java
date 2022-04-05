
package vip.xiaonuo.main.modular.tbproducecontract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.tbproducecontract.entity.TbProduceContract;
import vip.xiaonuo.main.modular.tbproducecontract.param.TbProduceContractParam;
import java.util.List;

/**
 * 加工合同service接口
 *
 * @author wjc
 * @date 2022-01-20 10:11:34
 */
public interface TbProduceContractService extends IService<TbProduceContract> {

    /**
     * 查询加工合同
     *
     * @author wjc
     * @date 2022-01-20 10:11:34
     */
    PageResult<TbProduceContract> page(TbProduceContractParam tbProduceContractParam);

    /**
     * 加工合同列表
     *
     * @author wjc
     * @date 2022-01-20 10:11:34
     */
    List<TbProduceContract> list(TbProduceContractParam tbProduceContractParam);

    /**
     * 添加加工合同
     *
     * @author wjc
     * @date 2022-01-20 10:11:34
     */
    void add(TbProduceContractParam tbProduceContractParam);

    /**
     * 删除加工合同
     *
     * @author wjc
     * @date 2022-01-20 10:11:34
     */
    void delete(List<TbProduceContractParam> tbProduceContractParamList);

    /**
     * 编辑加工合同
     *
     * @author wjc
     * @date 2022-01-20 10:11:34
     */
    void edit(TbProduceContractParam tbProduceContractParam);

    /**
     * 查看加工合同
     *
     * @author wjc
     * @date 2022-01-20 10:11:34
     */
     TbProduceContract detail(TbProduceContractParam tbProduceContractParam);

    /**
     * 导出加工合同
     *
     * @author wjc
     * @date 2022-01-20 10:11:34
     */
     void export(TbProduceContractParam tbProduceContractParam);

}
