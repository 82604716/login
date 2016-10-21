package com.siganid.web.model;

import com.demo.common.model.UserInfo;
import com.siganid.web.util.MD5;

/**
 * Created by Administrator on 2016/6/22.
 */
public class LoginModel {
    public static String USERINFO ="user_info";
    public ResultVo login(String userId, String password) {
        ResultVo resultVo = new ResultVo(false, null);
        UserInfo userInfo = getUserInfoById(userId);
        if (userInfo != null &&
                getSavePassword(password).equals(userInfo.getPassword())) {
            resultVo.setResult(true);
            resultVo.setData(userInfo);
        }
        return resultVo;
    }

    public UserInfo getUserInfoById(String userId) {
        UserInfo userInfo = UserInfo.dao.findFirst("select * from userInfo where user_id = '" + userId + "'");
        return userInfo;
    }

    public ResultVo regist(String userId, String password) {
        ResultVo result = new ResultVo(false);
        UserInfo userInfo = getUserInfoById(userId);
        if (userInfo != null) {
            result.setError("this userId had regist");
            return result;
        }
        userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setPassword(getSavePassword(password));
        userInfo.setToken(createToken(userId, password));
        userInfo.setUserName("");
        try {
            userInfo.save();
            result.setResult(true);
            result.setData(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String createToken(String userId, String password) {
        String temp = userId + "siganid" + System.currentTimeMillis() + password;
        String token = MD5.getMD5ofStr(temp);
        return token;
    }

    private String getSavePassword(String password) {
        String savePassword = MD5.getMD5ofStr(password + "siganid");
        return savePassword;
    }

    public UserInfo getUserInfoFromToken(String token) {
        UserInfo userInfo = UserInfo.dao.findFirst("select * from userInfo where token = '" + token + "'");
        return userInfo;
    }

    public static void main(String[] args) {
        //ResultVo loginVo = new LoginModel().regiest("123", "123");
        //System.out.println(loginVo.getToken());
    }

}
