
package vip.xiaonuo.main.modular.tbworkerorderexception.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.api.auth.entity.SysUser;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.sysConfig.service.ProductTypeService;
import vip.xiaonuo.main.modular.sysConfig.service.TbFactoryMerchandiserService;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.TbCustomerOrderVO;
import vip.xiaonuo.main.modular.tbworkerorderexception.entity.TbWorkerOrderException;
import vip.xiaonuo.main.modular.tbworkerorderexception.enums.TbWorkerOrderExceptionExceptionEnum;
import vip.xiaonuo.main.modular.tbworkerorderexception.mapper.TbWorkerOrderExceptionMapper;
import vip.xiaonuo.main.modular.tbworkerorderexception.param.TbWorkerOrderExceptionParam;
import vip.xiaonuo.main.modular.tbworkerorderexception.service.TbWorkerOrderExceptionService;
import vip.xiaonuo.main.modular.user.service.SysUserService;
import vip.xiaonuo.sys.modular.file.entity.SysFileInfo;
import vip.xiaonuo.sys.modular.file.service.SysFileInfoService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 生产异常反馈service接口实现类
 *
 * @author 邾茂星
 * @date 2022-01-20 09:23:33
 */
@Service
public class TbWorkerOrderExceptionServiceImpl extends ServiceImpl<TbWorkerOrderExceptionMapper, TbWorkerOrderException> implements TbWorkerOrderExceptionService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysFileInfoService sysFileInfoService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private TbFactoryMerchandiserService tbFactoryMerchandiserService;

    @Override
    public PageResult<TbWorkerOrderException> page(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        QueryWrapper<TbWorkerOrderException> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbWorkerOrderExceptionParam)) {
            //根据当前操作人id，查询所跟单的工厂ids
            Long operatorId = tbWorkerOrderExceptionParam.getOperatorId();
            if(ObjectUtil.isNotEmpty(operatorId)){
                //查询所跟单的工厂ids
                List<Long> factoryIdList = tbFactoryMerchandiserService.getFactoryIdListByUserId(operatorId);
                if(ObjectUtil.isNotEmpty(factoryIdList)){
                    queryWrapper.in("b.factoryr_id", factoryIdList);
                }else {
                    queryWrapper.eq("a.functionary_id", operatorId);
                }
            }
            // 根据异常编号 查询
            if (ObjectUtil.isNotEmpty(tbWorkerOrderExceptionParam.getCode())) {
                queryWrapper.eq("a.code", tbWorkerOrderExceptionParam.getCode());
            }
            // 根据工单id 查询
            if (ObjectUtil.isNotEmpty(tbWorkerOrderExceptionParam.getWorkOrderId())) {
                queryWrapper.eq("a.work_order_id", tbWorkerOrderExceptionParam.getWorkOrderId());
            }
            // 根据异常分类 查询
            if (ObjectUtil.isNotEmpty(tbWorkerOrderExceptionParam.getExceptionType())) {
                queryWrapper.eq("d.id", tbWorkerOrderExceptionParam.getExceptionType());
            }
            // 根据负责人 查询
            if (ObjectUtil.isNotEmpty(tbWorkerOrderExceptionParam.getFunctionaryId())) {
                queryWrapper.eq("a.functionary_id", tbWorkerOrderExceptionParam.getFunctionaryId());
            }
            // 根据处理人 查询
            if (ObjectUtil.isNotEmpty(tbWorkerOrderExceptionParam.getProcessedId())) {
                queryWrapper.eq("a.processed_id", tbWorkerOrderExceptionParam.getProcessedId());
            }
            // 根据处理人 查询
            if (ObjectUtil.isNotEmpty(tbWorkerOrderExceptionParam.getStatus())) {
                queryWrapper.eq("a.status", tbWorkerOrderExceptionParam.getStatus());
            }
            // 搜索值
            if (ObjectUtil.isNotEmpty(tbWorkerOrderExceptionParam.getSearchValue())){
                String searchValue = tbWorkerOrderExceptionParam.getSearchValue();
                queryWrapper.and(qw -> qw.like("b.work_order_no",searchValue)
                        .or().like("a.code",searchValue)
                        .or().like("c.order_no",searchValue)
                        .or().like("a.functionary_name",searchValue)
                        .or().like("a.processed_name",searchValue)
                );
            }
        }
        queryWrapper.orderByDesc("a.id");
        Integer pageNo = tbWorkerOrderExceptionParam.getPageNo();
        Integer pageSize = tbWorkerOrderExceptionParam.getPageSize();
        Page<Object> pg = new Page<>();
        if(pageNo != null){
            pg = new Page<>(pageNo, pageSize);
        }else {
            pg = PageFactory.defaultPage();
        }
        Page<TbWorkerOrderException> page = baseMapper.findPage(pg, queryWrapper);
        List<TbWorkerOrderException> records = page.getRecords();
        if(CollUtil.isNotEmpty(records)){
            records.forEach(tbWorkerOrderException ->{
                TbCustomerOrderVO tbCustomerOrderVO = tbWorkerOrderException.getTbCustomerOrderVO();
                if(tbCustomerOrderVO != null){
                    Long category = tbCustomerOrderVO.getCategory();
                    String names = productTypeService.getNamesById(category, null);
                    tbCustomerOrderVO.setStrCategory(names);
                }
                List<SysFileInfo> sysFileInfos = exceptionFileList(tbWorkerOrderException.getId());
                tbWorkerOrderException.setSysFileInfoList(sysFileInfos);
            });
        }
        return new PageResult<>(page);
    }

    @Override
    public List<TbWorkerOrderException> list(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        return this.list();
    }

    @Override
    public Long add(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        TbWorkerOrderException tbWorkerOrderException = new TbWorkerOrderException();
        BeanUtil.copyProperties(tbWorkerOrderExceptionParam, tbWorkerOrderException);
        //生成异常单号
        String code = "YC" + DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        tbWorkerOrderException.setCode(code);
        //负责人
        Long functionaryId = tbWorkerOrderException.getFunctionaryId();
        if(functionaryId != null){
            SysUser user = sysUserService.getById(functionaryId);
            if(user != null){
                tbWorkerOrderException.setFunctionaryName(user.getName());
            }
        }
        tbWorkerOrderException.setStatus(0);
        this.save(tbWorkerOrderException);
        return tbWorkerOrderException.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbWorkerOrderExceptionParam> tbWorkerOrderExceptionParamList) {
        tbWorkerOrderExceptionParamList.forEach(tbWorkerOrderExceptionParam -> {
            this.removeById(tbWorkerOrderExceptionParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        TbWorkerOrderException tbWorkerOrderException = this.queryTbWorkerOrderException(tbWorkerOrderExceptionParam);
        BeanUtil.copyProperties(tbWorkerOrderExceptionParam, tbWorkerOrderException);
        this.updateById(tbWorkerOrderException);
    }

    @Override
    public TbWorkerOrderException detail(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        TbWorkerOrderException tbWorkerOrderException = new TbWorkerOrderException();
        Long id = tbWorkerOrderExceptionParam.getId();
        QueryWrapper<TbWorkerOrderException> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.id",id);
        Page<TbWorkerOrderException> page = baseMapper.findPage(PageFactory.defaultPage(), queryWrapper);
        List<TbWorkerOrderException> records = page.getRecords();
        if(CollUtil.isNotEmpty(records)){
            tbWorkerOrderException = records.get(0);
            //查询异常附件
            List<SysFileInfo> list = exceptionFileList(tbWorkerOrderException.getId());
            tbWorkerOrderException.setSysFileInfoList(list);
        }
        return tbWorkerOrderException;
    }

    /**
     * 获取生产异常反馈
     *
     * @author 邾茂星
     * @date 2022-01-20 09:23:33
     */
    private TbWorkerOrderException queryTbWorkerOrderException(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        TbWorkerOrderException tbWorkerOrderException = this.getById(tbWorkerOrderExceptionParam.getId());
        if (ObjectUtil.isNull(tbWorkerOrderException)) {
            throw new ServiceException(TbWorkerOrderExceptionExceptionEnum.NOT_EXIST);
        }
        //查询异常附件
        List<SysFileInfo> list = exceptionFileList(tbWorkerOrderException.getId());
        tbWorkerOrderException.setSysFileInfoList(list);
        return tbWorkerOrderException;
    }

    /**
     * @Description: 查询附件信息
     * @author 邾茂星
     * @date 2022/1/20 14:11
     * @param id
     * @return List<SysFileInfo>
     */
    private List<SysFileInfo> exceptionFileList(Long id){
        List<SysFileInfo> list = new ArrayList<>();
        if(id != null){
            //查询异常附件
            QueryWrapper<SysFileInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysFileInfo::getBusinessDataId, id)
                    .eq(SysFileInfo::getBusinessType, 5);
            list = sysFileInfoService.list(queryWrapper);
            if(CollUtil.isNotEmpty(list)){
                list.forEach(file ->{
                    String filePath = file.getFilePath();
                    filePath = sysFileInfoService.joinOSSUrl(filePath);
                    file.setFilePath(filePath);
                });
            }
        }
        return list;
    }

    @Override
    public void export(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        List<TbWorkerOrderException> list = this.list(tbWorkerOrderExceptionParam);
        PoiUtil.exportExcelWithStream("SnowyTbWorkerOrderException.xls", TbWorkerOrderException.class, list);
    }

    /**
     * @Description: 处理异常
     * @author 邾茂星
     * @date 2022/1/20 11:23
     * @param tbWorkerOrderExceptionParam
     * @return int
     */
    @Override
    public int handlingException(TbWorkerOrderExceptionParam tbWorkerOrderExceptionParam) {
        TbWorkerOrderException tbWorkerOrderException = new TbWorkerOrderException();
        BeanUtil.copyProperties(tbWorkerOrderExceptionParam, tbWorkerOrderException);
        Long processedId = tbWorkerOrderExceptionParam.getProcessedId();
        if(processedId != null){
            SysUser user = sysUserService.getById(processedId);
            if(user != null){
                tbWorkerOrderException.setProcessedName(user.getName());
            }
        }
        tbWorkerOrderException.setProcessedTime(new Date());
        tbWorkerOrderException.setStatus(1);
        if(this.updateById(tbWorkerOrderException)){
            return 1;
        }
        return 0;
    }

    /**
     * @param userId
     * @Description: 生产异常反馈_生产异常类型数量查询
     * @author 邾茂星
     * @date 2022-2-14 12:59:24
     */
    @Override
    public HashMap<String, Object> getCount(Long userId) {
        HashMap<String, Object> result = new HashMap();
        //查询跟单工厂ids
        List<Long> factoryIdList = tbFactoryMerchandiserService.getFactoryIdListByUserId(userId);
        //未处理
        QueryWrapper<TbWorkerOrderException> progressQuery = new QueryWrapper<>();
        progressQuery.eq("a.status", 0);
        if(ObjectUtil.isNotEmpty(factoryIdList)){
            progressQuery.in("b.factoryr_id", factoryIdList);
        }else {
            progressQuery.eq("a.functionary_id", userId);
        }
        int progressCount = baseMapper.countNumber(progressQuery);
        result.put("0",progressCount);
        //已处理
        QueryWrapper<TbWorkerOrderException> finishQuery = new QueryWrapper<>();
        finishQuery.eq("a.status", 1);
        if(ObjectUtil.isNotEmpty(factoryIdList)){
            finishQuery.in("b.factoryr_id", factoryIdList);
        }else {
            finishQuery.eq("a.functionary_id", userId);
        }
        int finishCount = baseMapper.countNumber(finishQuery);
        result.put("1",finishCount);
        return result;
    }

    /**
     * @Description: 查询异常个数
     * @author 邾茂星
     * @date 2022/3/8 15:50
     * @param workOrderId
     * @param type 类型：0=未处理 1=已处理 2=全部
     * @return int
     */
    @Override
    public int getExceptionNumber(Long workOrderId, Integer type) {
        LambdaQueryWrapper<TbWorkerOrderException> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(workOrderId)){
            queryWrapper.eq(TbWorkerOrderException::getWorkOrderId, workOrderId);
        }
        if(ObjectUtil.isNotEmpty(type) && type != 2){
            queryWrapper.eq(TbWorkerOrderException::getStatus, type);
        }
        int count = this.count(queryWrapper);
        return count;
    }
}
