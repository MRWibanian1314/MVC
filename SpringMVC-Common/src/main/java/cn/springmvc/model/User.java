/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.springmvc.model;

import java.io.Serializable;

/**
 *
 * @author WangW
 */
public class User implements Serializable{
    private String uid;
    
    private String username;
    
    private String salt;
    
    public  User(){}
    
    public User ( String uid,
                  String username,
                  String salt){
        this.uid      = uid;
        this.username = username;
        this.salt     = salt;
    }

    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    
}
