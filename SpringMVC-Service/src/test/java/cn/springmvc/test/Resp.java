/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.springmvc.test;

/**
 *
 * @author hetq
 */
public class Resp {
    public int code;
    public String message;
    public String body;

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("code:"+code).append(",message:"+message).append(",body:"+body);
        return sb.toString();
    }

}
