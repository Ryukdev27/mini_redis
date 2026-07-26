package mini_redis.Src.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RedisDatabase{
    private final Map<String, RedisEntry> dataStore;
    public RedisDatabase(){
        dataStore= new HashMap<>();
    }
    public void set(String key,String value){
        dataStore.put(key, new RedisEntry(value, null));
    }
    public String get(String key){
        RedisEntry entry=dataStore.get(key);
        return entry != null ? entry.getValue() : null;
    }
    public boolean delete(String key){
        return dataStore.remove(key)!=null;
    }
    public boolean exists(String key){
        RedisEntry entry=dataStore.get(key);
        return entry!=null && !entry.isExpired();
    }
    public List<String> keys(String pattern) {
        List<String> matchingKeys= new ArrayList<>();
        for(String key: dataStore.keySet()){
            RedisEntry entry=dataStore.get(key);
            if(entry!=null && entry.isExpired()){
                dataStore.remove(key);
                continue;
            }
        if(matches(key, pattern)){
            matchingKeys.add(key);
        }
    }
        return matchingKeys;
    }
        private boolean matches(String key, String pattern){
            String regex=pattern.replace("*", ".*");
            return key.matches(regex);
        }
        public int incr(String key, int value) {
            RedisEntry entry=dataStore.get(key);
            if(entry!=null && entry.isExpired()){
                dataStore.remove(key);
                entry=null;
            }
            String stringValue=entry!=null?entry.getValue():null;
            int intValue=0;
            if(stringValue!=null){
                try{
                    intValue=Integer.parseInt(stringValue);
                }catch(NumberFormatException e){
                    throw new IllegalArgumentException("value is not an integer");
                }
            }
            intValue=intValue+value;
            dataStore.put(key, new RedisEntry(String.valueOf(intValue), null));
            return intValue;
        }
        public RedisEntry getEntry(String key){
            RedisEntry entry=dataStore.get(key);
            if(entry!=null && entry.isExpired()){
                dataStore.remove(key);
                return null;
            }
            return entry;
        }
        public int ttl(String key) {
           RedisEntry entry=dataStore.get(key);
           if(entry==null || entry.isExpired())
               return -2;
              Long expirationTime=entry.getExpirationTime();
            if(expirationTime==null)
                return -1;
            int ttl= (int)((expirationTime-System.currentTimeMillis())/1000);
            if(ttl<0){
                dataStore.remove(key);
                return -2;
            }
            return ttl;
        }
        public boolean expire(String key, int seconds) {
            RedisEntry entry=dataStore.get(key);
            if(entry==null || entry.isExpired())
                return false;
            Long expirationTime=System.currentTimeMillis()+seconds*1000L;
            entry.updateExpirationTime(expirationTime);
                return true;
        }
        public boolean persist(String key) {
            RedisEntry entry=dataStore.get(key);
            if(entry==null)
                return false;
            if(entry.getExpirationTime()==null)
                return false;
            entry.removeExpirationTime();
                return true;
        }
        public void flushDB() {
            dataStore.clear();
        }
        public int dbSize() {
            return dataStore.size();
        }
}
