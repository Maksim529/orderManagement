
package vip.xiaonuo.main.modular.tbcustomerask.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.consts.SymbolConstant;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.common.vo.TbCustomerAskVO;
import vip.xiaonuo.main.modular.sysConfig.service.ProductTypeService;
import vip.xiaonuo.main.modular.tbcustomerask.entity.TbCustomerAsk;
import vip.xiaonuo.main.modular.tbcustomerask.param.TbCustomerAskParam;
import vip.xiaonuo.main.modular.tbcustomerask.service.TbCustomerAskService;
import vip.xiaonuo.sys.modular.consts.service.SysConfigService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户询价单控制器
 *
 * @author 邾茂星
 * @date 2022-01-10 15:02:32
 */
@RestController
@Api(tags = "客户询价单")
public class TbCustomerAskController {

    @Resource
    private TbCustomerAskService tbCustomerAskService;


    @PostMapping(value= "/tbCustomerAsk/publish",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long publicAskOrder(@RequestParam("params") String params,
                                       @RequestPart( "stylePhoto") MultipartFile stylePhoto,
                                       @RequestPart( "uploadPhotos") MultipartFile[] uploadPhotos){


        System.out.println(params);
        TbCustomerAskParam customerAskParam = JSONObject.parseObject(params, TbCustomerAskParam.class);

        return tbCustomerAskService.publish(customerAskParam,stylePhoto,uploadPhotos);
    }


    @PostMapping(value = "/tbCustomerAsk/publish/form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long publish(@RequestParam("params") String params, @RequestPart("stylePhoto") MultipartFile stylePhoto){
        TbCustomerAskParam customerAskParam = JSONObject.parseObject(params, TbCustomerAskParam.class);

        return tbCustomerAskService.publish(customerAskParam,stylePhoto,null);
    }

    @PostMapping(value = "/tbCustomerAsk/publish/photo",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public int uploadPublishPhoto(@RequestParam("id") Long id, @RequestPart("uploadPhoto")MultipartFile uploadPhoto){
        int i = tbCustomerAskService.uploadPublishPhoto(id,uploadPhoto);
        return i;
    }

    /**
     *@Author: 邾茂星
     *@Des:获取询价单图片详情
     */
    @GetMapping( "/tbCustomerAsk/picInfos")
    public List<String> picInfos(@RequestParam("id") Long id){
        List<String> res = tbCustomerAskService.picInfos(id);
        return res;
    }



    @RequestMapping(value = "/tbCustomerAsk/queryMyCustomerAsks", method = RequestMethod.GET)
    ResponseData queryMyCustomerAsks(@RequestParam("askStatus")int askStatus, @RequestParam("accountId")Long accountId,
                                     @RequestParam("pageNo")int pageNo, @RequestParam("pageSize")int pageSize){
        return new SuccessResponseData(tbCustomerAskService.queryMyCustomerAsks(askStatus, accountId,pageNo,pageSize));
    }

    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private ProductTypeService productTypeService;

    @RequestMapping(value = "/tbCustomerAsk/getInfo", method = RequestMethod.GET)
    ResponseData getCustomerAskInfo(@RequestParam("id") Long id){
        TbCustomerAsk ask = tbCustomerAskService.getById(id);
        TbCustomerAskVO tbCustomerAskVO = new TbCustomerAskVO();
        BeanUtils.copyProperties(ask,tbCustomerAskVO);
        String ossUrl = sysConfigService.getSysConfigValueByCode("aliyunupload");
        tbCustomerAskVO.setStylePicStr(ossUrl + SymbolConstant.LEFT_DIVIDE + tbCustomerAskVO.getPic());
        //品类名称
        String categoryName = productTypeService.getNamesById(ask.getCategory(),null);
        tbCustomerAskVO.setCategoryName(categoryName);
        return new SuccessResponseData(tbCustomerAskVO);
    }

    @RequestMapping(value = "/tbCustomerAsk/askAgain", method = RequestMethod.POST)
    void askAgain(@RequestParam("id") Long id, @RequestParam(value = "askStatus") int  askStatus){
        TbCustomerAsk tbCustomerAsk =new TbCustomerAsk();
        tbCustomerAsk.setId(id);
        tbCustomerAsk.setAskStatus(askStatus);
        tbCustomerAskService.updateById(tbCustomerAsk);
    }

    /**
     * 查询客户询价单
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */


//    @Permission
    @GetMapping("/tbCustomerAsk/page")
    @BusinessLog(title = "客户询价单_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(TbCustomerAskParam tbCustomerAskParam) {
        return new SuccessResponseData(tbCustomerAskService.page(tbCustomerAskParam));
    }

    /**
     * 添加客户询价单
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
//    @Permission
    @PostMapping("/tbCustomerAsk/add")
    @BusinessLog(title = "客户询价单_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(TbCustomerAskParam.add.class) TbCustomerAskParam tbCustomerAskParam) {
            tbCustomerAskService.add(tbCustomerAskParam);
        return new SuccessResponseData();
    }

    /**
     * 删除客户询价单，可批量删除
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
//    @Permission
    @PostMapping("/tbCustomerAsk/delete")
    @BusinessLog(title = "客户询价单_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(TbCustomerAskParam.delete.class) List<TbCustomerAskParam> tbCustomerAskParamList) {
            tbCustomerAskService.delete(tbCustomerAskParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑客户询价单
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
//    @Permission
    @PostMapping("/tbCustomerAsk/edit")
    @BusinessLog(title = "客户询价单_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(TbCustomerAskParam.edit.class) TbCustomerAskParam tbCustomerAskParam) {
            tbCustomerAskService.edit(tbCustomerAskParam);
        return new SuccessResponseData();
    }

    /**
     * 查看客户询价单
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
//    @Permission
    @GetMapping("/tbCustomerAsk/detail")
    @BusinessLog(title = "客户询价单_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(TbCustomerAskParam.detail.class) TbCustomerAskParam tbCustomerAskParam) {
        return new SuccessResponseData(tbCustomerAskService.detail(tbCustomerAskParam));
    }

    /**
     * 客户询价单列表
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
//    @Permission
    @GetMapping("/tbCustomerAsk/list")
    @BusinessLog(title = "客户询价单_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(TbCustomerAskParam tbCustomerAskParam) {
        return new SuccessResponseData(tbCustomerAskService.list(tbCustomerAskParam));
    }

    /**
     * 导出客户询价单
     *
     * @author 邾茂星
     * @date 2022-01-10 15:02:32
     */
//    @Permission
    @GetMapping("/tbCustomerAsk/export")
    @BusinessLog(title = "客户询价单_导出", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(TbCustomerAskParam tbCustomerAskParam) {
        tbCustomerAskService.export(tbCustomerAskParam);
    }

}
