package com.liugeng.cloud.common.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

public class ToolHttp {
    public static String post(boolean isHttps, String url) {
        CloseableHttpClient httpClient = null;
        try {
            if(!isHttps){
                httpClient = HttpClients.createDefault();
            }else{
                httpClient = createSSLInsecureClient();
            }
            HttpPost httpget = new HttpPost(url);
            //httpget.addHeader(new BasicHeader("", ""));
            //httpget.addHeader("", "");
            CloseableHttpResponse response = httpClient.execute(httpget);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 获取状态行
                //System.out.println(response.getStatusLine());
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String out = EntityUtils.toString(entity, "UTF-8");
                    return out;
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(null != httpClient){
                    httpClient.close();
                }
            } catch (IOException e) {
                System.out.println("httpClient.close()异常");
            }
        }
        return null;
    }

    /**
     * HTTPS访问对象，信任所有证书
     * @return
     */
    public static CloseableHttpClient createSSLInsecureClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }}).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return  HttpClients.createDefault();
    }

    public static String getPostResponse(String url,  Map<String, String> parmMap ) throws IOException {
        {
            String result = "";
            PostMethod post = new PostMethod(url);
            HttpClient
                    client = new HttpClient();
            if(null != parmMap){
                Iterator it = parmMap.entrySet().iterator();
                NameValuePair[] param = new NameValuePair[parmMap.size()];
                int i = 0;
                while (it.hasNext()) {
                    Map.Entry parmEntry = (Map.Entry) it.next();
                    param[i++] = new NameValuePair((String) parmEntry.getKey(), (String) parmEntry.getValue());
                }
                post.setRequestBody(param);
            }
            try {
                int statusCode = client.executeMethod(post);

                if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                    Header locationHeader = post.getResponseHeader("location");
                    String location = "";
                    if (locationHeader != null) {
                        location = locationHeader.getValue();
                        result = getPostResponse(location, parmMap);//用跳转后的页面重新请求
                    }
                } else if (statusCode == HttpStatus.SC_OK) {
                    result = post.getResponseBodyAsString();
                }
            } catch (IOException ex) {
            } finally {
                post.releaseConnection();
            }
            return result;
        }
    }

    //处理http请求  requestUrl为请求地址  requestMethod请求方式，值为"GET"或"POST"
    public static String httpRequest(String requestUrl,String requestMethod,String outputStr){
        StringBuffer buffer=null;
        try{
            URL url=new URL(requestUrl);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(requestMethod);
            conn.connect();
            //往服务器端写内容 也就是发起http请求需要带的参数
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
