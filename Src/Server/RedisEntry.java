package mini_redis.Src.Server;

public class RedisEntry {
    private String value;
    private Long expirationTime;
     public RedisEntry(String value, Long expirationTime){
        this.value=value;
        this.expirationTime=expirationTime;
     }
     public String getValue(){
        return value;
     }
     public Long getExpirationTime(){
        return expirationTime;
     }
     public void setValue(String value){
        this.value=value;
     }
     public void setExpirationTime(Long expirationTime){
        this.expirationTime=expirationTime;
     }
     public boolean isExpired(){
        if(expirationTime==null)
            return false;
        return System.currentTimeMillis()>expirationTime;
     }
     public void updateExpirationTime(Long expirationTime){
        this.expirationTime=expirationTime;
     }
     public void removeExpirationTime(){
        this.expirationTime=null;
     }
}
