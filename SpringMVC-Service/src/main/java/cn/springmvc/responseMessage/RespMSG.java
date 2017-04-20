/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.springmvc.responseMessage;

import cn.springmvc.service.SpringMVCResponse;

/**
 *
 * @author WangW
 */
public class RespMSG {
    public static final SpringMVCResponse OK = new SpringMVCResponse("ok",200);
    
    public static final SpringMVCResponse SERVER_UNKNOWN = new SpringMVCResponse("server error : unknown",500);
    
    public static final SpringMVCResponse PARAMS_TOKEN = new SpringMVCResponse("illegal param : token",401);
    
    

}
