package cn.hongjitech.vehicle.initTestData;

import java.util.ArrayList;
import java.util.List;

import cn.hongjitech.vehicle.bean.ArtificialCode;

/**
 * 扣分代码数据
 */
public class ArtificialCodeData {

    public List<ArtificialCode> getData(){
        List<ArtificialCode> list = new ArrayList<ArtificialCode>();
        list.add(new ArtificialCode("10101", "不按规定使用安全带或者戴安全头盔", "100"));

        list.add(new ArtificialCode("10105", "起步时车辆后溜距离大于30cm", "100"));
        list.add(new ArtificialCode("10202", "起步时车辆后溜距离小于30cm", "10"));
        list.add(new ArtificialCode("10210", "因操作不当造成发动机熄火一次", "10"));
        list.add(new ArtificialCode("20101", "不按规定路线、顺序行驶", "100"));
        list.add(new ArtificialCode("20102", "车身出线", "100"));
        list.add(new ArtificialCode("20103", "倒库不入", "100"));
        list.add(new ArtificialCode("20104", "中途停车 ", "100"));
        list.add(new ArtificialCode("20301", "车辆停止后，前保险杠未定于桩杆线上，且前后超出50cm", "100"));
        list.add(new ArtificialCode("20302", "起步时间超过规定时间", "100"));
        list.add(new ArtificialCode("20303", "车辆停止后，前保险杠未定于桩杆线上，且前后不超出", "10"));
        list.add(new ArtificialCode("20304", "车辆停止后，车身距离路边缘线30cm以上", "10"));
        list.add(new ArtificialCode("20401", "车辆入库停止后，车身出线", "100"));
        list.add(new ArtificialCode("20403", "行驶中轮胎触轧车道边线", "10"));
        list.add(new ArtificialCode("20601", "车轮轧道路边缘线", "100"));
        list.add(new ArtificialCode("20701", "车轮轧道路边缘线", "100"));
        list.add(new ArtificialCode("21401", "驶抵隧道时未减速或未开启前照灯", "100"));
        list.add(new ArtificialCode("21402", "驶入隧道后不按规定车道行驶、变道", "100"));
        list.add(new ArtificialCode("21403", "驶抵隧道入（出）口时未鸣喇叭", "10"));
        list.add(new ArtificialCode("21404", "驶出隧道后未关闭前照灯", "10"));
        list.add(new ArtificialCode("21601", "未能使用低速挡平稳通过", "100"));
        list.add(new ArtificialCode("21602", "进入湿滑路前，未减速", "100"));
        list.add(new ArtificialCode("21603", "通过时急加速、急刹车", "100"));

        return list;
    }

}
