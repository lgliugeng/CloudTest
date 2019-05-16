package com.liugeng.cloud.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringProcessUtil {

    /**
     * 删除Html标签
     * @param inputString
     * @return
     */
    public static String removeHtmlTag(String inputString) {
        if (inputString == null)
            return null;
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        java.util.regex.Pattern p_special;
        java.util.regex.Matcher m_special;
        try {
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            // 定义HTML标签的正则表达式
            String regEx_html = "<[^>]+>";
            // 定义一些特殊字符的正则表达式 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            String regEx_special = "\\&[a-zA-Z]{1,10};";

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
            m_special = p_special.matcher(htmlStr);
            htmlStr = m_special.replaceAll(""); // 过滤特殊标签
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }

    /**
    * 方法说明   分离图文并茂字符串
    * @方法名    dividePhotoLink
    * @参数      [inputStr]
    * @返回值    java.lang.String[]
    * @异常
    * @创建时间  2019/5/16 10:15
    * @创建人    liugeng
    */
    public static String[] dividePhotoLink(String inputStr){
        String[] linkArr = null;
        if(inputStr != null){
            Pattern pattern =Pattern.compile("(http://|https://){1}[\\w\\.\\-/:]+");
            inputStr = inputStr.replaceAll("<img src=\"",",").replaceAll("\">", ",");
            linkArr = inputStr.split(",");
            Matcher matcher = null;
            StringBuffer buffer = null;
            for (int i = 0; i < linkArr.length; i++) {
                matcher =pattern.matcher(linkArr[i]);
                buffer = new StringBuffer();
                if(matcher.find()){
                    buffer.append(matcher.group());
                    linkArr[i] = buffer.toString();
                }else{
                    linkArr[i] = removeHtmlTag(linkArr[i]);
                }
            }
        }
        return linkArr;
    }

    /**
     * 测试用的main函数
     * @param args
     */
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        sb.append("sdfsd<img src=\"https://beta.bndxqc.com/gp/js/layui/images/face/0.gif\" alt=\"[微笑]\"><p><div></div><i></i><ul><img src=\"http://a.appsimg.com/upload/merchandise/pdcvis/2018/11/21/15/db329477-010c-4836-8a84-1449798359c9.jpg\" alt=\"[微笑]\"><ul>");
        String ssss = StringProcessUtil.removeHtmlTag(sb.toString());
        String[] ssssArr = StringProcessUtil.dividePhotoLink(sb.toString());
        System.out.println("字符：" + ssss);
        for (int i = 0; i < ssssArr.length; i++) {
            System.out.println("数组："+ ssssArr[i]);
        }
    }
}
