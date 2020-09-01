package com.example.demo.controller;

import com.example.demo.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author mulj
 * @date 2020/8/7 16:01
 * @Email:mlj@citycloud.com.cn
 */
@RestController
@RequestMapping("/")
@Slf4j
public class FastDfsController {
    /**
     * 上传文件到FastDFS
     *
     * @param file
     */
    @PostMapping(value = "fastDFSUpload")
    public Map<String,String>  fastDFSUpload(@RequestParam("file") MultipartFile file) {
        if(file==null){
            log.info("file is null");
            String str="file is null";
            Map<String,String>map=new HashMap<>();
            map.put("msg",str);
            return map;
        }
        String fileName=file.getOriginalFilename();
        int t=fileName.lastIndexOf(".");
        String ext_Name = fileName.substring(t+1);
        byte[] bytes = null;
        try {
           bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String,String> filePath= uploadFile(bytes,ext_Name,fileName);
        return filePath;
    }
    /**
     * 上传文件流到FastDFS
     *
     */
    @PostMapping(value = "fastDFSUploadStream")
    public Map<String,String>  fastDFSUploadStream(HttpServletRequest request) {
        byte[] bytes = null;
        InputStream is = null;
        String ext_Name="";
        try {
            Part part = request.getPart("file");
            String fileName=part.getSubmittedFileName();
            int t=fileName.lastIndexOf(".");
            ext_Name = fileName.substring(t+1);
            is=part.getInputStream();
            if(is==null){
                log.info("file is null");
                String str="file is null";
                Map<String,String>returnMap=new HashMap<>();
                returnMap.put("msg",str);
                return returnMap;
            }
        bytes = toByteArray(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String,String> filePath= uploadFile(bytes, ext_Name, "");
        return filePath;
    }
    /**
     * FastDFS实现文件下载
     *
     * @param filePath
     */
    @GetMapping(value = "/fastDFSDownload")
    public void fastDFSDownload(String filePath, HttpServletRequest request, HttpServletResponse response) {
        OutputStream out = null;
        try {
            ClientGlobal.initByProperties("application.properties");

            // 链接FastDFS服务器，创建tracker和Stroage
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();

            String storageServerIp = getStorageServerIp(trackerClient, trackerServer);
            StorageServer storageServer = getStorageServer(storageServerIp);
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            filePath.indexOf("/");
            byte[] b = storageClient.download_file("group1", filePath);
            if (b == null) {
                throw new IOException("文件" + filePath + "不存在");
            }
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            response.setHeader("content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.setContentType("application/x-msdownload");
            out=response.getOutputStream();
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(out!=null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * FastDFS获取将上传文件信息
     */
    @RequestMapping(value = "/fastDFSGetFileInfo", method = RequestMethod.GET)
    @ResponseBody
    public void fastDFSGetFileInfo(String filePath) {
        try {
            // 链接FastDFS服务器，创建tracker和Stroage
            ClientGlobal.initByProperties("application.properties");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();

            String storageServerIp = getStorageServerIp(trackerClient, trackerServer);
            StorageServer storageServer = getStorageServer(storageServerIp);
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            FileInfo fi = storageClient.get_file_info("group1", filePath);
            if (fi == null) {
                throw new IOException("文件" + filePath + "不存在");
            }

            log.info("文件信息=" + fi.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FastDFS获取文件名称
     */
    @RequestMapping(value = "/fastDFSGetFileName", method = RequestMethod.GET)
    @ResponseBody
    public void fastDFSGetFileName(String filePath) {
        try {
            // 链接FastDFS服务器，创建tracker和Stroage
            ClientGlobal.initByProperties("application.properties");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();

            String storageServerIp = getStorageServerIp(trackerClient, trackerServer);
            StorageServer storageServer = getStorageServer(storageServerIp);
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            NameValuePair[] nvps = storageClient.get_metadata("group1", filePath);
            if (nvps == null) {
                throw new IOException("文件" + filePath + "不存在");
            }

            log.debug(nvps[0].getName() + "." + nvps[0].getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FastDFS实现删除文件
     */
    @RequestMapping(value = "/fastDFSDelete", method = RequestMethod.GET)
    @ResponseBody
    public void fastDFSDelete(String filePath) {
        try {
            // 链接FastDFS服务器，创建tracker和Stroage
            ClientGlobal.initByProperties("application.properties");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();

            String storageServerIp = getStorageServerIp(trackerClient, trackerServer);
            StorageServer storageServer = getStorageServer(storageServerIp);
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            int i = storageClient.delete_file("group1", filePath);
            log.info(i == 0 ? "删除成功" : "删除失败:" + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String,String> uploadFile(byte[] byteFile, String ext_file, String file_Name) {
        Map<String,String>map=new HashMap<>();
        try {
            // 初始化文件资源
            ClientGlobal.initByProperties("application.properties");

            // 链接FastDFS服务器，创建tracker和Stroage
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();

            String storageServerIp = getStorageServerIp(trackerClient, trackerServer);
            StorageServer storageServer = getStorageServer(storageServerIp);
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            //利用字节流上传文件
            String[] strings = storageClient.upload_file(byteFile, ext_file, null);
            if (strings!=null) {
                map.put("groupName", strings[0]);
                map.put("fileId", strings[1]);
                log.debug("上传路径=" + StringUtil.joinArray("/", strings));
            }
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 得到Storage服务
     *
     * @param storageIp
     * @return 返回Storage服务
     */
    private static StorageServer getStorageServer(String storageIp) {
        StorageServer storageServer = null;
        if (storageIp != null && !("").equals(storageIp)) {
            try {
                // ip port store_path下标
                storageServer = new StorageServer(storageIp, 23000, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.debug("——storage server生成");
        return storageServer;
    }

    /**
     * 获得可用的storage IP
     *
     * @param trackerClient
     * @param trackerServer
     * @return 返回storage IP
     */
    private static String getStorageServerIp(TrackerClient trackerClient, TrackerServer trackerServer) {
        String storageIp = null;
        if (trackerClient != null && trackerServer != null) {
            try {
                StorageServer storageServer = trackerClient.getStoreStorage(trackerServer, "group1");
                storageIp = storageServer.getSocket().getInetAddress().getHostAddress();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.debug("——获取组中可用的storage IP——" + storageIp);
        return storageIp;
    }
    /**
     * InputStream 转换成byte[]
     * @param input
     * @return
     * @throws IOException
     */
    private static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
