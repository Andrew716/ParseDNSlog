package pojo;

/**
 * com.relatedata.evi.RestData
 *
 * This POJO class is like auxiliary class for storing data
 * from dns file.
 *
 * @author Andrii Koropets
 * @since 2016-02-16
 */
public class RestData {

    private String date;
    private String time;
    private String threadID;
    private String context;
    private String internalPacketIdentifier;
    private String protocol;
    private String xidHex;
    private Opcode opcode;
    private String questionType;

    public RestData() {
    }

    public RestData(String date, String time, String threadID, String context, String internalPacketIdentifier, String protocol, String xidHex, Opcode opcode, String questionType) {
        this.date = date;
        this.time = time;
        this.threadID = threadID;
        this.context = context;
        this.internalPacketIdentifier = internalPacketIdentifier;
        this.protocol = protocol;
        this.xidHex = xidHex;
        this.opcode = opcode;
        this.questionType = questionType;
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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getXidHex() {
        return xidHex;
    }

    public void setXidHex(String xidHex) {
        this.xidHex = xidHex;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public void setOpcode(Opcode opcode) {
        this.opcode = opcode;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    @Override
    public String toString() {
        return "Date:   " + date + "     Time:  " + time + "    ThreadID:  " + threadID + "     Context:  " + context+
                "    InternalPacketIdentifier: " + internalPacketIdentifier + "    Protocol:  " + protocol +
                "    Xid:  " + xidHex + "    Opcode: " + opcode + "     questionType:  " + questionType;
    }
}
