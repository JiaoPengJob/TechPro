package cn.hongjitech.vehicle.initTestData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongji on 16/7/11.
 */
public class ProjectTestData {

    public ProjectTestData() {

    }

    public List<String> setProjectTestData() {
        List<String> list = new ArrayList<String>();
        list.add("上车准备");
        list.add("起步");
        list.add("倒车入库");
        list.add("坡道定点停车和起步");
        list.add("侧方停车");
        list.add("曲线行驶");
        list.add("直角转弯");
        list.add("模拟隧道行驶");
        list.add("模拟湿滑路行驶");
//        list.add("模拟紧急情况处置(前方突然出现障碍物)");
//        list.add("模拟紧急情况处置(高速公路车辆故障)");
//        list.add("桩考");
//        list.add("通过单边桥");
//        list.add("通过限宽门");
//        list.add("通过连续障碍");
//        list.add("起伏路行驶");
//        list.add("窄路掉头");
//        list.add("模拟高速公路行驶");
//        list.add("模拟连续急转弯山区路行驶");
//        list.add("模拟雨(雾)天行驶");
        return list;
    }

}
