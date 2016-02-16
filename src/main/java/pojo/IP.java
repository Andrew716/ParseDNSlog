package pojo;


import java.util.Set;

/**
 * Created by andrii on 12.02.16.
 */
public class IP {

    private String ip;
    private Set<QuestionName> questionNameSet;

    public IP(){
    }

    public IP(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString(){
        return "ip: " +ip;
    }

    public Set<QuestionName> getQuestionNameSet() {
        return questionNameSet;
    }

    public void setQuestionNameSet(Set<QuestionName> questionNameSet) {
        this.questionNameSet = questionNameSet;
    }

    public void addQuestionName(QuestionName questionName){
        questionNameSet.add(questionName);
    }

}
