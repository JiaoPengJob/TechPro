package cn.hongjitech.vehicle.initTestData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongji on 16/7/12.
 */
public class ArtProTestData {

    public ArtProTestData() {

    }

    public List<String> setTestData() {
        List<String> list = new ArrayList<String>();
        list.add("上车准备");
        list.add("起步");
        list.add("直线行驶");
        list.add("加减档位操作");
        list.add("变更车道");
        list.add("靠边停车");
        list.add("直行通过马路");
        list.add("路口左转弯");
        list.add("路口右转弯");
        list.add("通过人性横道线");
        list.add("通过学校区域");
        list.add("通过公共汽车站");
        list.add("会车");
        list.add("超车");
        list.add("掉头");
        list.add("夜间行驶");
        return list;
    }

}
