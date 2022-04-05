package vip.xiaonuo.main.modular.tbcustomerask.util;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

@Component
public class UploadFileUtil {

    @Value("${file.local-path}")
    private String localPath;

    /**
     * 将文件上传到本地
     *
     * @param uploadPhoto 文件
     * @return 文件的全路径
     * @throws java.io.IOException
     */
    public String uploadPhotosAndGetPath(MultipartFile uploadPhoto) throws IOException {

        String type = uploadPhoto.getOriginalFilename().substring(uploadPhoto.getOriginalFilename().lastIndexOf("."));
        String fileName = System.currentTimeMillis() + UUID.randomUUID().toString().replace("-", "").substring(0, 4) + type;
        //创建日期分层文件夹
        String dayStr = DateUtil.format(new Date(), "yyyy/MM/dd/");
        //判断该路径是否存在
        File file = new File(localPath+ "/photos/" + dayStr);
        // 若不存在则创建该文件夹
        if (!file.exists()) {
            file.mkdirs();
        }
        uploadPhoto.transferTo(new File(localPath +  "/photos/" + dayStr, fileName));
        return "photos/" + dayStr + fileName;
    }

    /**
     * 将文件上传到本地
     *
     * @param styleNum    款号
     * @param uploadPhoto 文件
     * @return 文件的全路径
     * @throws java.io.IOException
     */
    public String uploadExcelAndGetPath(String styleNum, MultipartFile uploadPhoto) throws IOException {
        String type = uploadPhoto.getOriginalFilename().substring(uploadPhoto.getOriginalFilename().lastIndexOf("."));
        String fileName = styleNum + "---" + System.currentTimeMillis() + type;
        //创建日期分层文件夹
        String dayStr = DateUtil.format(new Date(), "yyyy/MM/dd/");
        //判断该路径是否存在
        File file = new File(localPath + "/excel/" + dayStr);
        // 若不存在则创建该文件夹
        if (!file.exists()) {
            file.mkdirs();
        }
        uploadPhoto.transferTo(new File(localPath + "/excel/" + dayStr, fileName));
        return "excel/" + dayStr + fileName;
    }

    /**
     * 删除本地文件
     * @param item
     */
    public void delete(String item) {
        File file = new File(localPath +"/" + item);
        if (file.exists()){
            file.delete();
        }
    }


    /**
     * MultipartFile 转 File
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    /**
     * 获取流文件
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

}
