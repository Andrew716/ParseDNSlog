package pojo;

/**
 * com.relatedata.evi.Opcode
 *
 * This POJO class has fields for storing information which
 * will be written to JSON file.
 *
 * @author Andrii Koropets
 * @since 2016-02-16
 */

public class Opcode {

    private String flagsHex;
    private String flagsCharCode;
    private String respondedCode;

    public Opcode(String flagsHex, String flagsCharCode, String respondedCode) {
        this.flagsHex = flagsHex;
        this.flagsCharCode = flagsCharCode;
        this.respondedCode = respondedCode;
    }

    public String getFlagsHex() {
        return flagsHex;
    }

    public String getFlagsCharCode() {
        return flagsCharCode;
    }

    public String getRespondedCode() {
        return respondedCode;
    }

    @Override
    public String toString(){
        return " flagsHEX:   " + flagsHex + "    flagsCharCode: " + flagsCharCode +
                "    respondedCode: " + respondedCode;
    }
}
