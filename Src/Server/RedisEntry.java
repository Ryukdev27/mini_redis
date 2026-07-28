package mini_redis.Src.Server;
import java.util.*;
public class RedisEntry {
    private RedisType type;
    private Object value;
    private Long expirationTime;

    public RedisEntry(RedisType type,Object value, Long expirationTime){
        this.type = type;
        this.value = value;
        this.expirationTime = expirationTime;
    }
    public RedisType getType(){
        return type;
    }
    public Object getValue(){
        return value;
    }
    public String getString(){
        if(type != RedisType.STRING) throw new IllegalStateException("Not a String");
        return (String)value;
    }
    public void setString(String value){
        this.type = RedisType.STRING;
        this.value = value;
    }
    @SuppressWarnings(value = { "unchecked" })
    public LinkedList<String> getList(){
        if(type != RedisType.LIST)
            throw new IllegalStateException("Not a List");
        return (LinkedList<String>)value;
    }
    public void setList(LinkedList<String> list){
        this.type = RedisType.LIST;
        this.value = list;
    }
    public Long getExpirationTime(){
        return expirationTime;
    }
    public void updateExpirationTime(Long expirationTime){
        this.expirationTime = expirationTime;
    }
    public void removeExpirationTime(){
        this.expirationTime = null;
    }
    public boolean isExpired(){
        if(expirationTime == null)
            return false;
        return System.currentTimeMillis() > expirationTime;
    }
}
