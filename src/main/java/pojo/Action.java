package pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by andrii on 15.02.16.
 */
public class Action {

    private Set<IP> ipList = new HashSet<IP>();

    public Action() {
    }

    public Set<IP> getIpList() {
        return ipList;
    }

    public void setIpList(Set<IP> ipList) {
        this.ipList = ipList;
    }

    public void addIP(IP ip){
        ipList.add(ip);
    }

    @Override
    public String toString() {
        return "" + ipList;
    }
}
