package pojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by andrii on 13.02.16.
 */
public class ParsedString {

    Map<IP, Set<QuestionName>> parsedString = new HashMap<IP, Set<QuestionName>>();

    public ParsedString() {
    }

    public ParsedString(Map<IP, Set<QuestionName>> parsedString) {
        this.parsedString = parsedString;
    }

    public Map<IP, Set<QuestionName>> getParsedString() {
        return parsedString;
    }

    public void setParsedString(Map<IP, Set<QuestionName>> parsedString) {
        this.parsedString = parsedString;
    }

    public void add(IP ip, Set<QuestionName> questionNameList){
        parsedString.put(ip, questionNameList);
    }

}
