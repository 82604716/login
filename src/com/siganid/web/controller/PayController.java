package com.siganid.web.controller;

import com.demo.common.model.UserInfo;
import com.siganid.web.model.LoginModel;
import com.siganid.web.model.ResultVo;
import com.siganid.web.pay.PayModel;
import com.siganid.web.pay.PayQrCodeVo.PayInfoRespon;

/**
 * Created by Administrator on 2016/9/25.
 */
public class PayController extends ResultController {
    //http://localhost:8080/monery/pay?token=B6E8CC8F183446EFC0CA795CE8DA904F&price=112&detail=%E5%8F%B7%E7%9A%84
    public void pay() {
        PayModel payModel = new PayModel();
        PayInfoRespon payInfoRespon = null;
        ResultVo resultVo = new ResultVo(false);
        try {
            UserInfo userInfo = getAttr(LoginModel.USERINFO);
            payInfoRespon = payModel.createPay(userInfo.getUserId(), getPara("price"), getPara("detail"));
            resultVo.setResult(true);
            resultVo.setData(payInfoRespon);
        } catch (Exception e) {
            e.printStackTrace();
            resultVo.setError(e.getLocalizedMessage());
        }
        renderJson(resultVo);
    }

}
