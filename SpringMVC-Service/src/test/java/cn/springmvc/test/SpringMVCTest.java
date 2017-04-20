/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.springmvc.test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author WangW
 */
public class SpringMVCTest {
    
    public static Map params = new HashMap();
    
    public static void main(String[] args){
        
        params.put("username", "1234");
        
        Resp resp = HttpRequest1.doPost("/test/login", params);
        
        System.out.print(resp);
        
    }
}
