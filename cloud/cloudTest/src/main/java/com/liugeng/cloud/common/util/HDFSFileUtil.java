package com.liugeng.cloud.common.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HDFSFileUtil {

    private static final String SEPARATOR = File.separator;

    /**
     * 获取文件客户端
     * @return
     */
    private static FileSystem getFileSystem() {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS","hdfs://127.0.0.1:9000");
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(configuration);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return fileSystem;
    }

    /**
     * 创建文件夹
     * @param path
     */
    public static void mkdir(String path) {
        // 获取文件客户端
        try (FileSystem fileSystem = getFileSystem()) {
            boolean result = fileSystem.mkdirs(new Path(SEPARATOR + path));
            System.out.println(result);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件或目录
     * @param path
     */
    public static void rmdir(String path) {
        // 获取文件客户端
        try (FileSystem fileSystem = getFileSystem()) {
            boolean result = fileSystem.delete(new Path(SEPARATOR + path),true);
            System.out.println(result);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 本地文件复制到Hdfs
     * @param delSrc
     * @param overwrite
     * @param srcFile
     * @param destFile
     */
    public static void copyFileToHDFS(boolean delSrc,boolean overwrite,String srcFile,String destFile) {
        // 获取文件客户端
        try (FileSystem fileSystem = getFileSystem()) {
            fileSystem.copyFromLocalFile(delSrc,overwrite,new Path(SEPARATOR + srcFile),new Path(SEPARATOR + destFile));
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Hdfs复制到本地文件
     * @param srcFile
     * @param destFile
     */
    public static void copyToLocalFile(String srcFile,String destFile) {
        // 获取文件客户端
        try (FileSystem fileSystem = getFileSystem()) {
            fileSystem.copyToLocalFile(new Path(SEPARATOR + srcFile),new Path(SEPARATOR + destFile));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件
     * @param file
     * @param destFile
     */
    public static void create(MultipartFile file,String destFile) {
        // 获取文件客户端
        try (FileSystem fileSystem = getFileSystem()) {
            FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path(SEPARATOR + destFile));
            IOUtils.copyBytes(file.getInputStream(),fsDataOutputStream,fileSystem.getConf());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     * @param srcFile
     * @param response
     */
    public static void download(String srcFile, HttpServletResponse response) {
        // 获取文件客户端
        try (FileSystem fileSystem = getFileSystem()) {
            FSDataInputStream fsDataInputStream = fileSystem.open(new Path(SEPARATOR + srcFile));
            // 读入文件流
            InputStream fis = new BufferedInputStream(fsDataInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" +
                    new String((DateUtil.format(new Date(),"yyyyMMddHHmmssSSS") + "_" + srcFile).getBytes()));
            response.addHeader("Content-Length", "" + fsDataInputStream.getPos());
            OutputStream ops = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            ops.write(buffer);
            ops.flush();
            ops.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取目录下所有文件
     * @param path
     * @return
     */
    public static List<String> listFile(String path) {
        List<String> resultList = new ArrayList<>();
        // 获取文件客户端
        try (FileSystem fileSystem = getFileSystem()) {
            FileStatus[] fileStatuses = fileSystem.listStatus(new Path(SEPARATOR + path));
            for (FileStatus fileStatus : fileStatuses) {
                String result = "是否目录：" + fileStatus.isDirectory() + ",路径：" + fileStatus.getPath().toString();
                resultList.add(result);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

}
