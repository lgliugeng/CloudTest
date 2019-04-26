package com.liugeng.cloud.service;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import com.liugeng.cloud.entity.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;
/**
* @Description:    生成数据库pdf文档
* @Author:         liugeng
* @CreateDate:     2019/4/18 16:56
* @UpdateUser:     liugeng
* @UpdateDate:     2019/4/18 16:56
* @UpdateRemark:   修改内容
*/
@Service
public class DataToPdf{


    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
    * 方法说明 获取数据库中所有的表信息
    * @方法名   getAllTable
    * @参数     []
    * @返回值   java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    * @异常     
    * @创建时间 2019/4/18 16:55
    * @创建人 liugeng
    */
    public List<Map<String,Object>> getAllTable(){
        List<Map<String,Object>> list = jdbcTemplate.queryForList("select table_name,table_comment from information_schema.tables where table_schema = 'gifts_20181126'");
        return list;
    }

    /**
     * 方法说明   获取数据库所有表以及表对应的字段信息，写出pdf格式数据库文档
     * @方法名    dataToPdf
     * @参数      [tableListAll, pathname]
     * @返回值    com.liugeng.cloud.entity.ApiResult
     * @异常
     * @创建时间  2019/4/18 17:00
     * @创建人    liugeng
     */
    public ApiResult dataToPdf(List<Map<String,Object>> tableListAll,String pathname){
        ApiResult apiResult = new ApiResult();
        apiResult.setCode("0");
        apiResult.setMsg("ok");
        Document doc = new Document(PageSize.A4);
        File file = new File(pathname);
        try {

            OutputStream os = new FileOutputStream(file);
            PdfWriter.getInstance(doc,os);
            doc.open();
            Paragraph paragraph = new Paragraph();
            Font f = new Font();
            Paragraph p = new Paragraph("数据库表设计文档", new Font( Font.FontFamily.HELVETICA, 24f,Font.getStyleValue(Font.FontStyle.NORMAL.getValue()), new BaseColor(0, 0, 0)));
            p.setAlignment(1);
            doc.add(p);
            paragraph.setFont(f);/* * 创建表格 通过查询出来的表遍历 */

            for (int i = 0; i < tableListAll.size(); i++) {
                // 表名
                String table_name = (String) tableListAll.get(i).get("table_name");
                // 表说明
                String table_comment = (String) tableListAll.get(i).get("table_comment");
                String sql = "SHOW FULL FIELDS FROM gifts_20181126." + table_name+ " ";
                //获取某张表的所有字段说明
                List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
                //构建表说明
                String all = "" + (i + 1) + " 表名：" + table_name + " "+ table_comment + "";
                //创建有6列的表格
                PdfPTable table = new PdfPTable(6);
                doc.add(new Paragraph(""));
                table.setTotalWidth(1);
                table.setPaddingTop(0);
                table.setSpacingAfter(0);
                /*
                 * 添加表头的元素，并设置表头背景的颜色
                 */

                BaseColor chade = new BaseColor(176, 196, 222);
                PdfPHeaderCell headerCell = new PdfPHeaderCell();
                headerCell.setName("序号");// 单元格
                headerCell.setBackgroundColor(chade);
                headerCell.setBackgroundColor(chade);
                table.addCell(headerCell);

                headerCell = new PdfPHeaderCell();
                headerCell.setName("字段名");// 单元格
                headerCell.setBackgroundColor(chade);
                table.addCell(headerCell);

                headerCell = new PdfPHeaderCell();
                headerCell.setName("类型");// 单元格
                headerCell.setBackgroundColor(chade);
                table.addCell(headerCell);

                headerCell = new PdfPHeaderCell();
                headerCell.setName("是否为空");// 单元格
                headerCell.setBackgroundColor(chade);
                table.addCell(headerCell);

                headerCell = new PdfPHeaderCell();
                headerCell.setName("主键");// 单元格
                headerCell.setBackgroundColor(chade);
                table.addCell(headerCell);

                headerCell = new PdfPHeaderCell();
                headerCell.setName("字段说明");// 单元格
                headerCell.setBackgroundColor(chade);
                table.addCell(headerCell);

                // 表格的主体，
                for (int k = 0; k < list.size(); k++) {
                    //获取某表每个字段的详细说明
                    String Field = (String) list.get(k).get("Field");
                    String Type = (String) list.get(k).get("Type");
                    String Null = (String) list.get(k).get("Null");
                    String Key = (String) list.get(k).get("Key");
                    String Comment = (String) list.get(k).get("Comment");
                    table.addCell((k + 1) + "");
                    table.addCell(Field);
                    table.addCell(Type);
                    table.addCell(Null);
                    table.addCell(Key);
                    table.addCell(Comment);
                }
                Paragraph pheae = new Paragraph(all);
                //写入表说明
                doc.add(pheae);
                //生成表格
                doc.add(table);
            }
            doc.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            apiResult.setCode("1");
            apiResult.setMsg("fail:"+e.getMessage());
        } catch (DocumentException e) {
            e.printStackTrace();
            apiResult.setCode("1");
            apiResult.setMsg("fail:"+e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            apiResult.setCode("1");
            apiResult.setMsg("fail:"+e.getMessage());
        }
        return apiResult;
    }
}
