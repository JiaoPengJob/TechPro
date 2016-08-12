package cn.hongjitech.vehicle.util;

import android.content.Context;
import cn.hongjitech.vehicle.bean.MarkingBean;

/**
 * 处理练一练返回的数据
 */
public class ExamDataUtil {

    private Context context;

    public ExamDataUtil(Context context) {
        this.context = context;
    }

    /**
     * 获取练一练处理后的数据
     *
     * @return
     */
    public MarkingBean getExamData(String strMsg) {
//        String strMsg = SharedPrefsUtils.getValue(context, "strMsg", "");
        if (!strMsg.equals("")) {
            String msg = strMsg.substring(strMsg.indexOf(",", 1) + 1, strMsg.indexOf(",", 6));
            switch (msg) {
                /*-------------------通用-----------------------*/
                case "qibushichelianghouliujulixiaoyu30limikou10fen":
                    return new MarkingBean("", "10", "起步时后溜小于30公分");
                case "qibushichelianghouliujulidayu30limikou100fen":
                    return new MarkingBean("","100" ,"起步时后溜大于30公分" );
                case "qibushibushiyonghuocuowushiyongzhuanxiangdengkou10fen":
                    return new MarkingBean("", "10","未按规定使用转向灯");
                case "buanguidingluxianxingshikou100fen":
                    return new MarkingBean("", "100","不按规定路线行驶");
                case "xihuoyicikou10fen":
                    return new MarkingBean("", "10","熄火一次");
                case "buanguidingshiyonganquandaikou100fen":
                    return new MarkingBean("","100", "未按规定使用安全带");
                case "chelunyadaolubianyuanxiankou100fen":
                    return new MarkingBean("", "100","车轮压道路边缘线");
                case "zhongtutingchekou100fen":
                    return new MarkingBean("", "100","中途停车");
                /*------------------倒车入库------------------------*/
                case "yunxingshijianchaoguoguidingshijian":
                    return new MarkingBean("倒车入库","100", "倒车入库超时");
                case "cheshenchuxiankou100fen":
                    return new MarkingBean("倒车入库", "100","车身出线");
                case "daocheruku":
                    return new MarkingBean("倒车入库",  "0","开始");
                case "daokuburukou100fen":
                    return new MarkingBean("倒车入库", "100","倒库不入");
//                case "buanguidingluxianxingshikou100fen":
//                    return new MarkingBean("倒车入库","不按规定路线行驶","100");
//                case "zhongtutingchekou100fen":
//                    return new MarkingBean("倒车入库","中途停车","100");
                /*-------------------坡道定点停车和起步---------------*/
//                case "buanguidingluxianxingshikou100fen":
//                    return new MarkingBean("坡道定点停车和起步","不按规定路线行驶","100");
                case "podaodingdiantingcheyuqibu":
                    return new MarkingBean("坡道定点停车和起步","0", "开始");
                case "tingcheweizhibuzhunquewuchadayu50limikou100fen":
                    return new MarkingBean("坡道定点停车和起步", "100","停车位置不准确误差大于50公分");
                case "tingcheweizhibuzhunquewuchaxiaoyu50limikou10fen":
                    return new MarkingBean("坡道定点停车和起步", "10","停车位置不准确误差小于50公分");
                case "tingchehouchenshenjulilubianyuanxiandayu30limikou10fen":
                    return new MarkingBean("坡道定点停车和起步", "10","车身距路边缘线大于30公分");
                case "qibushishijianchaoguoguidingshijiankou100fen":
                    return new MarkingBean("坡道定点停车和起步","100", "起步时间超过30秒");
                case "weianguidingshiyongzhuanxiangdengkou10fen":
                    return new MarkingBean("坡道定点停车和起步",  "10","未按规定使用转向灯");
                 /*------------------侧方停车------------------------*/
                case "chelunyadaolubianyuanxiankou10fen":
                    return new MarkingBean("侧方停车","10", "车轮压道路边缘线");
//                case "chelunyadaolubianyuanxiankou100fen":
//                    return new MarkingBean("侧方停车","车轮压道路边缘线","100");
                case "cefangtingche":
                    return new MarkingBean("侧方停车", "0","开始");
//                case "zhongtutingchekou100fen":
//                    return new MarkingBean("侧方停车","中途停车","100");
                case "rukutingzhihoucheshenchuxiankou100fen":
                    return new MarkingBean("侧方停车", "100","车身出线");
                case "weianguidingshiyongzhuanxiangdengkou10fenkou10fen":
                    return new MarkingBean("侧方停车", "10","未按规定使用转向灯");
                /*--------------------曲线行驶--------------------------*/
//                case "chelunyadaolubianyuanxiankou100fen":
//                    return new MarkingBean("曲线行驶","车轮压道路边缘线","100");
//                case "zhongtutingchekou100fen":
//                    return new MarkingBean("曲线行驶","中途停车","100");
//                case "buanguidingluxianxingshikou100fen":
//                    return new MarkingBean("曲线行驶","不按规定路线行驶","100");
                case "quxianxingshi":
                    return new MarkingBean("曲线行驶", "0","开始");
                /*-------------------直角转弯------------------------------*/
                case "zhijiaozhuanwan":
                    return new MarkingBean("直角转弯", "0","开始");
                /*--------------模拟湿滑路行驶-------------------------*/
                case "jinrushihualuqianweijiansu":
                    return new MarkingBean("模拟湿滑路行驶","100", "进入湿滑路前未减速");
                case "weinengshiyongdidangpingwentongguo":
                    return new MarkingBean("模拟湿滑路行驶", "100","未能使用低速挡平稳通过");
                case "monishihualuxingshi":
                    return new MarkingBean("模拟湿滑路行驶",  "0","开始");
                /*------------模拟隧道行驶----------------*/
                case "shidisuidaorukoushiweiminglabadengkou10fen":
                    return new MarkingBean("模拟隧道行驶", "10","驶抵隧道入口时未鸣喇叭");
                case "shidisuidaoshiweijiansuhuoweikaiqiqianzhaoming":
                    return new MarkingBean("模拟隧道行驶", "100","驶抵隧道时未减速或未开启前照灯");
                case "shirusuidaohoubuanguidingchedaoxingshibiandao":
                    return new MarkingBean("模拟隧道行驶", "100","驶入隧道后不按规定车道行驶、变道");
                case "monisuidaoxingshi":
                    return new MarkingBean("模拟隧道行驶", "0","开始");
                case "shidisuidaochukoushiweiminglabadengkou10fen":
                    return new MarkingBean("模拟隧道行驶", "10","驶抵隧道出口时未鸣喇叭灯");
                case "shichusuidaohouweiguanbiqianzhaodengkou10fen":
                    return new MarkingBean("模拟隧道行驶", "10","驶出隧道后未关闭前照灯");
            }
        }
        return null;
    }
}
