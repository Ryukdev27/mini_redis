package mini_redis.Src.Server;

import java.util.List;

public class RespEncoder {
    public static String simpleString(String value){
        return "+" + value + "\r\n";
    }
    public static String error(String value){
        return "-ERR" + value + "\r\n";
    }
    public static String integer(int value){
        return ":" + value + "\r\n";
    }
    public static String bulkString(String value){
        if(value == null)
            return "$-1\r\n";
        return "$" + value.length()+ "\r\n"+ value + "\r\n";
    }
    public static String array(List<String> keys) {
        if(keys== null) return "-1\r\n";
        StringBuilder sb= new StringBuilder();
        sb.append("*").append(keys.size()).append("\r\n");
        for(String key:keys){
            sb.append(bulkString(key));
        }
        return sb.toString();
    }
}
