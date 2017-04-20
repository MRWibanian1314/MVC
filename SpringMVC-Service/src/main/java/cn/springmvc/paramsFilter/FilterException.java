/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.springmvc.paramsFilter;

/**
 *
 * @author WangW
 */
public class FilterException extends Exception {

    public int errCode = 500;

    public FilterException(Throwable cause) {
        super(cause);
    }

    public FilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterException(String message, Throwable cause, int code) {
        super(message, cause);
        this.errCode = code;
    }

    public FilterException(String message) {
        super(message);
    }

    public FilterException(String message, int code) {
        super(message);
        this.errCode = code;
    }

    public FilterException() {
    }

    public FilterException(int code) {
        this.errCode = code;
    }

    public int getCode() {
        return errCode;
    }
}
