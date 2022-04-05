
package vip.xiaonuo.main.modular.tbpubliseprice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.main.modular.tbpubliseprice.entity.TbPublisePrice;
import vip.xiaonuo.main.modular.tbpubliseprice.param.TbPublisePriceParam;
import java.util.List;

/**
 * 报价记录service接口
 *
 * @author 邾茂星
 * @date 2022-01-11 10:11:12
 */
public interface TbPublisePriceService extends IService<TbPublisePrice> {

    /**
     * 查询报价记录
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    PageResult<TbPublisePrice> page(TbPublisePriceParam tbPublisePriceParam);

    /**
     * 报价记录列表
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    List<TbPublisePrice> list(TbPublisePriceParam tbPublisePriceParam);

    /**
     * 添加报价记录
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    void add(TbPublisePriceParam tbPublisePriceParam);

    /**
     * 删除报价记录
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    void delete(List<TbPublisePriceParam> tbPublisePriceParamList);

    /**
     * 编辑报价记录
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
    void edit(TbPublisePriceParam tbPublisePriceParam);

    /**
     * 查看报价记录
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
     TbPublisePrice detail(TbPublisePriceParam tbPublisePriceParam);

    /**
     * 导出报价记录
     *
     * @author 邾茂星
     * @date 2022-01-11 10:11:12
     */
     void export(TbPublisePriceParam tbPublisePriceParam);

}
