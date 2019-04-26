package com.liugeng.cloud.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
* @Description:    短链接生成
* @Author:         liugeng
* @CreateDate:     2019/4/18 17:03
* @UpdateUser:     liugeng
* @UpdateDate:     2019/4/18 17:03
* @UpdateRemark:   修改内容
*/
public class ShortUrlUtil {
    //固定短链接url前置
    private static final String defaultUrl = "http://liugeng.cn/";
    //密钥
    private static final String key = "liugeng";
    // 要使用生成 URL 的字符
    private static final char hexChar[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8' , '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    // 要使用生成 URL 的字符
    private static final String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
            "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
            "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
            "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
            "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
            "U" , "V" , "W" , "X" , "Y" , "Z"};

    /**
    * 方法说明 获取短链接
    * @方法名   getShortUrl
    * @参数     [longUrl]
    * @返回值   java.lang.String
    * @异常
    * @创建时间 2019/4/18 16:53
    * @创建人 liugeng
    */
    public static String getShortUrl(String longUrl){
        //获取长链接加密结果数组，数组值为4
        String[] resultUrls = generateShortUrl(longUrl);
        //取随机值
        Random random = new Random();
        return defaultUrl + resultUrls[random.nextInt(4)];
    }

    /**
    * 方法说明 获取长链接加密结果数组（最多四个）
    * @方法名   generateShortUrl
    * @参数     [url]
    * @返回值   java.lang.String[]
    * @异常     
    * @创建时间 2019/4/18 16:53
    * @创建人 liugeng
    */
    private static String[] generateShortUrl(String url) {
        //将长链接进行MD5加密
        String sMD5EncryptResult = getMd5(key + url);
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];
        for ( int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);
            String outChars = "" ;
            for ( int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[( int ) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;
    }
    
    /**
    * 方法说明 MD5加密
    * @方法名   getMd5
    * @参数     [param]
    * @返回值   java.lang.String
    * @异常     
    * @创建时间 2019/4/18 16:52
    * @创建人 liugeng
    */
    public static String getMd5(String param) {
        byte[] b = param.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(b);
            byte[] b2 = md.digest();
            char str[] = new char[b2.length << 1];
            int len = 0;
            for (int i = 0; i < b2.length; i++) {
                byte val = b2[i];
                str[len++] = hexChar[(val >>> 4) & 0xf];
                str[len++] = hexChar[val & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        //String sLongUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0d5d33b1dbfdd69a&redirect_uri=https://beta.bndxqc.com/gp/wx/wap/entrance/redirectUrl?url=n0KjYk97N1M50dFz9oaJfkwUzCZgJDlInSRk1O%2BRR9OWEsmghcQ%2FKpAJbbQw0bbiSQEOv5u1hY3jm57GRos%2BTQ%3D%3D&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect" ;
        String sLongUrl = "http://localhost:8081/cloudTest/";
        String resultUrl = getShortUrl(sLongUrl);
        System.out.println(resultUrl);
    }
}
