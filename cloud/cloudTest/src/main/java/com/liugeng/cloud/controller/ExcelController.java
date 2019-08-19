package com.liugeng.cloud.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.util.PoiReflectorUtil;
import com.liugeng.cloud.common.util.DateUtil;
import com.liugeng.cloud.common.util.ExcelUtil;
import com.liugeng.cloud.entity.ApiResult;
import com.liugeng.cloud.entity.ExcelDto.ExcelDemoDto;
import com.liugeng.cloud.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/excel")
@Api(value = "Excel请求接口",tags = "Excel请求接口")
public class ExcelController {

    @RequestMapping(value = "/export/pojo",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "自定义对象导出Excel")
    public void exportExcel(HttpServletResponse response){
        List<ExcelDemoDto> excelDemoDtoList = new ArrayList<>();
        ExcelDemoDto excelDemoDto = new ExcelDemoDto();
        excelDemoDto.setId(1);
        excelDemoDto.setName("张三");
        excelDemoDto.setHeight(65.55);
        excelDemoDtoList.add(excelDemoDto);
        ExportParams exportParams = new ExportParams();
        exportParams.setExclusions(new String[]{"体重"});
        String fileName = "测试Excel导出";
        fileName = fileName + DateUtil.getAllTime() + ".xls";
        ExcelUtil.exportExcel(excelDemoDtoList,ExcelDemoDto.class,fileName,exportParams,response);
    }

    @RequestMapping(value = "/import/pojo",method = RequestMethod.POST)
    @ApiOperation(value = "自定义对象导入Excel")
    public ApiResult importExcel(@RequestParam("file") MultipartFile file){
        ApiResult apiResult = new ApiResult("200","ok");
        List<ExcelDemoDto> excelDemoDtoList = ExcelUtil.importExcel(file,0,1,ExcelDemoDto.class);
        apiResult.setData(excelDemoDtoList);
        return apiResult;
    }
}
