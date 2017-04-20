/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.springmvc.service;

import cn.springmvc.log.SpringMVCLogFactory;
import cn.springmvc.responseMessage.RespMSG;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author WangW
 */
public class SpringMVCResponseUtil{
    
    public static final Logger log = SpringMVCLogFactory.getHttpLogger();
    
    public static void response(SpringMVCResponse springmvcResponse, HttpServletResponse response) throws IOException{
        OutputStream out = null;
        
        try{
            if(log.isDebugEnabled())
                
                log.debug("resp:"+springmvcResponse.toString());
            
            log.info("-------"+springmvcResponse.code);
//            log.info("-------"+response);
            
            response.setStatus(springmvcResponse.code);
            
            if(springmvcResponse.message!=null)
                
                response.setHeader("message", springmvcResponse.message);
            
            out = response.getOutputStream();
            
            out.write(springmvcResponse.toString().getBytes());
            
            out.flush();
            
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
            
            if(out != null){
                
                out.close();
            }
        }
    }
}
