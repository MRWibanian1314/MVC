/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.springmvc.service;

/**
 *
 * @author WangW
 */
public class SpringMVCResponse {
//response code in header

    public int code = 500;
    //response message in header
    public String message;
    public String content;

    public SpringMVCResponse() {
    }

    public SpringMVCResponse(String content) {
        this.code = 200;
        this.content = content;
    }

    public SpringMVCResponse(String message, int code) {
        this.message = message;
        this.code = code;
        if (code == 200) {
            StringBuffer sb = new StringBuffer();
            sb.append("{\"status\":").append(code)
                    .append(",\"result\":\"").append(message).append("\"}");
            this.content = sb.toString();
        }
    }

    public String toString() {
        if (code == 200) {
            return content;
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append("{\"status\":").append(code)
                    .append(",\"result\":\"").append(message).append("\"}");
            return sb.toString();
        }
    }
}
