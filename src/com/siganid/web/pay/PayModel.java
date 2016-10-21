package com.siganid.web.pay;

import com.demo.common.model.PayStuteInfo;
import com.siganid.web.model.AddMoneryModel;
import com.siganid.web.model.ResultVo;
import com.siganid.web.pay.PayQrCodeVo.PayInfoRespon;
import com.siganid.web.util.DateUtil;

/**
 * Created by Administrator on 2016/9/25.
 */
public class PayModel {

    public PayInfoRespon createPay(String userId, String price, String detail) {
        PayStuteModel payStuteModel = new PayStuteModel();
        PayInfoRespon payInfoRespon = payStuteModel.createqrcode(detail, price);
        String qrId = payInfoRespon.getResponse().getQr_id();
        PayStuteInfo payStuteInfo = new PayStuteInfo();
        payStuteInfo.setUserId(userId);
        payStuteInfo.setAddTime(System.currentTimeMillis() + "");
        payStuteInfo.setAddTimeStr(DateUtil.getCurTimeDateString());
        payStuteInfo.setPrice(price);
        payStuteInfo.setQrId(qrId);
        payStuteInfo.setIsPay(false);
        try {
            payStuteInfo.save();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return payInfoRespon;
    }

    public boolean isPaySuccess(String userId) {
        PayStuteModel payStuteModel = new PayStuteModel();
        PayStuteInfo payStuteInfo = PayStuteInfo.dao.findFirst("select * from PayStuteInfo");
        String qrId = payStuteInfo.getQrId();
        boolean payResult = payStuteModel.checkPayResult(qrId);
        if (payResult) {
            AddMoneryModel addMoneryModel = new AddMoneryModel();
            String price = payStuteInfo.getPrice();
            ResultVo resultVo = addMoneryModel.add(userId, price);
            return resultVo.isResult();
        }
        return false;
    }
}
