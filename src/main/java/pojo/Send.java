package pojo;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by andrii on 12.02.16.
 */
public class Send {

    private List<RestData> restDataList = new ArrayList<RestData>();

    public Send() {
    }

    public Send(List<RestData> restDataList) {
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
