package pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * com.relatedata.evi.Action
 *
 * This POJO class contains set of ip for writing
 * this set in JSON file.
 *
 * @author Andrii Koropets
 * @since 2016-02-16
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
