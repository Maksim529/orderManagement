
package vip.xiaonuo.main.modular.tbcustomerask.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.taobao.api.ApiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.consts.SymbolConstant;
import vip.xiaonuo.common.exception.ServiceException;
import vip.xiaonuo.common.pojo.page.PageResult;
import vip.xiaonuo.common.vo.TbCustomerAskVO;
import vip.xiaonuo.common.vo.TbPublisePriceVO;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.util.HttpServletUtil;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.main.modular.sysConfig.service.ProductTypeService;
import vip.xiaonuo.main.modular.tbcustomeraccount.entity.TbCustomerAccount;
import vip.xiaonuo.main.modular.tbcustomeraccount.service.TbCustomerAccountService;
import vip.xiaonuo.main.modular.tbcustomerask.entity.TbCustomerAsk;
import vip.xiaonuo.main.modular.tbcustomerask.enums.TbCustomerAskExceptionEnum;
import vip.xiaonuo.main.modular.tbcustomerask.mapper.TbCustomerAskMapper;
import vip.xiaonuo.main.modular.tbcustomerask.param.TbCustomerAskParam;
import vip.xiaonuo.main.modular.tbcustomerask.service.TbCustomerAskService;
import vip.xiaonuo.main.modular.tbcustomerorder.vo.CustomerOrderRollInfoVO;
import vip.xiaonuo.main.modular.tbpubliseprice.entity.TbPublisePrice;
import vip.xiaonuo.main.modular.tbpubliseprice.param.TbPublisePriceParam;
import vip.xiaonuo.main.modular.tbpubliseprice.service.TbPublisePriceService;
import vip.xiaonuo.sys.modular.consts.service.SysConfigService;
import vip.xiaonuo.sys.modular.file.entity.SysFileInfo;
import vip.xiaonuo.sys.modular.file.param.SysFileInfoParam;
import vip.xiaonuo.sys.modular.file.service.SysFileInfoService;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ???????????????service???????????????
 *
 * @author ?????????
 * @date 2022-01-10 15:02:32
 */
@Service
public class TbCustomerAskServiceImpl extends ServiceImpl<TbCustomerAskMapper, TbCustomerAsk> implements TbCustomerAskService {


    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private TbCustomerAccountService tbCustomerAccountServiceImpl;

    @Autowired
    private TbPublisePriceService tbPublisePriceServiceImpl;

    @Autowired
    private SysFileInfoService sysFileInfoServiceImpl;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProductTypeService productTypeService;

    @Value("${dingTalk.sjRobotUrl}")
    private String sjRobotUrl;

    @Value("${dingTalk.pcManageUrl}")
    private String pcManageUrl;

    @Override
    public PageResult<TbCustomerAskVO> page(TbCustomerAskParam tbCustomerAskParam) {
        QueryWrapper<TbCustomerAsk> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(tbCustomerAskParam)) {

            // ?????????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerAskParam.getAskOrderNo())) {
                queryWrapper.like("a.ask_order_no", tbCustomerAskParam.getAskOrderNo());
            }
            // ?????????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerAskParam.getCustomerSku())) {
                queryWrapper.like("a.customer_sku", tbCustomerAskParam.getCustomerSku());
            }
            // ?????????????????? ??????
            if (ObjectUtil.isNotEmpty(tbCustomerAskParam.getCustomerAccountName())) {
                queryWrapper.like("b.owner",tbCustomerAskParam.getCustomerAccountName())
                        .or().like("b.owner_tel",tbCustomerAskParam.getCustomerAccountName());
            }
        }
        queryWrapper.lambda().orderByDesc(TbCustomerAsk::getCreateTime);
        Page<TbCustomerAsk> pageResult = baseMapper.findPage(PageFactory.defaultPage(), queryWrapper);
//        PageResult<TbCustomerAsk> pageResult = new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
        List<TbCustomerAsk> rows = pageResult.getRecords();
        List<TbCustomerAskVO> voList = getTbCustomerAskVOS(rows);
        PageResult<TbCustomerAskVO> result = new PageResult<>();
        BeanUtils.copyProperties(pageResult, result);
        result.setRows(voList);
        return result;
    }

    private List<TbCustomerAskVO> getTbCustomerAskVOS(List<TbCustomerAsk> rows) {
        String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
        List<TbCustomerAskVO> voList = new ArrayList<>();
        List<TbPublisePriceVO> priceVOList = null;

        if (CollectionUtils.isNotEmpty(rows)) {
            TbCustomerAskVO tbCustomerAskVO = null;
            Long customerUserId;
            TbCustomerAccount tbCustomerAccount = null;
            TbPublisePriceVO publisePriceVO = null;
            String tell = "";
            String name = "";
            String customerInfo;
            TbPublisePriceParam tbPublisePriceParam = null;
            String categoryName = "";
            for (TbCustomerAsk customerAsk : rows) {
                //????????????
                tbCustomerAskVO = new TbCustomerAskVO();
                priceVOList = new ArrayList<>();

                BeanUtils.copyProperties(customerAsk, tbCustomerAskVO);
                customerUserId = customerAsk.getAccountId();
                tbCustomerAccount = tbCustomerAccountServiceImpl.getById(customerUserId);
                if (tbCustomerAccount != null) {
                    tell = tbCustomerAccount.getOwnerTel();
                    name = tbCustomerAccount.getOwner();
                }
                //????????????
                customerInfo = "?????????" + (StringUtils.isBlank(name) ? "??????" : name) + ";" + "?????????" + tell;
                tbCustomerAskVO.setCustomerInfo(customerInfo);
                //????????????
                categoryName = productTypeService.getNamesById(customerAsk.getCategory(), null);
                tbCustomerAskVO.setCategoryName(categoryName);
                Long id = tbCustomerAskVO.getId();

                LambdaQueryWrapper<TbPublisePrice> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(TbPublisePrice::getAskOrderId, id);
                List<TbPublisePrice> list = tbPublisePriceServiceImpl.list(lambdaQueryWrapper);

                for (TbPublisePrice publisePrice : list) {
                    publisePriceVO = new TbPublisePriceVO();
                    BeanUtils.copyProperties(publisePrice, publisePriceVO);
                    //?????????
                    Long publicPriceUser = publisePriceVO.getPublicPriceUser();
                    if (publicPriceUser != null) {
                        String userName = sysUserService.getNameByUserId(publisePriceVO.getPublicPriceUser());
                        publisePriceVO.setPublicPriceUserName(userName);
                    }
                    priceVOList.add(publisePriceVO);
                }
                tbCustomerAskVO.setPublisePrices(priceVOList);
                //?????????
                if (StringUtils.isNotEmpty(tbCustomerAskVO.getPic())) {
                    tbCustomerAskVO.setStylePicStr(ossUrl + SymbolConstant.LEFT_DIVIDE + tbCustomerAskVO.getPic());
                }
                voList.add(tbCustomerAskVO);
            }
        }
        return voList;
    }


    @Override
    public List<TbCustomerAsk> list(TbCustomerAskParam tbCustomerAskParam) {
        return this.list();
    }

    @Override
    public void add(TbCustomerAskParam tbCustomerAskParam) {
        TbCustomerAsk tbCustomerAsk = new TbCustomerAsk();
        BeanUtil.copyProperties(tbCustomerAskParam, tbCustomerAsk);
        this.save(tbCustomerAsk);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TbCustomerAskParam> tbCustomerAskParamList) {
        tbCustomerAskParamList.forEach(tbCustomerAskParam -> {
            this.removeById(tbCustomerAskParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TbCustomerAskParam tbCustomerAskParam) {
        TbCustomerAsk tbCustomerAsk = this.queryTbCustomerAsk(tbCustomerAskParam);
        BeanUtil.copyProperties(tbCustomerAskParam, tbCustomerAsk);
        this.updateById(tbCustomerAsk);
    }

    @Override
    public TbCustomerAsk detail(TbCustomerAskParam tbCustomerAskParam) {
        return this.queryTbCustomerAsk(tbCustomerAskParam);
    }

    /**
     * ?????????????????????
     *
     * @author ?????????
     * @date 2022-01-10 15:02:32
     */
    private TbCustomerAsk queryTbCustomerAsk(TbCustomerAskParam tbCustomerAskParam) {
        TbCustomerAsk tbCustomerAsk = this.getById(tbCustomerAskParam.getId());
        if (ObjectUtil.isNull(tbCustomerAsk)) {
            throw new ServiceException(TbCustomerAskExceptionEnum.NOT_EXIST);
        }
        return tbCustomerAsk;
    }

    @Override
    public void export(TbCustomerAskParam tbCustomerAskParam) {
        List<TbCustomerAsk> list = this.list(tbCustomerAskParam);
        PoiUtil.exportExcelWithStream("SnowyTbCustomerAsk.xls", TbCustomerAsk.class, list);
    }


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long publish(TbCustomerAskParam tbCustomerAskParam, MultipartFile stylePhoto, MultipartFile[] uploadPhotos) {

        try {
            if (simpleDateFormat.parse(tbCustomerAskParam.getGivedate())
                    .before(simpleDateFormat.parse(tbCustomerAskParam.getMaterialReachDate()))) {
                return -1000L;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(tbCustomerAskParam.getCustomerSku())) {
            tbCustomerAskParam.setCustomerSku(tbCustomerAskParam.getAskOrderNo());
        }
//        RedisAtomicLong counter = new RedisAtomicLong("XJ", redisTemplate.getConnectionFactory());
//        long orderNum = counter.incrementAndGet();
//        String str = String.format("%03d", orderNum);
//        String order = "XJ" + sdf.format(new Date()) + str;
//        tbCustomerAskParam.setAskOrderNo(order);

        if (null != stylePhoto) {
            // ????????????????????????
            String originalFilename = stylePhoto.getOriginalFilename();
            // ??????????????????
            String fileSuffix = null;
            if (ObjectUtil.isNotEmpty(originalFilename)) {
                fileSuffix = StrUtil.subAfter(originalFilename, SymbolConstant.PERIOD, true);
            }
            // ??????
            Long fileId = sysFileInfoServiceImpl.uploadFile(stylePhoto);
            tbCustomerAskParam.setPic(fileId + SymbolConstant.PERIOD + fileSuffix);
        }

        TbCustomerAsk tbCustomerAsk = new TbCustomerAsk();
        BeanUtil.copyProperties(tbCustomerAskParam, tbCustomerAsk);
        //?????????
        tbCustomerAsk.setAskStatus(1000);
        this.save(tbCustomerAsk);
        Long id = tbCustomerAsk.getId();
        try {
            if (null != uploadPhotos && uploadPhotos.length >= 1) {
                for (MultipartFile multipartFile : uploadPhotos) {
                    SysFileInfo sysFileInfo = new SysFileInfo();
                    sysFileInfo.setBusinessDataId(id);
                    sysFileInfo.setBusinessType(1);
                    // ??????
                    sysFileInfoServiceImpl.uploadFile(multipartFile, sysFileInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //??????????????????-????????????
        ThreadUtil.execute(() -> sendMessageAsk(id));
        return tbCustomerAsk.getId();
    }

    @Override
    public PageResult<TbCustomerAskVO> queryMyCustomerAsks(int askStatus, Long accountId, int pageNo, int pageSize) {
        //????????????id
        Long customerId = tbCustomerAccountServiceImpl.getCustomerIdByAccountId(accountId);
        QueryWrapper<TbCustomerAsk> qw = new QueryWrapper<>();
        if (askStatus != 0) {
            qw.eq("a.ask_status", askStatus);
        }
        if(customerId != null && customerId != 0){
            qw.eq("b.customer_id", customerId);
        }else {
            qw.eq("a.account_id", accountId);
        }
        HttpServletRequest request = HttpServletUtil.getRequest();
        //????????????
        request.setAttribute("pageNo", pageNo);
        request.setAttribute("pageSize", pageSize);
        Page<TbCustomerAsk> pageResult = baseMapper.findPage(PageFactory.defaultPage(), qw);
        List<TbCustomerAsk> rows = pageResult.getRecords();
        List<TbCustomerAskVO> tbCustomerAskVOS = getTbCustomerAskVOS(rows);
        PageResult<TbCustomerAskVO> result = new PageResult<>();
        BeanUtils.copyProperties(pageResult, result);
        result.setRows(tbCustomerAskVOS);
        return result;
    }

    @Override
    public int uploadPublishPhoto(Long id, MultipartFile uploadPhoto) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setBusinessDataId(id);
        sysFileInfo.setBusinessType(1);
        // ??????
        sysFileInfoServiceImpl.uploadFile(uploadPhoto, sysFileInfo);
        return 1;
    }

    /**
     * @return List<CustomerOrderRollInfoVO>
     * @Description: ?????????????????????
     * @author ?????????
     * @date 2022/1/18 9:29
     */
    @Override
    public List<CustomerOrderRollInfoVO> findRollInfo() {
        QueryWrapper<TbCustomerAsk> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("a.id");
        //????????????????????????4???
        queryWrapper.last(" limit 4");
        return baseMapper.findRollInfo(queryWrapper);
    }

    @Override
    public List<String> picInfos(Long id) {
        //???????????????
        String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
        SysFileInfoParam sysFileInfoParam = new SysFileInfoParam();
        sysFileInfoParam.setBusinessDataId(id);
        sysFileInfoParam.setBusinessType(1);
        List<String> filePaths = sysFileInfoServiceImpl.findFilePath(sysFileInfoParam);
        List<String> newFilePaths = new ArrayList<>();
        if (CollUtil.isNotEmpty(filePaths)) {
            for (String filePath : filePaths) {
                newFilePaths.add(ossUrl + SymbolConstant.LEFT_DIVIDE + filePath);
            }
        }
        return newFilePaths;
    }

    /**
     * @Description: ??????????????????-??????
     * @author ?????????
     * @date 2022/3/4 9:53
     */
    public void sendMessageAsk(Long id) {
        TbCustomerAsk tbCustomerAsk = this.getById(id);
        String askOrderNo = tbCustomerAsk.getAskOrderNo();
        String pic = tbCustomerAsk.getPic();
        Long category = tbCustomerAsk.getCategory();
        String names = productTypeService.getNamesById(category, null);
        String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
        String picUrl = ossUrl + SymbolConstant.LEFT_DIVIDE + pic;
        DingTalkClient client = new DefaultDingTalkClient(sjRobotUrl);
        try {
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("markdown");
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle("???????????????");
            markdown.setText("#### ????????????" + askOrderNo + " \n\n" +
                    "> " + names + "\n\n" +
                    "> ![screenshot](" + picUrl + ")\n\n" +
                    "> #### [PC??? ????????????](" + pcManageUrl + ") \n");
            request.setMarkdown(markdown);
            client.execute(request);
            ThreadUtil.sleep(1, TimeUnit.SECONDS);
            //??????????????????
            String askUserids = sysConfigService.getSysConfigValueByCode("dingTalk_ask_userids");
            if(StrUtil.isNotBlank(askUserids)){
                request.setMsgtype("text");
                OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
                text.setContent("??????????????????????????????????????????????????????????????????");
                request.setText(text);
                OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                at.setAtUserIds(Arrays.asList(askUserids.split(";")));
                request.setAt(at);
                client.execute(request);
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error(StrUtil.format("??????????????????????????????????????????????????????{}", askOrderNo));
        }
    }

    @Override
    public int countNumber(QueryWrapper queryWrapper) {
        return baseMapper.countNumber(queryWrapper);
    }
}
