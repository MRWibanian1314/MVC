/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.springmvc.utils;

/**
 *
 * @author WangW
 */
public class Constant {
    public static final String EMAIL_REGEX = "^[_a-zA-Z0-9\\-\\.]+(\\.)*@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    
    public static final String MOBILE_REGEX =
            "^(((13[0-9]{1})|(17[0-9]{1})|(15[0-9]{1})|(88[0-9]{1})|(83[0-9]{1})|(85[0-9]{1})|(14[0-9]{1})|(18[0-9]{1}))+\\d{8})$";

    public static final String HEADER_X_CLIENT_ADDRESS = "x-client-address";
}
