package parse;


/**
 * com.relatedata.evi.MainClass
 *
 * The ParseDNSlog program parse file with dns.log to JSON file
 *
 * @author Andrii Koropets
 * @since 2016-02-16
 */

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrii on 09.02.16.
 */
public class MainClass {

    public static final String REGEX_DOMEN = "^\\([1-9]+\\)\\(0\\)$";
    public static final Logger LOGGER = Logger.getLogger("Info logging");
    public static final String jsonFilePath = "/home/andrii/FileJSON.json";

    public static void main(String [] args){
       // ReadFromFIle.readFile();
       // ParseBlackList.parseFile();
        //ParseBlackList.compareIPWithBlackList("222.77.227.238");
        //ReadFromFIle.readFile();
        try {
            ReadFromFIle.writeDataToPOJOClasses();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkingDomenName(String string){
        boolean flag = false;
        Pattern pattern = Pattern.compile(REGEX_DOMEN);
        Matcher matcher = pattern.matcher(string);
        flag = matcher.matches();
        return flag;
    }
}
