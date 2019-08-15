package com.liugeng.cloud.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

public class RSAUtil {

    private static Logger logger = LoggerFactory.getLogger(RSAUtil.class);


    /**私钥 */
    private final static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC5vX2c349mVYTf9epp0wZeGpXRSkzNFp5UaKQ5aROZ5VnwX95wzXFe/3cHptKMRvPKXEINsWIamCaa2SwwrixS/onf6ZCNAASlVa0aPI5SRixtXOUbgnoDs/fYyrqF6/DjxtMd8WIml8fpHunZkaYN30kBfWu9T9n61pRCMcEUF7Tje+ynQPm90smiXZFMOOgHdF7OczhmnoLF3XOwf/rQyqEm1hJCBCoc3zGUB1bnbasScxTmGcw154wuMWBgaLyRzeYDccV2HjOreqyj/1hucd0wwHkkpE+4D/V5ZBcXoMULlMWofHLlJTL3tu33Y48rjmZC6NlL6gLSUapD6KgFAgMBAAECggEAXSbvrJFidcxGC5xlqBUxiFiE8qX0xpalaWfhlPlh3zjrVmZ+RMyEI0S0CZ5c0gVzlvWWGw/vR68vDVIFm5R+nlESDL4yxMRRkCnb2uCr8A9JkL+aF6XkU58amhtyWF/wPAwnLUk2FGzi0cOzPG6TfTmSf7DLK/K0O90KFfimANDVw0eQ9O8Cp0+x1bqlaXU97KL/na1dBk/VTDQd9V3AWfb+QkKZUJjDHfl+heATrILEKGNA59vEg5gIrhGKdSVL45VzOrVTMaQIpcTY2OYms2jJsQLTcM0b3f33Ur7ejVcYrEFC1SUlfVetg8LIukVWOrwslfbS2rHz/ydcsjstAQKBgQD83CdvNVWe5mr6S4lEo48txHfNShmJ0Q8YzHz5RLHOmiQj4/Az1MVQG0ZqAFvnKllT8pP0YA9sZuG2rcE3ZoYizsohlbdtw9XYygk9kk83U6w5hQpEMuBTWvXKjCXoI3J8GNDMFAgmpnib39RbhzHmCH+IGUomdfizhgWWPcc2NQKBgQC8C/Y2oD86JE8IZ2ZkPge6D2MI9a8uYl2xmVisMfPzQ7WZArZMcOfWjtE6eyliPnJkT0NHsTdL98zhcuZLoXAxEwqRfTUj9MX0LhKT8WNd4CxxJ5Rmc9VFhb6/t5O5h3/lKgTwpp4mR1t4wLYUOLDkvFpMlxJe1IjLkP+wARWkkQKBgQCUqc8ZiA7t3GS7AZwT2eG9M8yHyvAW4e3xU5CKcUPkxyVGYKYBoy/auB5/BfUChRh9zZoFTFDlF9vOrntx2fo1DsUyV7a/dysMSUSDibi/O5d/PePaUtsmxszJp/pFcEnIAkMIKSLdrnQsmL1ejq7cotRwLQY2dJei3MH+IhY0nQKBgHn/cK8j66E26SdUnfMu4yhszx2C9zIqkwqjqAmfJtuN9ATe45JYNbDXWxqLyN96EBDP86BRcsSQcZVaS9qJVBYielrFVnKLXNn8AgWFN6gxxeNrgDWvtI+0I3qSCP72z5zHVAkkr7m51bhTnk0kMm9TW2oRINPxIwaefaOcMIRhAoGBAOZzxTQlm5G7ZV3284RRHZVFSP196tV1o12cP4/zBI6aeKOkro3nLhsZxPhDMeLFxk5jdKgdsxsmqnqZMkmzoyaNbYuM+xZ5iVuhxNAkUqOqoiouoDuCJOOVM7Sw2/TqiGg1qJDnLMnspIWl9BMH/3kyIHz5rukywyLbSxlHo73x";

    /**公钥 */
    private static final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAub19nN+PZlWE3/XqadMGXhqV0UpMzRaeVGikOWkTmeVZ8F/ecM1xXv93B6bSjEbzylxCDbFiGpgmmtksMK4sUv6J3+mQjQAEpVWtGjyOUkYsbVzlG4J6A7P32Mq6hevw48bTHfFiJpfH6R7p2ZGmDd9JAX1rvU/Z+taUQjHBFBe043vsp0D5vdLJol2RTDjoB3ReznM4Zp6Cxd1zsH/60MqhJtYSQgQqHN8xlAdW522rEnMU5hnMNeeMLjFgYGi8kc3mA3HFdh4zq3qso/9YbnHdMMB5JKRPuA/1eWQXF6DFC5TFqHxy5SUy97bt92OPK45mQujZS+oC0lGqQ+ioBQIDAQAB";

    /** 指定加密算法为RSA */
    private static final String ALGORITHM = "RSA";

    /** sha加密 **/
    private static final String SHA = "SHA";

    /** md5加密 **/
    private static final String MD5 = "MD5";

    private static  long timestamps = 0;

    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
    * 方法说明   获取sign
    * @方法名    getSign
    * @参数      [timestamp]
    * @返回值    java.lang.String
    * @异常      
    * @创建时间  2019/5/9 15:22
    * @创建人    liugeng
    */
    public static String getSign(long timestamp) throws Exception{
        String data = RSAUtil.getShaData(timestamp);
        String sign = RSAUtil.sign(data);
        return sign;
    }

    /**
    * 方法说明   获取时间戳
    * @方法名    getTimestamo
    * @参数      []
    * @返回值    long
    * @异常
    * @创建时间  2019/5/9 14:56
    * @创建人    liugeng
    */
    public static long getTimestamo(){
        return System.currentTimeMillis();
    }

    /**
    * 方法说明   校验时间戳和sign
    * @方法名    verify
    * @参数      [timestamp, sign]
    * @返回值    boolean
    * @异常
    * @创建时间  2019/5/9 15:21
    * @创建人    liugeng
    */
    public static boolean verify(long timestamp,String sign) throws Exception{
        String shaNewData = getShaData(timestamp);
        String signNew = RSAUtil.sign(shaNewData);
        logger.info("oldSign:》》》》》》" + sign);
        logger.info("newSign:》》》》》》" + signNew);
        if(sign.equals(signNew)){
            return true;
        }
        return false;
    }

    /**
    * 方法说明   获取Sha加密串
    * @方法名    getShaData
    * @参数      [timestamp]
    * @返回值    java.lang.String
    * @异常
    * @创建时间  2019/5/9 15:23
    * @创建人    liugeng
    */
    public static String getShaData(long timestamp) throws Exception {
        String data = timestamp + publicKey;
        MessageDigest sha = MessageDigest.getInstance(SHA);
        sha.update(data.getBytes());
        return new String(Base64.getEncoder().encode(sha.digest()));
    }

    /**
    * 方法说明   通过时间戳和签名获取token
    * @方法名    getToken
    * @参数      [timestamp, sign]
    * @返回值    java.lang.String
    * @异常      
    * @创建时间  2019/5/9 16:01
    * @创建人    liugeng
    */
    public static String getToken(long timestamp,String sign) throws Exception{
        String uuid = UUID.randomUUID().toString();
        String tokenStr = uuid + "=" + timestamp + "=" + getMd5Data(System.currentTimeMillis() + sign + UUID.randomUUID().toString());
        return tokenStr;
    }

    /**
    * 方法说明   获取MD5加密字符串
    * @方法名    getMd5Data
    * @参数      [data]
    * @返回值    java.lang.String
    * @异常      
    * @创建时间  2019/5/9 16:00
    * @创建人    liugeng
    */
    public static String getMd5Data(String data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(MD5);
        sha.update(data.getBytes());
        return new String(Base64.getEncoder().encode(sha.digest()));
    }

    /**
    * 方法说明   私钥签名
    * @方法名    sign
    * @参数      [data]
    * @返回值    java.lang.String
    * @异常
    * @创建时间  2019/5/9 15:22
    * @创建人    liugeng
    */
    private static String sign(String data) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data.getBytes());
        return  new String(Base64.getEncoder().encode(signature.sign()));
    }

    public static void main(String[] args)throws Exception {
        timestamps = RSAUtil.getTimestamo();//获取时间戳
        String sign = RSAUtil.getSign(timestamps);//获取sign
        boolean flag = RSAUtil.verify(timestamps,sign);//校验时间戳和sign是否正确
        boolean flag2 = RSAUtil.verify(Long.valueOf("1557386855502"),sign);//校验错误时间戳是否正确
        //校验错误sign是否正确
        boolean flag3 = RSAUtil.verify(timestamps,"pQnpVuWsb5ZShBHgtyQSc+hveU11LI3Z07WkZ9zYJMdlxse4CuAlEztSbypksCyuWELymI455qNBIoVixsAdC+z53PobEqKqhjfSNTJ+Uxhw+iaH5QCmJrvKf32XrdqnhjUFKAThdbSiC0jHgIzLQNN7lPWC1DvQi/gzqChnJikpenLHAOVtiDb2+wqEIy/fEE5kRYoj2fXB88b4EHRNN11gbj87vz9uYrdmteIQB71fN4NnMfY6G4kafy1BMGttDbQE9v3vUTQh91U29QPvn9v4jG+PzX3CmWEYBbnusjxN+6QfQLY+7jRYNjgC53DJsxG/Ui6JZ/w+oyxKvLAidw==");
        //校验错误时间戳,错误sign是否正确
        boolean flag4 = RSAUtil.verify(Long.valueOf("1557386855502"),"pQnpVuWsb5ZShBHgtyQSc+hveU11LI3Z07WkZ9zYJMdlxse4CuAlEztSbypksCyuWELymI455qNBIoVixsAdC+z53PobEqKqhjfSNTJ+Uxhw+iaH5QCmJrvKf32XrdqnhjUFKAThdbSiC0jHgIzLQNN7lPWC1DvQi/gzqChnJikpenLHAOVtiDb2+wqEIy/fEE5kRYoj2fXB88b4EHRNN11gbj87vz9uYrdmteIQB71fN4NnMfY6G4kafy1BMGttDbQE9v3vUTQh91U29QPvn9v4jG+PzX3CmWEYBbnusjxN+6QfQLY+7jRYNjgC53DJsxG/Ui6JZ/w+oyxKvLAidw==");
        //产生token
        String token = RSAUtil.getToken(timestamps,sign);
        System.out.println("时间戳:::"+timestamps);
        System.out.println("私钥签名:::"+sign);
        System.out.println("校验正确时间戳和sign是否成功:::"+flag);
        System.out.println("校验错误时间戳和正确sign是否成功:::"+flag2);
        System.out.println("校验正确时间戳和错误sign是否成功:::"+flag3);
        System.out.println("校验错误时间戳和错误sign是否成功:::"+flag4);
        System.out.println("产生的token:::"+token);

    }
}
