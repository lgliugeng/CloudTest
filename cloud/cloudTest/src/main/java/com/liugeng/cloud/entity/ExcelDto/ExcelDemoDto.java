package com.liugeng.cloud.entity.ExcelDto;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

public class ExcelDemoDto implements Serializable {

    @Excel(name = "主键",orderNum = "0")
    private Integer id;

    @Excel(name = "姓名",orderNum = "1")
    private String name;

    @Excel(name = "体重",orderNum = "2")
    private double height;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
