package mini_redis.Src;

import java.io.*;

public class RespReader {

    private final BufferedReader reader;


    public RespReader(BufferedReader reader){
        this.reader = reader;
    }


    public String readResponse() throws IOException {

        String firstLine = reader.readLine();

        if(firstLine == null)
            return null;


        char type = firstLine.charAt(0);


        switch(type) {


            case '+':

                return firstLine.substring(1);



            case '-':

                return "ERROR: " + firstLine.substring(1);



            case ':':

                return firstLine.substring(1);



            case '$':

                int length =
                        Integer.parseInt(
                                firstLine.substring(1)
                        );


                if(length == -1)
                    return "(nil)";


                return reader.readLine();




            case '*':

                int count =
                        Integer.parseInt(
                                firstLine.substring(1)
                        );


                if(count == -1)
                    return "(nil)";


                StringBuilder array =
                        new StringBuilder();


                for(int i = 1; i <= count; i++) {

                    array.append(i)
                         .append(") ")
                         .append(readResponse())
                         .append("\n");
                }


                return array.toString();




            default:

                return firstLine;
        }
    }
}
