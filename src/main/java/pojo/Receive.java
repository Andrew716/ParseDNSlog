package pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Created by andrii on 12.02.16.
 */
public class Receive {

    private List<RestData> restDataList = new ArrayList<RestData>();

    public Receive() {
    }

    public Receive(List<RestData> restDataList) {
        this.restDataList = restDataList;
    }

    public List<RestData> getRestDataList() {
        return restDataList;
    }

    public void setRestDataList(List<RestData> restDataList) {
        this.restDataList = restDataList;
    }

    public void addRestData(RestData restData){
        restDataList.add(restData);
    }

}
