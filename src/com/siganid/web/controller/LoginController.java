package com.siganid.web.controller;

import com.siganid.web.model.LoginModel;
import com.siganid.web.model.ResultVo;

/**
 * Created by Administrator on 2016/5/18.
 */

public class LoginController extends ResultController {

    public void login() {
        ResultVo loginVo = new LoginModel().login(getPara("userId"), getPara("password"));
        renderJson(loginVo);
    }

    public void regist() {
        ResultVo loginVo = new LoginModel().regist(getPara("userId"), getPara("password"));
        renderJson(loginVo);
    }

    public void checkToken(String uid,String token){

    }
}
