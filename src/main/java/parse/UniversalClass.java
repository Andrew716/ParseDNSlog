package parse;

import pojo.Opcode;

/**
 * com.relatedata.evi.UniversalClass
 *
 * This is auxiliary class which has fields for
 * storing information which was in dns.log
 *
 * @author Andrii Koropets
 * @since 2016-02-16
 */
public class UniversalClass {

    private String date;
    private String time;
    private String threadID;
    private String context;
    private String internalPacketIdentifier;
    private String protocol;
    private String sendReceive;
    private String remoteIP;
    private String xidHex;
    private String questionType;
    private String questionName;
    private Opcode opcode;

    public UniversalClass() {
    }

    public UniversalClass(String date, String time, String threadID, String context, String internalPacketIdentifier, String protocol, String sendReceive, String remoteIP, String xidHex, Opcode opcode, String questionType, String questionName) {
        this.date = date;
        this.time = time;
        this.threadID = threadID;
        this.context = context;
        this.internalPacketIdentifier = internalPacketIdentifier;
        this.protocol = protocol;
        this.sendReceive = sendReceive;
        this.remoteIP = remoteIP;
        this.xidHex = xidHex;
        this.opcode = opcode;
        this.questionType = questionType;
        this.questionName = questionName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getThreadID() {
        return threadID;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getInternalPacketIdentifier() {
        return internalPacketIdentifier;
    }

    public void setInternalPacketIdentifier(String internalPacketIdentifier) {
        this.internalPacketIdentifier = internalPacketIdentifier;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSendReceive() {
        return sendReceive;
    }

    public void setSendReceive(String sendReceive) {
        this.sendReceive = sendReceive;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }

    public String getXidHex() {
        return xidHex;
    }

    public void setXidHex(String xidHex) {
        this.xidHex = xidHex;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public void setOpcode(Opcode opcode) {
        this.opcode = opcode;
    }

    @Override
    public String toString(){
        return "Date: " + date + "    Time: " + time + "   ThreadID: " + threadID + "    Context: " + context +
                "    InternalPacketIdentifier:  " + internalPacketIdentifier + "     Protocol: " + protocol +
                "     SendReceive:  " + sendReceive + "    RemoteIP:  " + remoteIP +   "   Xid_Hex:  " + xidHex +
                "     Opcode:  " + opcode +  "    QuestionType:  "  + questionType +
                "    QuestionName:  " + questionName;
    }
}
