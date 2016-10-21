package com.siganid.web.pay;

import com.google.gson.Gson;
import com.siganid.web.pay.TokenVo.ReflashTokenRespon;
import com.siganid.web.util.HttpDownloader;
import com.siganid.web.util.HttpRequestUtil;
import com.sun.deploy.net.URLEncoder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2016/6/10.
 */
public class TokenModel {

    static String client_id = "cded940cceacbe07e0";
    static String client_secret = "72e27f8d575c664bafc31820b1ff38e0";

    static String accessToken = "";

    public static void main(String[] args) {
        //new TokenModel().getTokenBYReflashToken("528e5ca33d580f56731840c22cd230ff12f714a9");

        //new TokenModel().getToken();
        // new TokenModel().getTokenById();
        //new TokenModel().storeReflashToken("123");
       //new TokenModel().getCode();
       // new TokenModel().getToken("931fbed8c21e303d90d586bde4bd12ef");
        String token = new TokenModel().getToken();
        System.out.println("token:"+token);
    }

    public String getToken() {
        if (accessToken != null && accessToken.length() > 0) {
            return accessToken;
        }
        String reflashToken = loadReflashToken();
        ReflashTokenRespon reflashTokenRespon = getTokenBYReflashToken(reflashToken);
        String accessToken = reflashTokenRespon.getAccess_token();
        if (accessToken != null && accessToken.length() > 0) {
            TokenModel.accessToken = accessToken;
            storeReflashToken(reflashTokenRespon.getRefresh_token());
            return accessToken;
        }
        return "";

    }

    private String loadReflashToken() {
        Properties pro = new Properties();
        FileInputStream in = null;
        String result = "";
        try {
            String path = this.getClass().getClassLoader().getResource("/").getPath();
            path= path.substring(1,path.length());
            System.out.println(path);
            in = new FileInputStream(path+"a.properties");
            pro.load(in);
            result = (String) pro.get("ReflashToken");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void storeReflashToken(String reflashToken) {
        try {
            Properties prop = new Properties();
            String path = this.getClass().getClassLoader().getResource("/").getPath();
            path= path.substring(1,path.length());
            System.out.println(path);
            FileOutputStream oFile = new FileOutputStream(path+"a.properties", false);//true表示追加打开
            prop.clear();
            prop.setProperty("ReflashToken", reflashToken);
            prop.store(oFile, "The New properties file");
            oFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getTokenById() {
        String host = "https://open.koudaitong.com/oauth/token";
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("grant_type", "password");
        paramsMap.put("client_id", client_id);
        paramsMap.put("client_secret", client_secret);
        paramsMap.put("username", "15899979786");
        paramsMap.put("password", "123chen.!");
        String result = HttpRequestUtil.request(host, paramsMap);
        System.out.println(result);
        return result;
    }

    /**
     * client_id	String	是	testclient		分配的调用Oauth的应用端ID
     * response_type	String	是	token		返回的令牌类型（固定为 “token”）
     * state	String	是	teststate		用于保持请求和回调的状态，在回调时，会回传该参数。开发者可以用这个参数验证请求有效性，也可以记录用户请求授权页前的位置。可防止CSRF攻击
     * redirect_uri	String	否	http://your.com/callback		授权回调地址
     * scope	String	否	testcope		授权权限。以空格分隔的权限列表，若不传递此参数，代表请求用户的默认权限
     */


    public String getCode() {
        String host = "https://open.koudaitong.com/oauth/authorize";
        StringBuilder params = new StringBuilder();
        params.append("?response_type=").append("code");
        params.append("&client_id=").append(client_id);
        params.append("&state=").append("teststate");
        try {
            params.append("&redirect_uri=").append(URLEncoder.encode("http://siganid.com", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = host + params.toString();
        System.out.println(url);
        String result = HttpDownloader.download(url);
        System.out.println(result);
        return result;
    }

    public String getToken(String code) {
        String host = "https://open.koudaitong.com/oauth/token";
        StringBuilder params = new StringBuilder();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("grant_type", "authorization_code");
        paramsMap.put("client_id", client_id);
        paramsMap.put("client_secret", client_secret);
        paramsMap.put("code", code);

        try {
            params.append("&redirect_uri=").append(URLEncoder.encode("http://siganid.com", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = host + params.toString();
        System.out.println(url);
        String result = HttpRequestUtil.request(url, null);
        System.out.println(result);
        return result;
    }

    public ReflashTokenRespon getTokenBYReflashToken(String reflashToken) {
        String host = "https://open.koudaitong.com/oauth/token";
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("grant_type", "refresh_token");
        paramsMap.put("client_id", client_id);
        paramsMap.put("client_secret", client_secret);
        paramsMap.put("refresh_token", reflashToken);
        String result = HttpRequestUtil.request(host, paramsMap);
        System.out.println(result);
        Gson jackson = new Gson();
        ReflashTokenRespon reflashTokenRespon = jackson.fromJson(result, ReflashTokenRespon.class);
        System.out.println("reflashTokenRespon:" + reflashTokenRespon.getAccess_token());
        return reflashTokenRespon;
    }


    /** 用它的 测试页面获得的 地址是 https://open.koudaitong.com/doc/oauth?granttype=authorizationCode
     * {"access_token":"eda3b65dd39261dec69fb04b17de60e60693c612","expires_in":604800,"token_type":"Bearer","scope":"item trade trade_virtual user utility shop item_category logistics pay_qrcode coupon present_advanced item_category_advanced",
     * "refresh_token":"7b64b1e151d4c5b1dde9ae4a9c427120d1150369"}
     *
     * 获取code地址：
     * https://open.koudaitong.com/oauth/authorize?response_type=code&client_id=cded940cceacbe07e0&state=teststate&redirect_uri=http%3A%2F%2Fsiganid.com
     */


}
