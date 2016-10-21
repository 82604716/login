package com.siganid.web.model;

import com.demo.common.model.AddMoneryInfo;
import com.demo.common.model.UserInfo;
import com.siganid.web.util.DateUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class AddMoneryModel {


    public ResultVo add(String userId, String monery) {
        LoginModel loginModel = new LoginModel();
        UserInfo userInfo = loginModel.getUserInfoById(userId);
        return add(userInfo, monery);
    }

    /**
     * @param monery   单位为：分
     * @param clinetId
     */
    public ResultVo add(UserInfo userInfo, String monery) {
        ResultVo resultVo = new ResultVo(false);
        try {
            AddMoneryInfo addMoneryInfo = new AddMoneryInfo();
            addMoneryInfo.setMonery(monery);
            addMoneryInfo.setUserId(userInfo.getUserId());
            addMoneryInfo.setAddtime(System.currentTimeMillis() + "");
            addMoneryInfo.setAddtimeStr(DateUtil.getCurTimeDateString());
            addMoneryInfo.save();
            resultVo.setResult(true);
            resultVo.setData(addMoneryInfo);
        } catch (Exception e) {
            e.printStackTrace();
            resultVo.setError(e.getLocalizedMessage());
        }
        return resultVo;
    }

    public ResultVo getAddMoneryList() {
        ResultVo resultVo = new ResultVo(false);
        try {
            List list = AddMoneryInfo.dao.find("select * from addMoneryInfo");
            resultVo.setData(list);
            resultVo.setResult(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultVo.setError(e.toString());
        }
        return resultVo;
    }


}
