package com.siganid.web.controller;

import com.jfinal.core.Controller;
import com.siganid.web.model.ResultVo;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ResultController extends Controller {
    public void renderJson(boolean result,Object object) {
        ResultVo resultVo = new ResultVo(result,object);
        super.renderJson(resultVo);
    }
}
