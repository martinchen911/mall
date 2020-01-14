package com.cf.mall.common.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

/**
 * @Author chen
 * @Date 2020/1/14
 */
public class UploadUtil {


    public static String uploadImage(MultipartFile multipartFile) {
        StringBuilder sb = new StringBuilder("http://192.168.9.191:8888");

        // 客户端配置文件路径
        String path = UploadUtil.class.getResource("/tracker.conf").getPath();

        try {
            // 获取 storage 实例
            ClientGlobal.init(path);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getTrackerServer();
            StorageClient storageClient = new StorageClient(trackerServer,null);

            // 获取文件格式名
            String fileName = multipartFile.getOriginalFilename();
            String exName = fileName.substring(fileName.lastIndexOf(".")+1);
            // 上传文件并接收路径信息
            String[] uploadInfo = storageClient.upload_file(multipartFile.getBytes(),exName,null);
            // 拼接访问url
            Arrays.stream(uploadInfo).forEach( s -> sb.append("/").append(s));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
