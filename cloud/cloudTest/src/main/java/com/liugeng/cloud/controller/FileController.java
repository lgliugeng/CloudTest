package com.liugeng.cloud.controller;


import com.liugeng.cloud.common.util.HDFSFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * HDFS文件处理类
 * 1.先部署hdfs文件服务器，通过https://www.apache.org/dyn/closer.cgi/hadoop/common/hadoop-2.10.0/hadoop-2.10.0.tar.gz下载文件压缩包
 * 2.windows系统下下载https://codeload.github.com/sardetushar/hadooponwindows/zip/master压缩包替换bin目录
 * 3.修改etc目录下core-site.xml文件设置数据保存路径，用于定义系统级别的参数，如HDFS URL 、Hadoop的临时目录等
 * 4.修改mapred-site.xml文件设置资源调度，包括JobHistory Server 和应用程序参数两部分，如reduce任务的默认个数、任务所能够使用内存的默认上下限等
 * 5.修改hdfs-site.xml文件设置高可用以及故障迁移等配置，如名称节点和数据节点的存放位置、文件副本的个数、文件的读取权限等
 * 6.修改yarn-site.xml文件设置集群资源管理系统参数，配置ResourceManager ，nodeManager的通信端口，web监控端口等
 * 7.控制台运行hadoop namenode -format命令进行初始化
 * 8.在hadoop/sbin目录下依次执行start-dfs.cmd和start-yarn.cmd启动并可通过http://localhost:50070/dfshealth.html#tab-overview进行监控
 * 9.JAVA调用使用时引入与服务器版本一致的hadoop-client依赖，通过获取FileSystem类进行文件操作
 * 10.若需要重新启动hdfs，通过控制台访问sbin目录重新执行start-dfs.cmd和start-yarn.cmd启动
 */
@RestController
@RequestMapping("/file")
@Api(value = "file请求接口",tags = "file请求接口")
public class FileController {

    @RequestMapping(value = "/listFile",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "文件集合")
    @ApiImplicitParam(value = "路径",name = "path",required = false,dataType = "String")
    public List<String> listFile(@RequestParam("path") String path){
        return HDFSFileUtil.listFile(path);
    }

    @RequestMapping(value = "/mkdir/{path}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "创建文件目录")
    @ApiImplicitParam(value = "路径",name = "path",required = true,dataType = "String")
    public void mkdir(@PathVariable("path") String path){
        HDFSFileUtil.mkdir(path);
    }

    @RequestMapping(value = "/rmdir/{path}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除文件或目录")
    @ApiImplicitParam(value = "路径",name = "path",required = true,dataType = "String")
    public void rmdir(@PathVariable("path") String path){
        HDFSFileUtil.rmdir(path);
    }

    @RequestMapping(value = "/copyFileToHDFS/{srcFile}/{destFile}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "复制文件到系统")
    @ApiImplicitParams({
        @ApiImplicitParam(value = "源路径",name = "srcFile",required = true,dataType = "String"),
        @ApiImplicitParam(value = "目标路径",name = "destFile",required = true,dataType = "String")
    })
    public void copyFileToHDFS(@PathVariable("srcFile") String srcFile,@PathVariable("destFile") String destFile){
        HDFSFileUtil.copyFileToHDFS(false,true,srcFile,destFile);
    }

    @RequestMapping(value = "/copyToLocalFile/{srcFile}/{destFile}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "复制系统文件到本地")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "源路径",name = "srcFile",required = true,dataType = "String"),
            @ApiImplicitParam(value = "目标路径",name = "destFile",required = true,dataType = "String")
    })
    public void copyToLocalFile(@PathVariable("srcFile") String srcFile,@PathVariable("destFile") String destFile){
        HDFSFileUtil.copyToLocalFile(srcFile,destFile);
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST,headers = "content-type=multipart/form-data")
    @ResponseBody
    @ApiOperation(value = "上传文件")
    public void create(@RequestParam("file") MultipartFile file){
        HDFSFileUtil.create(file,file.getOriginalFilename());
    }

    @RequestMapping(value = "/download/{srcFile}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "下载文件")
    @ApiImplicitParam(value = "源路径",name = "srcFile",required = true,dataType = "String")
    public void create(@PathVariable("srcFile") String srcFile,HttpServletResponse response){
        HDFSFileUtil.download(srcFile,response);
    }
}
