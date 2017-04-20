/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.springmvc.service;

import cn.springmvc.model.User;
import cn.springmvc.log.SpringMVCLogFactory;
import cn.springmvc.model.Params;
import cn.springmvc.responseMessage.RespMSG;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 *
 * @author WangW
 */
@Controller
@RequestMapping("/test")
public class SpringMVCEntrance extends HttpServlet{
    
    public static final Logger log = SpringMVCLogFactory.getHttpLogger();

    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public void sayHello(HttpServletResponse response, Params params) throws IOException, JSONException {
        String token = (String)params.token;
        if(token == null || token.isEmpty()){
            sendResponseMessage(response, RespMSG.PARAMS_TOKEN);
        }
        String challenge = (String)params.challengeValue;
        if(challenge == null || challenge.isEmpty()){
            sendResponseMessage(response, RespMSG.PARAMS_TOKEN);
        }
        JSONObject map = new JSONObject();

        sendResponseMessage(response, new SpringMVCResponse(map.toString()));
    }
    
    private void sendResponseMessage(HttpServletResponse response, SpringMVCResponse mResponse) throws IOException{
        OutputStream out = null;
        try{
            if(mResponse != null ){
                if(log.isInfoEnabled()){
                    log.info("resp" + mResponse.toString());
                }
                response.setStatus(mResponse.code);
                if(mResponse.message != null && !mResponse.message.isEmpty()){
                    response.setHeader("message", mResponse.message);
                }
                out = response.getOutputStream();
                out.write(mResponse.toString().getBytes());
                out.flush();
            }
        }catch(Exception e){
            log.error(e, e);
            response.setStatus(500);
            response.setHeader("message", e.getMessage());
            out = response.getOutputStream();
            if (log.isDebugEnabled()){
                log.debug("resp:status=500,message="+e.getMessage());
            }
            out.write(RespMSG.SERVER_UNKNOWN.toString().getBytes());
            out.flush();
        }finally{
            if(out != null)
                out.close();
        }
    }
}
