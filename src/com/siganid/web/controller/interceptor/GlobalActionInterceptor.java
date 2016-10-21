package com.siganid.web.controller.interceptor;

import com.demo.common.model.UserInfo;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.siganid.web.model.LoginModel;
import com.siganid.web.model.ResultVo;

/**
 * Created by Administrator on 2016/9/24.
 */
public class GlobalActionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        Controller controller = invocation.getController();
        controller.setAttr(LoginModel.USERINFO,"测试的userInfo");
        System.out.println("Action拦截"+invocation.getMethodName());
        UserInfo userInfo = null;
        if (invocation.getMethodName().equals("login")||invocation.getMethodName().equals("regist")){
            invocation.invoke();
        }else {
            userInfo = new LoginModel().getUserInfoFromToken(controller.getPara("token"));
            if (userInfo == null){
                ResultVo resultVo  = new ResultVo(false);
                resultVo.setError("token is invid");
                controller.renderJson(resultVo);
            }else {
                controller.setAttr(LoginModel.USERINFO,userInfo);
                invocation.invoke();
            }
        }

    }
}
