package cn.springmvc.cache.token;

/**
 *
 * @author WangW
 */
public class ServiceToken {
    public String key;
    public String service;
    //version: generate time
    //expire time = version + timeout
    public int timeout;
    public long version;
    public String challengeValue;//challenge
    public TokenGrantToken tgt;

    public boolean isAdmin() {//管理员
        if (tgt == null || tgt.roles == null) {
            return false;
        }
        for (String role : tgt.roles) {
            if (role.contains("&")) {
                int and = role.indexOf("&");
                role = role.substring(0, and);
            }
            if ("admin".equals(role)) {
                return true;
            }
        }
        return false;
    }
}
