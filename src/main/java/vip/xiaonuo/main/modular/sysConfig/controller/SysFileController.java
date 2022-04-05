package vip.xiaonuo.main.modular.sysConfig.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ftp.Ftp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.annotion.BusinessLog;
import vip.xiaonuo.common.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.common.pojo.response.ErrorResponseData;
import vip.xiaonuo.common.pojo.response.ResponseData;
import vip.xiaonuo.common.pojo.response.SuccessResponseData;
import vip.xiaonuo.main.config.FtpSupportConfig;
import vip.xiaonuo.main.modular.sysConfig.service.SysFileService;
import vip.xiaonuo.sys.modular.file.entity.SysFileInfo;
import vip.xiaonuo.sys.modular.file.param.SysFileInfoParam;
import vip.xiaonuo.sys.modular.file.service.SysFileInfoService;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;

@Api(tags = "文件管理")
@RestController
@RequestMapping("/mobile/sysFile")
public class SysFileController {
    @Autowired
    private FtpSupportConfig ftpSupportConfig;
    @Autowired
    private SysFileInfoService sysFileInfoService;
    @Autowired
    private SysFileService sysFileService;

    @GetMapping("/downloadByPath")
    @ApiOperation("根据文件路径下载")
    @ApiImplicitParam(name = "path", value = "文件路径", required = true, dataType = "string")
    @BusinessLog(title = "文件下载", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downloadByPath(String path, HttpServletResponse response) throws Exception {
        if (StrUtil.isNotBlank(path)) {
            Ftp ftp = new Ftp(ftpSupportConfig.getFtpUrl(), ftpSupportConfig.getFtpPort(),
                    ftpSupportConfig.getFtpAcc(), ftpSupportConfig.getFtpPwd());
            path = path.replace("\\","/");
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            path = path.split(fileName)[0];
            //将文件名称进行编码
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/octet-stream");
            OutputStream outputStream = response.getOutputStream();
//        ftp.download("/wmsVideo/test/askOrder/photos/XJ20220113037/tmp_a275b86aa44efca4bf9a2badd506808aedb0efce73760931.jpg", FileUtil.file("d:/test1.jpg"));
            ftp.download(path, fileName, outputStream);
            ftp.close();
            outputStream.close();
        }
    }

    @GetMapping("/downloadById")
    @ApiOperation("根据文件id下载")
    @ApiImplicitParam(name = "id", value = "文件id", required = true)
    @BusinessLog(title = "文件下载", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downloadById(Long id, HttpServletResponse response) throws Exception {
        if (id != null) {
            SysFileInfo sysFileInfo = sysFileInfoService.getById(id);
            if (sysFileInfo != null) {
                String path = sysFileInfo.getFilePath();
                String fileObjectName = sysFileInfo.getFileObjectName();
                Ftp ftp = new Ftp(ftpSupportConfig.getFtpUrl(), ftpSupportConfig.getFtpPort(),
                        ftpSupportConfig.getFtpAcc(), ftpSupportConfig.getFtpPwd());
                path = path.replace("\\","/");
                String fileName = path.substring(path.lastIndexOf("/") + 1);
                path = path.split(fileName)[0];
                //将文件名称进行编码
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileObjectName, "UTF-8"));
                response.setContentType("application/octet-stream");
                OutputStream outputStream = response.getOutputStream();
                ftp.download(path, fileName, outputStream);
                ftp.close();
                outputStream.close();
            }
        }
    }

    @GetMapping("/getById")
    @ApiOperation("查询文件信息")
    @ApiImplicitParam(name = "id", value = "文件id", required = true)
    public ResponseData getById(@RequestParam("id") Long id){
        if(id != null){
            SysFileInfo sysFileInfo = sysFileInfoService.getById(id);
            return new SuccessResponseData(sysFileInfo);
        }
        return new ErrorResponseData(ResponseData.DEFAULT_ERROR_CODE,"无文件信息");
    }

    @ApiOperation("上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessValue", value = "关联值", required = true, dataType = "string"),
            @ApiImplicitParam(name = "type", value = "类型 1=款式图 2=详情图 3=合同", required = true, dataType = "int")
    })
    @PostMapping(value= "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseData uploadFile(@RequestPart( "files") MultipartFile[] files, String businessValue, Integer type){
        int successNum = sysFileService.uploadFile(files, businessValue, type);
        return successNum > 0 ? new SuccessResponseData() : new ErrorResponseData(ResponseData.DEFAULT_ERROR_CODE,"上传失败！");
    }

    @ApiOperation("查看文件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessDataId", value = "关联值id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "business_type", value = "类型", required = true, dataType = "int")
    })
    @GetMapping("/list")
    @BusinessLog(title = "文件信息表_查询所有", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(@RequestParam("businessDataId") Long businessDataId, @RequestParam("businessType") Integer businessType) {
        SysFileInfoParam sysFileInfoParam = new SysFileInfoParam();
        sysFileInfoParam.setBusinessDataId(businessDataId);
        sysFileInfoParam.setBusinessType(businessType);
        return new SuccessResponseData(sysFileInfoService.list(sysFileInfoParam));
    }

    @GetMapping("/download")
    @ApiOperation("根据文件id下载")
    @ApiImplicitParam(name = "id", value = "文件id", required = true)
    @BusinessLog(title = "文件下载", opType = LogAnnotionOpTypeEnum.EXPORT)
    public void download(Long id, HttpServletResponse response){
        SysFileInfoParam sysFileInfoParam = new SysFileInfoParam();
        sysFileInfoParam.setId(id);
        sysFileInfoService.download(sysFileInfoParam, response);
    }

    @GetMapping("/delFile")
    @ApiOperation("删除文件")
    @ApiImplicitParam(name = "id", value = "文件id", required = true)
    @BusinessLog(title = "删除文件", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delFile(Long id){
        SysFileInfoParam sysFileInfoParam = new SysFileInfoParam();
        sysFileInfoParam.setId(id);
        boolean result = sysFileInfoService.delFile(id);
        return result ? new SuccessResponseData() : new ErrorResponseData(ResponseData.DEFAULT_ERROR_CODE, "操作失败！");
    }

}
