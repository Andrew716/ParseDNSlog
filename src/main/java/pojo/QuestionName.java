package pojo;

/**
 * Created by andrii on 14.02.16.
 */
public class QuestionName {

    private String name;
    private Send send;
    private Receive receive;

    public QuestionName(){
    }

    public QuestionName(String name){
        this.name = name;
    }

    public QuestionName(Send send, Receive receive) {
        this.send = send;
        this.receive = receive;
    }

    public Send getSend() {
        return send;
    }

    public Receive getReceive() {
        return receive;
    }

    public void setSend(Send send) {
        this.send = send;
    }

    public void setReceive(Receive receive) {
        this.receive = receive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "Send:  " + send + "   Receive:" + receive;
    }
}
