package com.liugeng.cloud.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
    * 方法说明   拼接字符串
    * @方法名    contact
    * @参数      [str, anoStr]
    * @返回值    java.lang.String
    * @异常
    * @创建时间  2019/5/5 18:20
    * @创建人    liugeng
    */
    public String concat(String str,String anoStr){
        char[] valStr = this.getChars(str);//获取字符以及长度
        int valStrLen = valStr.length;

        char[] anoValStr = this.getChars(anoStr);//获取另外一个字符和长度
        int anoValStrLen = anoValStr.length;

        int newLength = valStrLen + anoValStrLen;
        char[] newChar = new char[newLength];//产生新的数组
        System.arraycopy(valStr,0,newChar,0,valStrLen);//复制数组
        System.arraycopy(anoValStr,0,newChar,valStrLen,anoValStrLen);//将原数组拼接复制到新数组
        return new String(newChar);//new一个string
    }

    /**
    * 方法说明   去除首尾空格
    * @方法名    trim
    * @参数      [str]
    * @返回值    java.lang.String
    * @异常
    * @创建时间  2019/5/5 19:10
    * @创建人    liugeng
    */
    public String trim(String str){
        char[] chars = this.getChars(str);//获取数组字符
        int len = chars.length;
        int st = 0;
        char[] val = chars;
        while((st < len) && (val[st] <= ' ')){//首位为空格且小于最大长度时
            st++;
        }
        while((st < len) && (val[len-1] <= ' ')){//尾位为空格且比首位计数大时
            len--;
        }
        return ((st > 0) || (len < chars.length)) ? this.substring(st,len,str) : str;
    }

    /**
    * 方法说明   截取字符串
    * @方法名    substring
    * @参数      [beginIndex, endIndex, str]
    * @返回值    java.lang.String
    * @异常      
    * @创建时间  2019/5/5 19:06
    * @创建人    liugeng
    */
    public String substring(int beginIndex,int endIndex,String str){
        char[] chars = this.getChars(str);//获取字符
        if(beginIndex < 0){
            throw new StringIndexOutOfBoundsException(beginIndex);
        }
        if(endIndex > chars.length){
            throw new StringIndexOutOfBoundsException(endIndex);
        }
        int len = endIndex - beginIndex;
        if(len < 0){
            throw new StringIndexOutOfBoundsException(len);
        }
        return ((beginIndex == 0) && (endIndex == chars.length)) ? str : new String(chars,beginIndex,endIndex);//截取字符长与原字符一致则返回字符串，否则根据所给长度new一个string
    }

    /**
    * 方法说明   替换
    * @方法名    replace
    * @参数      [target, replacement, str]
    * @返回值    java.lang.String
    * @异常
    * @创建时间  2019/5/5 19:19
    * @创建人    liugeng
    */
    public String replace(CharSequence target,CharSequence replacement,CharSequence str){
        return Pattern.compile(target.toString(),Pattern.LITERAL).matcher(str).replaceAll(Matcher.quoteReplacement(replacement.toString()));
    }

    /**
    * 方法说明   equals方法
    * @方法名    equals
    * @参数      [obj, anoObj]
    * @返回值    boolean
    * @异常      
    * @创建时间  2019/5/5 19:04
    * @创建人    liugeng
    */
    public boolean equals(Object obj,Object anoObj){
        if(obj == anoObj){//对象一致返回true
            return true;
        }
        if(obj instanceof String && anoObj instanceof String){//对象均为字符串
            String objStr = (String)obj;
            int len1 = this.getChars(objStr).length;

            String anoObjStr = (String)anoObj;
            int len2 = this.getChars(anoObjStr).length;

           if(len1 == len2){//比较字符长度
               char[] v1 = this.getChars(objStr);
               char[] v2 = this.getChars(anoObjStr);
               int i = 0;
               while (len1-- != 0){//字符长度一致递减比较字符是否一致
                   if(v1[i] != v2[i]){//字符不一致返回false
                        return false;
                   }
                   i++;
               }
               return true;
           }
        }
        return false;
    }

    /**
    * 方法说明   获取数组字符长度
    * @方法名    length
    * @参数      [str]
    * @返回值    int
    * @异常
    * @创建时间  2019/5/5 18:12
    * @创建人    liugeng
    */
    public int length(String str){
        return this.getChars(str).length;
    }

    /**
    * 方法说明   转换为数组字符
    * @方法名    getChars
    * @参数      [str]
    * @返回值    char[]
    * @异常
    * @创建时间  2019/5/5 18:12
    * @创建人    liugeng
    */
    public char[] getChars(String str){
        return str.toCharArray();
    }

    public static void main(String[] args)
    {
        StringUtil s = new StringUtil();

        String str1 = "hello,大家好，我是C";
        String str2 = "nice to see you!";
        String str21 = "nice to see you!";

        //模拟concat方法
        String str3 = s.concat(str1, str2);
        System.out.println("模拟concat方法 ： " + str3);

        //模拟concat方法
        String str4 = s.concat(s.trim(str1), str2);
        System.out.println("模拟trim方法 ： " + str4);

        //模拟concat方法
        String str11 = s.trim(str2);
        System.out.println("模拟trim方法 ： " + str11);

        //模拟substring
        String str5 = s.substring(0, 10, str1);
        System.out.println("模拟trim方法 ： " + str5);

        //模拟equals
        boolean b1 = s.equals(str1, str2);
        boolean b2 = s.equals(str2,str21);
        System.out.println("模拟equals方法 :  b1:" + b1 + " b2:" + b2);

        //模拟replace
        String str6 = s.replace("我是C", " java", str1);
        System.out.println("模拟replace方法 ： " + str6);

        //模拟length
        int length = s.length(str6);
        System.out.println("模拟length方法 ： " + length);
    }
}
