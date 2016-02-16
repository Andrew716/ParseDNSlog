package pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * com.relatedata.evi.Receive
 *
 * This POJO classes has field which stores data for
 * writing information to JSON file.
 *
 * @author Andrii Koropets
 * @since 2016-02-16
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

    @Override
    public String toString(){
        return "" + restDataList;
    }

}
