package parse;

import com.google.gson.Gson;
import pojo.QuestionName;
import pojo.Receive;
import pojo.IP;
import pojo.Opcode;
import pojo.RestData;
import pojo.Send;
import pojo.ParsedString;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrii on 09.02.16.
 */

public class ReadFromFIle {

    public static final String PATH_TO_DNS_LOG_FILE = "/home/andrii/IdeaProjects/Test_DNS_LOG.txt";
    public static final String JSON_FILE_PATH = "/home/andrii/IdeaProjects/Test_Parsed_DNS_Log.json";
    public static final String FILE_BAD_IP = "/home/andrii/IP.txt";
    public static final Logger LOGGER = Logger.getLogger("Info logging");
    public static final String REG_EX_DATE = "^([0-9]+\\.[0-9]+\\.[0-9]+)\\s+([0-9]+\\:[0-9]+\\:[0-9]+)\\s+(\\w+)\\s+(\\w+)\\s+(\\w+)\\s+(\\w+)\\s+(\\w+)\\s+([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+)\\s+(\\w+)\\s+(\\w)\\s+\\[([0-9]+)\\s+(\\w+)\\s+(\\w+)\\]\\s+(\\w+)\\s+(\\S+)$";
    public static final String REG_EX_IP = "^\\(\\d+\\)(\\S+)\\(\\d\\)$";

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

    public static void writeDataToPOJOClasses() throws IOException {
        List<UniversalClass> data = readFile();
        Send send = null;
        Receive receive = null;
        IP ip;
        Set<QuestionName> questionNameSet;
        QuestionName questionName;
        boolean flagForEquality;
        FileWriter fileWriter = new FileWriter(JSON_FILE_PATH);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        ParsedString parsedString = new ParsedString();
        System.out.println(data.size());
        for (int i = 0; i < data.size() - 1; i++) {
            ip = new IP(data.get(i).getRemoteIP());
            boolean flagForSendEqual = false;
            boolean flagForReceiveEqual = false;
            boolean flagForSendNotEqual = false;
            boolean flagForReceiveNotEqual = false;
            int numberForComparedQuestionNumber;
            questionNameSet = new HashSet<QuestionName>();
            questionName = new QuestionName(data.get(i).getQuestionName());
            if (data.get(i).getSendReceive().equals("Snd")) {
                send = new Send();
                send.addRestData(addDataToRestDataClass(data.get(i)));
                questionName.setSend(send);
                questionNameSet.add(questionName);
            }
            if (data.get(i).getSendReceive().equals("Rcv")) {
                receive = new Receive();
                receive.addRestData(addDataToRestDataClass(data.get(i)));
                questionName.setReceive(receive);
                questionNameSet.add(questionName);
            }
            for (int j = 1; j < data.size(); j++) {
                if (data.get(i).getRemoteIP().equals(data.get(j).getRemoteIP())) {
                    if (data.get(i).getQuestionName().equals(data.get(j).getQuestionName())) {
                        if (data.get(j).getSendReceive().equals("Rcv")) {
                            receive = new Receive();
                            questionName = new QuestionName(data.get(j).getQuestionName());
                            if (questionNameSet.contains(questionName)) {
                                Iterator iterator = questionNameSet.iterator();
                                while (iterator.hasNext()) {
                                    if (iterator.next().equals(questionName)) {
                                        receive.addRestData(addDataToRestDataClass(data.get(j)));
                                        ((QuestionName)iterator.next()).setReceive(receive);
                                    }
                                }
                            } else {
                                receive.addRestData(addDataToRestDataClass(data.get(j)));
                                questionName.setReceive(receive);
                                questionNameSet.add(questionName);
                            }
                        }
                        if (data.get(j).getSendReceive().equals("Snd")) {
                            send = new Send();
                            questionName = new QuestionName(data.get(j).getQuestionName());
                            if (questionNameSet.contains(questionName)) {
                                Iterator iterator = questionNameSet.iterator();
                                while (iterator.hasNext()) {
                                    if (iterator.next().equals(questionName)) {
                                        send.addRestData(addDataToRestDataClass(data.get(j)));
                                        ((QuestionName)iterator.next()).setSend(send);
                                    }
                                }
                            } else {
                                send.addRestData(addDataToRestDataClass(data.get(j)));
                                questionName.setSend(send);
                                questionNameSet.add(questionName);
                            }
                        }
                    }else {
                        if (data.get(j).getSendReceive().equals("Snd")){
                            send = new Send();
                            questionName = new QuestionName(data.get(j).getQuestionName());
                            if (questionNameSet.contains(questionName)){
                                Iterator iterator = questionNameSet.iterator();
                                while (iterator.hasNext()){
                                    if (iterator.next().equals(questionName)){
                                        send.addRestData(addDataToRestDataClass(data.get(j)));
                                        ((QuestionName)iterator.next()).setSend(send);
                                    }
                                }
                            }else {
                                send.addRestData(addDataToRestDataClass(data.get(j)));
                                questionName.setSend(send);
                                questionNameSet.add(questionName);
                            }
                        }
                        if (data.get(j).getSendReceive().equals("Rcv")){
                            receive = new Receive();
                            questionName = new QuestionName(data.get(j).getQuestionName());
                            if (questionNameSet.contains(questionName)) {
                                Iterator iterator = questionNameSet.iterator();
                                while (iterator.hasNext()) {
                                    if (iterator.next().equals(questionName)) {
                                        receive.addRestData(addDataToRestDataClass(data.get(j)));
                                        ((QuestionName)iterator.next()).setReceive(receive);
                                    }
                                }
                            } else {
                                receive.addRestData(addDataToRestDataClass(data.get(j)));
                                questionName.setReceive(receive);
                                questionNameSet.add(questionName);
                            }
                        }
                    }
                    System.out.println(questionNameSet.toString());
                    numberForComparedQuestionNumber = j;
                    data.remove(numberForComparedQuestionNumber);
                    //j = j - 1;
                    System.out.println("j =    " + j);
                    System.out.println(data.size());
                }
                int temp;
                parsedString.add(ip, questionNameSet);
                fileWriter.write(writeDataToJSONString(parsedString));
                printWriter.println();
            }
            fileWriter.close();
            System.out.println(data.size());
        }
    }

    public static String writeDataToJSONString(ParsedString parsedString){
        Gson gson = new Gson();
        String jsonString = gson.toJson(parsedString);
        return jsonString;
    }

    public static RestData addDataToRestDataClass(UniversalClass universalClass){
        Opcode opcode = new Opcode(universalClass.getOpcode().getFlagsHex(), universalClass.getOpcode().getFlagsCharCode(), universalClass.getOpcode().getRespondedCode());
        RestData restData = new RestData(universalClass.getDate(),universalClass.getTime(),universalClass.getThreadID(),universalClass.getContext(),universalClass.getInternalPacketIdentifier(),universalClass.getProtocol(),universalClass.getXidHex(),opcode, universalClass.getQuestionType());
        return restData;
    }

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


//restData.setOpcode(opcode);

/*restData.setDate(universalClass.getDate());
        restData.setTime(universalClass.getTime());
        restData.setThreadID(universalClass.getThreadID());
        restData.setContext(universalClass.getContext());
        restData.setInternalPacketIdentifier(universalClass.getInternalPacketIdentifier());
        restData.setProtocol(universalClass.getProtocol());
        restData.setXidHex(universalClass.getXidHex());*/

// restData.setQuestionType(universalClass.getQuestionType());

/*
send = new Send();
                        receive = new Receive();
                        if (data.get(i).getSendReceive().equals("Snd") && data.get(j).getSendReceive().equals("Rcv")) {
                            System.out.println("55555555555");
                            receive.addRestData(addDataToRestDataClass(data.get(j)));
                            flagForSendNotEqual = true;
                        }
                        if (data.get(i).getSendReceive().equals("Rcv")  && data.get(j).getSendReceive().equals("Rcv")) {
                            System.out.println("66666666666666666");
                            receive.addRestData(addDataToRestDataClass(data.get(j)));
                            flagForReceiveNotEqual = true;
                        }
                        if (data.get(i).getSendReceive().equals("Rcv")  && data.get(j).getSendReceive().equals("Snd")) {
                            System.out.println("77777777777777777");
                            send.addRestData((addDataToRestDataClass(data.get(j))));
                            flagForReceiveNotEqual = true;
                        }
                        if (data.get(i).getSendReceive().equals("Snd")  && data.get(j).getSendReceive().equals("Snd")) {
                            System.out.println("88888888888888888888");
                            send.addRestData(addDataToRestDataClass(data.get(j)));
                            flagForSendNotEqual = true;
                        }
                        questionName = new QuestionName(data.get(j).getQuestionName());
                        questionName.setSend(send);
                        questionName.setReceive(receive);
                        questionNameSet.add(questionName);
 */