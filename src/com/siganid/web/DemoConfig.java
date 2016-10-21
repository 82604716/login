package com.siganid.web;

import com.demo.common.model._MappingKit;
import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.siganid.web.controller.*;
import com.siganid.web.controller.interceptor.GlobalActionInterceptor;

/**
 * Created by Administrator on 2016/5/18.
 */
public class DemoConfig extends JFinalConfig {

    public void configConstant(Constants me) {
        me.setDevMode(true);
    }

    public void configRoute(Routes me) {
        me.add("/user", LoginController.class);
        me.add("/monery", PayController.class);
        me.add("/aouth", AouthController.class);
       // me.add("/pay", PayController.class);
        me.add("/thrid", ThirdroadController.class);
    }

    public void configPlugin(Plugins me) {
        C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://jp.siganid.com:3306/Aouth",
                "root", "longkid");
        me.add(cp);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
        _MappingKit.mapping(arp);
        me.add(arp);

    }

    public void configInterceptor(Interceptors interceptors) {
        //这里可以添加token的判断
//        //全局拦截器，对所有请求拦截
//        //添加控制层全局拦截器
        interceptors.addGlobalActionInterceptor(new GlobalActionInterceptor());
//        interceptors.addGlobalActionInterceptor(new ExceptionIntoLogInterceptor());
//        //添加业务层全局拦截器
//interceptors.addGlobalServiceInterceptor(new GlobalServiceInterceptor());
//        interceptors.addGlobalServiceInterceptor(new ExceptionIntoLogInterceptor());
//
//        //兼容老版jfinal写法
//        //interceptors.add(new GlobalActionInterceptor());

    }

    public void configHandler(Handlers me) {
    }

}
