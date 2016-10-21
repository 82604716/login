package com.siganid.web.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Http������
 *
 * @author Liuxey
 */
public class HttpRequestUtil {

    public static final String REQUEST_TYPE_GET = "get";
    public static final String REQUEST_TYPE_POST = "post";

    public static String request(String uri,Map<String, String> params) {
        String result = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(uri);
            if (params != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : params.keySet()) {
                    if (key != null) {
                        paramList.add(new BasicNameValuePair(key, params
                                .get(key)));
                    }
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                        paramList, "UTF-8");
                httpPost.setEntity(entity);
                httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    InputStream is = httpEntity.getContent();
                    int l;
                    byte[] buff = new byte[9192];
                    while ((l = is.read(buff)) != -1) {
                        result += new String(buff, 0, l, "utf-8");
                    }
                }
            }


        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return result;
    }


//    @SuppressWarnings("deprecation")
//    public static String request(String uri, Map<String, String> params,
//                                 String ip, int port) throws ClientProtocolException, IOException {
//        String result = "";
//        HttpHost proxy = new HttpHost(ip, port);
//        HttpClient httpClient = new DefaultHttpClient();
//        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
//                proxy);
//        HttpResponse httpResponse = null;
//        if (HttpRequestUtil.REQUEST_TYPE_GET.equals("get")) {
//            if (params != null) {
//                if (uri.indexOf("?") != -1) {
//                    uri += "&";
//                } else {
//                    uri += "?";
//                }
//                for (String key : params.keySet()) {
//                    uri += key + "=" + params.get(key) + "&";
//                }
//            }
//            HttpGet httpGet = new HttpGet(uri);
//            httpGet.addHeader("Cookie",
//                    "PHPSESSID=hj8nd6b3nr40noqp4o6isvic53;watch_times=1");
//
//            httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
//                    CookiePolicy.BEST_MATCH);
//            httpResponse = httpClient.execute(httpGet);
//        } else if (HttpRequestUtil.REQUEST_TYPE_POST.equals("post")) {
//            HttpPost httpPost = new HttpPost(uri);
//            if (params != null) {
//                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//                for (String key : params.keySet()) {
//                    if (key != null) {
//                        paramList.add(new BasicNameValuePair(key, params
//                                .get(key)));
//                    }
//                }
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
//                        paramList, "UTF-8");
//                httpPost.setEntity(entity);
//            }
//            httpResponse = httpClient.execute(httpPost);
//        }
//
//        HttpEntity entity = httpResponse.getEntity();
//        if (entity != null) {
//            InputStream is = entity.getContent();
//            int l;
//            byte[] buff = new byte[9192];
//            while ((l = is.read(buff)) != -1) {
//                result += new String(buff, 0, l, "utf-8");
//            }
//        }
//        return result;
//    }

    public interface OnGetLocationURIListener {
        void onGetLocationURI(String uri);

    }

    public static String request(String uri, String cookiesString, OnGetLocationURIListener onGetLocationURIListener)
            throws ClientProtocolException, IOException {
        System.out.println("path:" + uri);
        String result = "";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Cookie", cookiesString);
//		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
//				CookiePolicy.BEST_MATCH);
        RedirectHandler customRedirectHandler = new CustomRedirectHandler(onGetLocationURIListener);
        httpClient.setRedirectHandler(customRedirectHandler);
        httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();
            int l;
            byte[] buff = new byte[9192];
            while ((l = is.read(buff)) != -1) {
                result += new String(buff, 0, l, "utf-8");
            }
        }
        return result;
    }

    static class CustomRedirectHandler extends DefaultRedirectHandler {
        OnGetLocationURIListener onGetLocationURIListener;

        public CustomRedirectHandler(OnGetLocationURIListener onGetLocationURIListener) {
            this.onGetLocationURIListener = onGetLocationURIListener;
        }

        public java.net.URI getLocationURI(HttpResponse response, HttpContext context) {
            // Extract the Location: header and manually convert spaces to %20's
            // Return the corrected URI
            Header locationHeader = response.getFirstHeader("location");
            String url = locationHeader.getValue();
            try {
                url = new String(url.getBytes("ISO-8859-1"), "utf8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (onGetLocationURIListener != null) {
                onGetLocationURIListener.onGetLocationURI(url);
            }
            java.net.URI url1 = null;
            try {
                url1 = new java.net.URI(url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return url1;
        }
    }


}