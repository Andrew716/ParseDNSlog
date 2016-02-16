package parse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *com.relatedata.evi.ParseBlackList
 *
 * The class ParseBlackList parses file, which contains blackList of IP
 * to suitable view and statement for working.
 *
 * @author Andrii Koropets
 * @since 2016-02-16
 */

public class ParseBlackList {

    public static final String PATH_TO_BLACK_LIST_FILE = "/home/andrii/IdeaProjects/ParsingDNS_Server_File/src/main/resources/full_blacklist_database.txt";
    public static final String PATH_TO_PARSED_BLACK_LIST_FILE = "/home/andrii/IdeaProjects/ParsingDNS_Server_File/src/main/resources/parsed_blackList.txt";
    public static final String PATH_TO_FOUND_BLACK_IP = "/home/andrii/IdeaProjects/ParsingDNS_Server_File/src/main/resources/found_black_ip.txt";
    public static final Logger LOGGER = Logger.getLogger("Info logging");
    public static final String REG_EX_FOR_IP = "([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+).";

    /**
     * This method parses file, which contains blackList of ip and information about them
     * and adds ip to the new file, which contains only ip's.
     */

    public static void parseFile() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(PATH_TO_BLACK_LIST_FILE));
            FileWriter fileWriterParsedBlackList = new FileWriter(PATH_TO_PARSED_BLACK_LIST_FILE);
            PrintWriter printWriterParsedBlackList = new PrintWriter(fileWriterParsedBlackList);
            String currentString;
            while ((currentString = bufferedReader.readLine()) != null){
                Pattern pattern = Pattern.compile(REG_EX_FOR_IP);
                Matcher matcher = pattern.matcher(currentString);
                if (matcher.find()) {
                    fileWriterParsedBlackList.write(matcher.group(1));
                    fileWriterParsedBlackList.flush();
                    printWriterParsedBlackList.println();
                }
            }
            fileWriterParsedBlackList.close();
            LOGGER.info("File exists");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "FileNotFoundException has been generated!", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException has been generated!", e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *This method compare one given ip with list of ip's in blackList of ip.
     * @param ip
     */

    public static void compareIPWithBlackList(String ip){
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(PATH_TO_PARSED_BLACK_LIST_FILE));
            FileWriter fileWriter = new FileWriter(PATH_TO_FOUND_BLACK_IP);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null){
                if (currentLine.equals(ip)){
                    fileWriter.write(currentLine);
                    fileWriter.flush();
                    printWriter.println();
                }
            }
            LOGGER.info("File exists");
            fileWriter.close();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "FileNotFoundException has been generated", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException has been generated", e);
        }finally {
            try {
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "IOException has been generated", e);
            }
        }
    }
}

