package cn.springmvc.cache.token;


import cn.springmvc.common.MD5;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

/**
 *
 * @author WangW
 */
public class TokenManager {
    //key=TokenGrantToken.key
    private static Hashtable<String,TokenGrantToken> tgtCache =
            new Hashtable<String, TokenGrantToken>();
    //key=ServiceTicket.key
    private static Hashtable<String,ServiceToken> stCache =
            new Hashtable<String, ServiceToken>();

    private static Random random = new Random(System.currentTimeMillis());

    public static void put(TokenGrantToken tgt){
        tgtCache.put(tgt.key, tgt);
    }

    public static void put(ServiceToken st){
        stCache.put(st.key, st);
    }

    public static TokenGrantToken getTGT(String key){
        return tgtCache.get(key);
    }

    public static ServiceToken getST(String key){
        return stCache.get(key);
    }

    public static String getSsid(String uid, String username, String eid, int timeout){
        TokenGrantToken tgt = new TokenGrantToken();
        tgt.uid = uid;
        tgt.username = username;
        tgt.eid = eid;
        tgt.timeout = timeout;
        tgt.key = MD5.digestToHex(("" + random.nextLong() + uid + eid).getBytes());
        put(tgt);
        return tgt.key;
    }

    public static String getUidByUsername(String username){
        if(username == null)
            return null;
        for(TokenGrantToken tgt:tgtCache.values()){
            if(username.equals(tgt.username))
                return tgt.uid;
        }
        return null;
    }

    public static TokenGrantToken getTGTByUsername(String username){
        if(username == null)
            return null;
        for(TokenGrantToken tgt:tgtCache.values()){
            if(username.equals(tgt.username))
                return tgt;
        }
        return null;
    }

    public static TokenGrantToken createTGT(String uid, int timeout){
        TokenGrantToken tgt = new TokenGrantToken();
        tgt.uid = uid;
        tgt.version = System.currentTimeMillis()/1000;
        tgt.timeout = timeout;
        tgt.key = MD5.digestToHex(("" + random.nextLong() + uid ).getBytes());
        put(tgt);
        return tgt;
    }

    public static ServiceToken createST(String uid, String service, int timeout){
        ServiceToken st = new ServiceToken();
        st.service = service;
        st.version = System.currentTimeMillis()/1000;
        st.timeout = timeout;
        long l = random.nextLong();
        if(l<0) l = -l;
        st.challengeValue = l+"";
        st.key = MD5.digestToHex((service + st.challengeValue + uid).getBytes());
        put(st);
        return st;
    }

    public static void removeTGT(String key){
        if(tgtCache.containsKey(key)){
            TokenGrantToken tgt = tgtCache.remove(key);
            Iterator<Entry<String,ServiceToken>> it = stCache.entrySet().iterator();
            while(it.hasNext()){
                if(it.next().getValue().tgt == tgt){
                    it.remove();
                }
            }
        }
    }

    public static void removeST(String key){
        if(stCache.containsKey(key)){
            ServiceToken st = stCache.remove(key);
        }
    }
}
