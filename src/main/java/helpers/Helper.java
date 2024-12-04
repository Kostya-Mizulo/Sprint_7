package helpers;

public class Helper {
    public static String uniqueizeString(String initialString){
         return initialString + "_" + System.currentTimeMillis();
    }
}