/*
 * 同步的html
 */
package com.linkin.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class SynHtmlUtil {
    private static HttpClient httpClient;
    
    
    public static String get(String url) {
        return get(url, "UTF_8");
    }

    public static String get(String url, String charset) {
        if (httpClient == null) {
            httpClient = createHttpClient();
        }
        String con = null;

        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = httpClient.execute(get);
            con = EntityUtils.toString(response.getEntity(), charset);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    
    public static HttpClient createHttpClient() {

        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params,
                HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schReg.register(new Scheme("https",
                SSLSocketFactory.getSocketFactory(), 443));

        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
                params, schReg);

        return new DefaultHttpClient(conMgr, params);
    }
}
