package mini_redis.Src.Server;

public class RespEncoder {
   public static String simpleString(String str){
       return "+"+str+"\r\n";
   }
   public static String error(String str){
    return "-"+str+"\r\n";
   }
   public static String bulkString(String str){
        if(str == null)
        return "$-1\r\n";
        return "$" + str.length() + "\r\n"+ str + "\r\n";
   }
   public static String integer(long value){
    return ":"+value+"\r\n";
   }
}
