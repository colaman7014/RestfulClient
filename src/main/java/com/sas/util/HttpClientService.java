package com.sas.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

public class HttpClientService {

    private static final int SUCCESS_CODE = 200;

    /**
     * 發送GET請求
     * @param url
     * @param nameValuePairList
     * @return 字符串
     * @throws Exception
     */
    public static String sendGet(String url, List<NameValuePair> nameValuePairList) throws Exception{
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            /**
             * 創建HttpClient對象
             */
            CredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(
                    AuthScope.ANY,
                    new UsernamePasswordCredentials("sas.ec", "")
            );
            client = HttpClientBuilder.create()
                    .setDefaultCredentialsProvider(provider)
                    .build();
            /**
             * 創建URIBuilder
             */
            URIBuilder uriBuilder = new URIBuilder(url);
            /**
             * 設置參數
             */
            if (nameValuePairList != null) {
                uriBuilder.addParameters(nameValuePairList);
            }
            /**
             * 創建HttpGet
             */

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            /**
             * 設置請求頭部編碼
             */
            httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            /**
             * 設置返回編碼
             */
            httpGet.setHeader(new BasicHeader("Accept", "application/json"));
            /**
             * 請求服務
             */
            response = client.execute(httpGet);
            /**
             * 獲取響應嗎
             */
            int statusCode = response.getStatusLine().getStatusCode();

            if (SUCCESS_CODE == statusCode){
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity,"UTF-8");
                return result;
            }else{
                System.out.println("HttpClientService-statusCode: "+ statusCode);
            }
        }catch (Exception e){
            System.out.println("HttpClientService-Exception: "+ e);
        } finally {
            response.close();
            client.close();
        }
        return null;
    }

    /**
     * 發送POST請求
     * @param url
     * @param nameValuePairList
     * @return 字符串
     * @throws Exception
     */
    public static String sendPost(String url, List<NameValuePair> nameValuePairList) throws Exception{
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        try{
            client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);

            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            post.setEntity(entity);
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));

            response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                return result;
            }else{
                System.out.println("HttpClientService-statusCode："+ statusCode);
            }
        }catch (Exception e){
            System.out.println("HttpClientService-Exception："+ e);
        }finally {
            response.close();
            client.close();
        }
        return null;
    }

    /**
     * 組織請求參數{參數名和參數值下標保持一致}
     * @param params    參數名數組
     * @param values    參數值數組
     * @return 參數對象
     */
    public static List<NameValuePair> getParams(Object[] params, Object[] values){
        /**
         * 校驗參數合法性
         */
        boolean flag = params.length>0 && values.length>0 && params.length == values.length;
        if (flag) {
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            for(int i=0; i<params.length; i++) {
                nameValuePairList.add(new BasicNameValuePair(params[i].toString(),values[i].toString()));
            }
            return nameValuePairList;
        } else {
            System.out.println("HttpClientService-errorMsg："+ "請求參數為空/參數長度不一致");
        }
        return null;
    }


    public static String sendHttpsGet(String url, List<NameValuePair> nameValuePairList) throws Exception{
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        try{
            PoolingHttpClientConnectionManager connManager = ConnectionManagerBuilder();
            client = HttpClients.custom().setConnectionManager(connManager).build();

            URIBuilder uriBuilder = new URIBuilder(url);
            if (nameValuePairList != null) {
                uriBuilder.addParameters(nameValuePairList);
            }
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));

            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (SUCCESS_CODE == statusCode){
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                return result;
            } else {
                System.out.println("HttpClientService-errorCode: "+ statusCode+", errorMsg GET請求失敗！");
            }
        }catch (Exception e){
            System.out.println("HttpClientService-Exception: "+ e);
        } finally {
            response.close();
            client.close();
        }
        return null;
    }

    public static String sendHttpsPost(String url, List<NameValuePair> nameValuePairList) throws Exception{
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        try{
            PoolingHttpClientConnectionManager connManager = ConnectionManagerBuilder();
            client = HttpClients.custom().setConnectionManager(connManager).build();

            HttpPost post = new HttpPost(url);
            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            post.setEntity(entity);
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));

            response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                return result;
            }else{
                System.out.println("HttpClientService-statusCode："+ statusCode);
            }
        }catch (Exception e){
            System.out.println("HttpClientService-Exception："+ e);
        }finally {
            response.close();
            client.close();
        }
        return null;
    }


    public static PoolingHttpClientConnectionManager ConnectionManagerBuilder() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        // 實現一個X509TrustManager接口，用於繞過驗證，不用修改裡面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sslContext.init(null, new TrustManager[] { trustManager }, null);

        // 設置協議http和https對應的處理socket鏈接工廠的對象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext))
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        return connManager;
    }



    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        // 實現一個X509TrustManager接口，用於繞過驗證，不用修改裡面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sslContext.init(null, new TrustManager[] { trustManager }, null);
        return sslContext;
    }


    public static void main(String[] args) {
        String url = "https://ixyzero.com/blog/awk_sed.txt";
        // 參數值
        Object [] params = new Object[]{"param1","param2"};
        // 參數名
        Object [] values = new Object[]{"value1","value2"};
        // 獲取參數對象
        List<NameValuePair> paramsList = HttpClientService.getParams(params, values);

        // 發送get
        String result = null;
        try {
            result = sendGet(url, paramsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("GET返回信息：\n" + result);

        // 發送get
        result = null;
        try {
            // result = sendHttpsGet(url, paramsList);
            result = sendHttpsGet(url, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[HTTPS]GET返回信息：\n" + result);

        // 發送post
        url = "https://httpbin.org/post";
        result = null;
        try {
            result = sendPost(url, paramsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("POST返回信息：\n" + result);

        // 發送post
        url = "https://ixyzero.com/blog/wp-comments-post.php";
        result = null;
        try {
            result = sendHttpsPost(url, paramsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[HTTPS]POST返回信息：\n" + result);

    }
}
