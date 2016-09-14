package cn.hongjitech.vehicle.judgment;

import cn.hongjitech.vehicle.javaBean.SerialPortBean;
import cn.hongjitech.vehicle.parameter.BParameter;
import cn.hongjitech.vehicle.parameter.Marking;
import cn.hongjitech.vehicle.util.StringUtils;

/**
 * 科目二扣分判定
 * Created by jiaop on 2016/9/8.
 */
public class BJudgment {

    /**
     * 车身信号
     */
    SerialPortBean serialPortBean;

    /**
     * 扣分对象
     */
    Marking marking = null;

    public BJudgment(SerialPortBean serialPortBean){
        this.serialPortBean = serialPortBean;
    }

    /**
     * 科目二扣分判定主方法
     */
    public void main(){
         switch (BParameter.areaName){
             case "DCRK":
                 DCRK();
                 break;
             case "SPQB":
                 SPQB();
                 break;
             case "CFTC":
                 CFTC();
                 break;
             case "QXXS":
                 QXXS();
                 break;
                            case "ZJZW":
                                ZJZW();
                                break;
                            case "MNSDXS":
                                MNSDXS();
                                break;
                            case "MNSHLXS":
                                MNSHLXS();
                                break;
                        }

    }

    /**********************项目判定******************************/

    /**
     * 起步时后溜
     */
    public Marking carSneakedMax(){
        //判断是否倒车入库或者侧方停车
        if(!BParameter.areaName.equals("DCRK") && !BParameter.areaName.equals("CFTC")){
            //判断后退距离是否大于30cm
            if(BParameter.retreatLength > 0 && BParameter.retreatLength * 100 > 30){
                if(BParameter.areaName.equals("SPQB")){
                    return new Marking("上坡起步", "10105","100", "起步时后溜大于30公分", "qibushichelianghouliujulidayu30limikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                }else if(BParameter.areaName.equals("QXXS")){
                    return new Marking("曲线行驶", "10105","100", "起步时后溜大于30公分", "qibushichelianghouliujulidayu30limikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                }else if(BParameter.areaName.equals("ZJZW")){
                    return new Marking("直角转弯", "10105","100", "起步时后溜大于30公分", "qibushichelianghouliujulidayu30limikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                }else if(BParameter.areaName.equals("MNSDXS")){
                    return new Marking("模拟隧道行驶", "10105","100", "起步时后溜大于30公分", "qibushichelianghouliujulidayu30limikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                }else if(BParameter.areaName.equals("MNSHLXS")){
                    return new Marking("模拟湿滑路行驶", "10105","100", "起步时后溜大于30公分", "qibushichelianghouliujulidayu30limikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                }else{
                    return null;
                }
            }else{
                if(BParameter.areaName.equals("SPQB")){
                    return new Marking("上坡起步", "10202","10", "起步时后溜小于30公分", "qibushichelianghouliujulixiaoyu30limikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                }else if(BParameter.areaName.equals("QXXS")){
                    return new Marking("曲线行驶", "10202","10", "起步时后溜小于30公分", "qibushichelianghouliujulixiaoyu30limikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                }else if(BParameter.areaName.equals("ZJZW")){
                    return new Marking("直角转弯", "10202","10", "起步时后溜小于30公分", "qibushichelianghouliujulixiaoyu30limikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                }else if(BParameter.areaName.equals("MNSDXS")){
                    return new Marking("模拟隧道行驶", "10202","10", "起步时后溜小于30公分", "qibushichelianghouliujulixiaoyu30limikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                }else if(BParameter.areaName.equals("MNSHLXS")){
                    return new Marking("模拟湿滑路行驶", "10202","10", "起步时后溜小于30公分", "qibushichelianghouliujulixiaoyu30limikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                }else{
                    return null;
                }
            }
        }else{
            if(BParameter.retreatLength > 0 && BParameter.retreatLength * 100 > 30) {
                if (BParameter.areaName.equals("DCRK")) {
                    return new Marking("倒车入库", "10105","100", "起步时后溜大于30公分", "qibushichelianghouliujulidayu30limikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                } else if (BParameter.areaName.equals("CFTC")) {
                    return new Marking("侧方停车", "10105","100", "起步时后溜大于30公分", "qibushichelianghouliujulidayu30limikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                } else {
                    return null;
                }
            }else{
                if (BParameter.areaName.equals("DCRK")) {
                    return new Marking("倒车入库", "10202","10", "起步时后溜小于30公分", "qibushichelianghouliujulixiaoyu30limikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                } else if (BParameter.areaName.equals("CFTC")) {
                    return new Marking("侧方停车", "10202","10", "起步时后溜小于30公分", "qibushichelianghouliujulixiaoyu30limikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * 是否使用安全带
     * @return
     */
    public Marking useSeatBelt(){
        if(serialPortBean.getBf_belt_info().equals("02") || serialPortBean.getBf_belt_info().equals("01")){
            if (BParameter.areaName.equals("DCRK")) {
                return new Marking("倒车入库", "10101","100", "不按规定使用安全带", "buanguidingshiyonganquandaikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
            } else if (BParameter.areaName.equals("CFTC")) {
                return new Marking("侧方停车", "10101","100", "不按规定使用安全带", "buanguidingshiyonganquandaikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
            }else if(BParameter.areaName.equals("SPQB")){
                return new Marking("上坡起步", "10101","100", "不按规定使用安全带", "buanguidingshiyonganquandaikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
            }else if(BParameter.areaName.equals("QXXS")){
                return new Marking("曲线行驶", "10101","100", "不按规定使用安全带", "buanguidingshiyonganquandaikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
            }else if(BParameter.areaName.equals("ZJZW")){
                return new Marking("直角转弯", "10101","100", "不按规定使用安全带", "buanguidingshiyonganquandaikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
            }else if(BParameter.areaName.equals("MNSDXS")){
                return new Marking("模拟隧道行驶", "10101","100", "不按规定使用安全带", "buanguidingshiyonganquandaikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
            }else if(BParameter.areaName.equals("MNSHLXS")){
                return new Marking("模拟湿滑路行驶", "10101","100", "不按规定使用安全带", "buanguidingshiyonganquandaikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 是否熄火
     * @return
     */
    public boolean ifFlameout(){
        if(serialPortBean.getBf_rpm().equals("0")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 倒车入库
     * @return
     */
    public Marking DCRK(){
        //是否系安全带
        if(useSeatBelt() != null){
            marking = useSeatBelt();
        }

        //是否熄火
        if(ifFlameout()){
            marking = new Marking("倒车入库", "10210","10", "因操作不当造成发动机熄火一次", "xihuoyicikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //是否后溜
        if(carSneakedMax() != null){
            marking = carSneakedMax();
        }

        //不按规定路线行驶
        if(BParameter.underTheExercise){
            marking = new Marking("倒车入库", "20101","100", "不按规定路线、顺序行驶", "buanguidingluxianxingshikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //车身出线
        if(BParameter.outLetForStop){
            marking = new Marking("倒车入库", "20102","100", "车身出线", "cheshenchuxiankou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //车身出线
        if(BParameter.outLetForTravel){
            marking = new Marking("倒车入库", "20102","100", "车身出线", "cheshenchuxiankou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //倒库不入
        if(BParameter.nothingDownLibraries){
            marking = new Marking("倒车入库", "20103","100", "倒库不入", "daokuburukou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //中途停车
        if(BParameter.midwayParking){
            marking = new Marking("倒车入库", "20104","100", "中途停车", "zhongtutingchekou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //倒车入库超时,代码未定
        if(BParameter.DCRKTimeOut){
            marking = new Marking("倒车入库", "00000","100", "倒车入库超时", "yunxingshijianchaoguoguidingshijian", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        return marking;
    }

    /**
     * 上坡起步
     * @return
     */
    public Marking SPQB(){
        //是否系安全带
        if(useSeatBelt() != null){
            marking = useSeatBelt();
        }

        //是否熄火
        if(ifFlameout()){
            marking = new Marking("上坡起步", "10210","10", "因操作不当造成发动机熄火一次", "xihuoyicikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //是否后溜
        if(carSneakedMax() != null){
            marking = carSneakedMax();
        }

        //车辆停止后，汽车前保险杠前轴未定于桩杆线上
        if(BParameter.headForLine * 100 > 50){
            marking =new Marking("坡道定点停车和起步","20301", "100","停车位置不准确误差大于50公分","tingcheweizhibuzhunquewuchadayu50limikou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }else{
            marking =new Marking("坡道定点停车和起步","20303", "10","停车位置不准确误差小于50公分","tingcheweizhibuzhunquewuchaxiaoyu50limikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //起步时间超过规定时间
        if(BParameter.whetherOvertimeSPQB){
            marking = new Marking("坡道定点停车和起步","20302","100", "起步时间超过30秒","qibushishijianchaoguoguidingshijiankou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //车身距路边缘线大于30公分
        if(BParameter.pressRightEdgeLine * 100 > 30){
            marking = new Marking("坡道定点停车和起步","20304", "10","车身距路边缘线大于30公分","tingchehouchenshenjulilubianyuanxiandayu30limikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        return marking;
    }

    /**
     * 侧方停车
     */
    public Marking CFTC(){

        //是否系安全带
        if(useSeatBelt() != null){
            marking = useSeatBelt();
        }

        //是否熄火
        if(ifFlameout()){
            marking = new Marking("侧方停车", "10210","10", "因操作不当造成发动机熄火一次", "xihuoyicikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //是否后溜
        if(carSneakedMax() != null){
            marking = carSneakedMax();
        }

        //车辆入库停止后，车身出线
        if(BParameter.outLetForStop){
            marking = new Marking("侧方停车","20401","100", "车身出线","cheshenchuxiankou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //中途停车
        if(BParameter.midwayParking){
            marking = new Marking("侧方停车", "20402","100", "中途停车", "zhongtutingchekou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //行驶中压线
        if(BParameter.outLetForStop){
            marking = new Marking("侧方停车", "20403","10", "车身出线", "chelunyadaolubianyuanxiankou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        return marking;
    }

    /**
     * 曲线行驶
     * @return
     */
    public Marking QXXS(){

        //是否系安全带
        if(useSeatBelt() != null){
            marking = useSeatBelt();
        }

        //是否熄火
        if(ifFlameout()){
            marking = new Marking("曲线行驶", "10210","10", "因操作不当造成发动机熄火一次", "xihuoyicikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //是否后溜
        if(carSneakedMax() != null){
            marking = carSneakedMax();
        }

        //车辆入库停止后，车身出线
        if(BParameter.outLetForStop || BParameter.outLetForStop){
            marking = new Marking("曲线行驶","20601","100", "车身出线","cheshenchuxiankou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //中途停车
        if(BParameter.midwayParking){
            marking = new Marking("曲线行驶", "20602","100", "中途停车", "zhongtutingchekou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        return  marking;
    }

    /**
     * 直角转弯
     * @return
     */
    public Marking ZJZW(){

        //是否系安全带
        if(useSeatBelt() != null){
            marking = useSeatBelt();
        }

        //是否熄火
        if(ifFlameout()){
            marking = new Marking("直角转弯", "10210","10", "因操作不当造成发动机熄火一次", "xihuoyicikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //是否后溜
        if(carSneakedMax() != null){
            marking = carSneakedMax();
        }

        //车辆入库停止后，车身出线
        if(BParameter.outLetForStop || BParameter.outLetForStop){
            marking = new Marking("直角转弯","20701","100", "车身出线","cheshenchuxiankou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //中途停车
        if(BParameter.midwayParking){
            marking = new Marking("直角转弯", "20702","100", "中途停车", "zhongtutingchekou100fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        return marking;
    }

    /**
     * 模拟隧道行驶
     * @return
     */
    public Marking MNSDXS(){

        //是否系安全带
        if(useSeatBelt() != null){
            marking = useSeatBelt();
        }

        //是否熄火
        if(ifFlameout()){
            marking = new Marking("模拟隧道行驶", "10210","10", "因操作不当造成发动机熄火一次", "xihuoyicikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //是否后溜
        if(carSneakedMax() != null){
            marking = carSneakedMax();
        }

        //驶抵隧道时未减速或未开启前照灯
        if(BParameter.headForLine * 100< 300){
            if(BParameter.slowDown == false || !serialPortBean.getBf_dipped_headlight().equals("01")){
                marking = new Marking("模拟隧道行驶", "21401","100", "驶抵隧道时未减速或未开启前照灯", "", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
            }

            if(!serialPortBean.getBf_speaker().equals("A7")){
                marking = new Marking("模拟隧道行驶", "21403","10", "驶抵隧道入口时未鸣喇叭", "", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
            }
        }

        //驶抵隧道出口时未鸣喇叭
        if(BParameter.tailLength * 100 < 300){
            if(serialPortBean.getBf_speaker().equals("A7")){
                marking = new Marking("模拟隧道行驶", "21403","10", "驶抵隧道出口时未鸣喇叭", "", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
            }
        }

        //未开启前照灯
        if(!serialPortBean.getBf_dipped_headlight().equals("01")){
            marking = new Marking("模拟隧道行驶", "21402","100", "未开启前照灯", "", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //驶出隧道后未关闭前照灯
        if(BParameter.outSD){
            marking = new Marking("模拟隧道行驶", "21404","10", "驶出隧道后未关闭前照灯", "", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        return marking;
    }

    /**
     * 模拟湿滑路行驶
     * @return
     */
    public Marking MNSHLXS(){

        //是否系安全带
        if(useSeatBelt() != null){
            marking = useSeatBelt();
        }

        //是否熄火
        if(ifFlameout()){
            marking = new Marking("模拟湿滑路行驶", "10210","10", "因操作不当造成发动机熄火一次", "xihuoyicikou10fen", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //是否后溜
        if(carSneakedMax() != null){
            marking = carSneakedMax();
        }

        //未能使用低速挡平稳通过
        if(!serialPortBean.getBf_gear_info().equals("1") && !serialPortBean.getBf_gear_info().equals("2")){
            marking = new Marking("模拟湿滑路行驶", "21601","100", "未能使用低速挡平稳通过", "", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        //进入湿滑路前，未减速
        if(BParameter.headForLine * 100 < 300){
            if(BParameter.slowDown == false){
                marking = new Marking("模拟湿滑路行驶", "21602","100", "进入湿滑路前，未减速", "", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
            }
        }

        //通过湿滑路时急加速、急刹车
        if(BParameter.rapidAcceleration || BParameter.rapidDeceleration){
            marking = new Marking("模拟湿滑路行驶", "21603","100", "通过湿滑路时急加速、急刹车", "", StringUtils.getCurrentTime("yyyy-MM-dd"),StringUtils.getCurrentTime("HH:mm:ss"));
        }

        return marking;
    }

}
