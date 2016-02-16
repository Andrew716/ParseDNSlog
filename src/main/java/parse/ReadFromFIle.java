package parse;

import com.google.gson.Gson;
import pojo.Send;
import pojo.Receive;
import pojo.Opcode;
import pojo.RestData;
import pojo.QuestionName;
import pojo.IP;
import pojo.Action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *com.relatedata.evi.ReadFromFile
 *
 * The class extracts data from file, which contains dns logs(dns.log)
 * turns domain names in appropriate view for user and then parses dns logs
 * and stores them in new file. Parsing occurred by method writeDataToPOJOClasses().
 *
 * @author Andrii Koropets
 * @since 2016-02-16
 */

public class ReadFromFIle {

    public static final String PATH_TO_DNS_LOG_FILE = "/home/andrii/IdeaProjects/Test_DNS_LOG.txt";
    public static final String JSON_FILE_PATH = "/home/andrii/IdeaProjects/Test_Parsed_DNS_Log.json";
    public static final String FILE_BAD_IP = "/home/andrii/IP.txt";
    public static final Logger LOGGER = Logger.getLogger("Info logging");
    public static final String REG_EX_DATE = "^([0-9]+\\.[0-9]+\\.[0-9]+)\\s+([0-9]+\\:[0-9]+\\:[0-9]+)\\s+(\\w+)\\s+(\\w+)\\s+(\\w+)\\s+(\\w+)\\s+(\\w+)\\s+([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+)\\s+(\\w+)\\s+(\\w)\\s+\\[([0-9]+)\\s+(\\w+)\\s+(\\w+)\\]\\s+(\\w+)\\s+(\\S+)$";
    public static final String REG_EX_IP = "^\\(\\d+\\)(\\S+)\\(\\d\\)$";

    /**
     * Method readFile() reads data from file, which contains dns logs and
     * saves them in POJO classes for further analysis and parsing
     * @return List. This returns list of data which were in file,
     * which contains dns log.
     */

    public static List readFile() {
        List<UniversalClass> storeDataList = new ArrayList<UniversalClass>();
        UniversalClass universalClass;
        BufferedReader bufferedReader = null;
        try {
            String sCurrentLine;
            bufferedReader = new BufferedReader(new FileReader(PATH_TO_DNS_LOG_FILE));
            LOGGER.info("File exists");
            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile(REG_EX_DATE);
                Matcher matcher = pattern.matcher(sCurrentLine);
                if (matcher.find()) {
                    universalClass = new UniversalClass();
                    universalClass.setDate(matcher.group(1));
                    universalClass.setTime(matcher.group(2));
                    universalClass.setThreadID(matcher.group(3));
                    universalClass.setContext(matcher.group(4));
                    universalClass.setInternalPacketIdentifier(matcher.group(5));
                    universalClass.setProtocol(matcher.group(6));
                    universalClass.setSendReceive(matcher.group(7));
                    universalClass.setRemoteIP(matcher.group(8));
                    universalClass.setXidHex(matcher.group(9));
                    Opcode opcode = new Opcode(matcher.group(11), matcher.group(12), matcher.group(13));
                    universalClass.setOpcode(opcode);
                    universalClass.setQuestionType(matcher.group(14));
                    universalClass.setQuestionName(turnDomainToGoodView(matcher.group(15)));
                    storeDataList.add(universalClass);
                }
            }
            LOGGER.info("Data added successfully to the temp list");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File not found", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException has been generated", e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "PrintStackTrace has been generated", e);
            }
        }
        return storeDataList;
    }

    /**
     * This method does researching and for writing data in file.
     * @throws IOException If file does not exist.
     */

    public static void writeDataToPOJOClasses() throws IOException {
        List<UniversalClass> data = readFile();
        Send send = null;
        Receive receive = null;
        IP ip;
        boolean flagForReceive = false;
        boolean flagForSend = false;
        Set<QuestionName> questionNameSet;
        QuestionName questionName;
        FileWriter fileWriter = new FileWriter(JSON_FILE_PATH);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        System.out.println(data.size());
        Action action = new Action();
        for (int i = 0; i < data.size(); i++) {
            ip = new IP(data.get(i).getRemoteIP());
            int numberForComparedQuestionNumber;
            questionNameSet = new HashSet<QuestionName>();
            questionName = new QuestionName(data.get(i).getQuestionName());
            if (data.get(i).getSendReceive().equals("Snd")) {
                send = new Send();
                send.addRestData(addDataToRestDataClass(data.get(i)));
                questionName.setSend(send);
                questionNameSet.add(questionName);
                flagForSend = true;
            }
            if (data.get(i).getSendReceive().equals("Rcv")) {
                receive = new Receive();
                receive.addRestData(addDataToRestDataClass(data.get(i)));
                questionName.setReceive(receive);
                questionNameSet.add(questionName);
                flagForReceive = true;
            }
            if (i < data.size()-1){
                for (int j = i + 1; j < data.size(); j++) {
                    if (data.get(i).getRemoteIP().equals(data.get(j).getRemoteIP())) {
                        if (data.get(i).getQuestionName().equals(data.get(j).getQuestionName())) {
                            if (data.get(j).getSendReceive().equals("Rcv")) {
                                if (!flagForReceive){
                                    receive = new Receive();
                                    receive.addRestData(addDataToRestDataClass(data.get(j)));
                                    questionName.setReceive(receive);
                                }else {
                                    receive.addRestData(addDataToRestDataClass(data.get(j)));
                                    questionName.setReceive(receive);
                                }
                            }
                            if (data.get(j).getSendReceive().equals("Snd")) {
                                if (!flagForSend){
                                    send = new Send();
                                    send.addRestData(addDataToRestDataClass(data.get(j)));
                                    questionName.setSend(send);
                                }else {
                                    send.addRestData(addDataToRestDataClass(data.get(j)));
                                    questionName.setSend(send);
                                }
                            }
                            System.out.println(questionNameSet.toString());
                            numberForComparedQuestionNumber = j;
                            data.remove(numberForComparedQuestionNumber);
                            j = j - 1;
                            System.out.println("j =    " + j);
                            System.out.println("size:   "+data.size());
                        }else {
                            if (questionNameSet.contains(data.get(j).getQuestionName())){
                                    Iterator iterator = questionNameSet.iterator();
                                    while (iterator.hasNext()){
                                        if (iterator.next().equals(data.get(j))){
                                            if (data.get(j).getSendReceive().equals("Snd")){
                                                send = new Send();
                                                send.addRestData(addDataToRestDataClass(data.get(j)));
                                                ((QuestionName)iterator.next()).setSend(send);
                                            }
                                            if (data.get(j).getSendReceive().equals("Rcv")){
                                                receive = new Receive();
                                                receive.addRestData(addDataToRestDataClass(data.get(j)));
                                                ((QuestionName)iterator.next()).setReceive(receive);
                                            }
                                        }
                                    }
                            }else {
                                questionName = new QuestionName(data.get(j).getQuestionName());
                                if (data.get(j).getSendReceive().equals("Snd")){
                                    send = new Send();
                                    send.addRestData(addDataToRestDataClass(data.get(j)));
                                    questionName.setSend(send);
                                }
                                if (data.get(j).getSendReceive().equals("Rcv")){
                                    receive = new Receive();
                                    receive.addRestData(addDataToRestDataClass(data.get(j)));
                                    questionName.setReceive(receive);
                                }
                                questionNameSet.add(questionName);
                            }
                            numberForComparedQuestionNumber = j;
                            data.remove(numberForComparedQuestionNumber);
                            j = j - 1;
                        }
                    }
                }
            }
            flagForReceive = false;
            flagForSend = false;
            ip.setQuestionNameSet(questionNameSet);
            action.addIP(ip);
            System.out.println(data.size());
        }
        fileWriter.write(writeDataToJSONString(action));
        printWriter.println();
        fileWriter.close();
    }

    /**
     * This method writes data in json string.
     * @param parsedString the parameter which will be turned in json string
     * @return json string which will be added in JSON file
     */

    public static String writeDataToJSONString(Action parsedString){
        Gson gson = new Gson();
        String jsonString = gson.toJson(parsedString);
        return jsonString;
    }

    /**
     * This method writes data from list to auxiliary object of RestData
     * for helping parsing data.
     * @param universalClass stores information which will be parsed and added in
     *                       object of class RestData.
     * @return object which contains data of dns log string
     */

    public static RestData addDataToRestDataClass(UniversalClass universalClass){
        Opcode opcode = new Opcode(universalClass.getOpcode().getFlagsHex(), universalClass.getOpcode().getFlagsCharCode(), universalClass.getOpcode().getRespondedCode());
        RestData restData = new RestData(universalClass.getDate(),universalClass.getTime(),universalClass.getThreadID(),universalClass.getContext(),universalClass.getInternalPacketIdentifier(),universalClass.getProtocol(),universalClass.getXidHex(),opcode, universalClass.getQuestionType());
        return restData;
    }

    /**
     *This method turns bad viewed domain which was in dns.log to good  viewed domain.
     * @param badDomainString The bad viewed domain string
     * @return string of good viewed domain name
     */

    public static String turnDomainToGoodView(String badDomainString) {
        String realDomain = null;
        char[] charsOfIP;
        char[] beforeWWW;
        char[] charsForWWW;
        char[] afterWWW;
        Pattern pattern = Pattern.compile(REG_EX_IP);
        Matcher matcher = pattern.matcher(badDomainString);
        if (matcher.matches()) {
            String str = matcher.group(1).replaceAll("\\(\\d+\\)", ".");
            charsOfIP = str.toCharArray();
            for (int i = 0; i < str.length() - 4; i++) {
                if (charsOfIP[i] == 'w' && charsOfIP[i + 1] == 'w' && charsOfIP[i + 2] == 'w' && charsOfIP[i + 3] == '.') {
                    beforeWWW = Arrays.copyOfRange(charsOfIP, 0, i);
                    charsForWWW = Arrays.copyOfRange(charsOfIP, i, i + 4);
                    afterWWW = Arrays.copyOfRange(charsOfIP, i + 4, charsOfIP.length);
                    List<Character> tempList = new ArrayList<Character>();
                    if (beforeWWW.length != 0 && afterWWW.length != 0 && charsForWWW.length != 0) {
                        for (int p = 0; p < charsForWWW.length; p++) {
                            tempList.add(charsForWWW[p]);
                        }
                        for (int j = 0; j < beforeWWW.length; j++) {
                            tempList.add(beforeWWW[j]);
                        }
                        for (int k = 0; k < afterWWW.length; k++) {
                            tempList.add(afterWWW[k]);
                        }
                        for (int m = 0; m < tempList.size(); m++) {
                            charsOfIP[m] = tempList.get(m);
                        }
                    }
                    realDomain = new String(charsOfIP);
                    break;
                } else {
                    realDomain = str;
                }
            }
        }
        return realDomain;
    }
}
