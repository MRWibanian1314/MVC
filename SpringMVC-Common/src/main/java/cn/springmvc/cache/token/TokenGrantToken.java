package cn.springmvc.cache.token;

/**
 *
 * @author WangW
 */
public class TokenGrantToken {
    public String key;
    public String uid;
    public String username;//login name
    public String truename;
    public String roles[];
    public String sn;
    public String eid;
    public int type;
    //version: generate time
    //expire time = version + timeout
    public int timeout;
    public long version;

}
