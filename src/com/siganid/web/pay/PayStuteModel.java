package com.siganid.web.pay;

import com.google.gson.Gson;
import com.siganid.web.pay.PayQrCodeVo.PayInfoRespon;
import com.siganid.web.pay.PayResult.PayResultVo;
import com.siganid.web.util.HttpRequestUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/10.
 */
public class PayStuteModel {


    String host = "https://open.koudaitong.com/api/oauthentry";

    public static void main(String[] args) {
       // new PayStuteModel().createqrcode("我擦测试一元云购的", "100");
        boolean result=new PayStuteModel().checkPayResult("3977881");
        System.out.println("result:"+result);
    }

    public PayInfoRespon createqrcode(String detail, String price) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("access_token", new TokenModel().getToken());
        paramsMap.put("method", "kdt.pay.qrcode.createqrcode");
        paramsMap.put("qr_type", "QR_TYPE_DYNAMIC");//（二维码只能被支付一次）
        paramsMap.put("qr_source", "INSIDE");
        paramsMap.put("qr_price", price);
        paramsMap.put("qr_name", detail);
        String result = HttpRequestUtil.request(host, paramsMap);
        System.out.println(result);
        Gson gson = new Gson();
        PayInfoRespon payInfoRespon = gson.fromJson(result, PayInfoRespon.class);
       // System.out.println(payInfoRespon.getResponse());
        return payInfoRespon;
    }

    /**
     * 每次交易请求一个新的二维码 会生成一个新的qr_id 每次交易都轮训这个结果就好
     */
    public boolean checkPayResult(String qr_id){
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("access_token", new TokenModel().getToken());
        paramsMap.put("method", "kdt.trades.qr.get");
        paramsMap.put("qr_id", qr_id);
        paramsMap.put("status", "TRADE_RECEIVED");
        String result = HttpRequestUtil.request(host, paramsMap);
        System.out.println(result);
        Gson gson = new Gson();
        PayResultVo payResultVo = gson.fromJson(result, PayResultVo.class);
        Integer integer= payResultVo.getResponse().getTotalResults();
        if (integer!=null&&integer>0){
            return true;
        }
        return false;
    }


}
