package cn.hongjitech.vehicle.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import cn.hongjitech.vehicle.bean.MarkingBean;

/**
 * 处理练一练返回的数据
 */
public class ExamDataUtil {

    private Context context;
    private MediaPlayer mp;

    public ExamDataUtil(Context context) {
        this.context = context;
        mp = new MediaPlayer();
    }

    /**
     * 获取练一练处理后的数据
     *
     * @return
     */
    public MarkingBean getExamData(String strMsg) {
        if (!strMsg.equals("")) {
            switch (strMsg) {
                case "-":
                    return new MarkingBean("倒车入库", "0", "开始","daocheruku");
//                case "bencixunliankaishi":
//                    return new MarkingBean("", "0", "开始训练","bencixunliankaishi");
                /*-------------------通用-----------------------*/
                case "qibushichelianghouliujulixiaoyu30limikou10fen":
                    return new MarkingBean("", "10", "起步时后溜小于30公分","qibushichelianghouliujulixiaoyu30limikou10fen");
                case "qibushichelianghouliujulidayu30limikou100fen":
                    return new MarkingBean("","100" ,"起步时后溜大于30公分","qibushichelianghouliujulidayu30limikou100fen");
                case "qibushibushiyonghuocuowushiyongzhuanxiangdengkou10fen":
                    return new MarkingBean("", "10","未按规定使用转向灯","qibushibushiyonghuocuowushiyongzhuanxiangdengkou10fen");
                case "buanguidingluxianxingshikou100fen":
                    return new MarkingBean("", "100","不按规定路线行驶","buanguidingluxianxingshikou100fen");
                case "xihuoyicikou10fen":
                    return new MarkingBean("", "10","熄火一次","xihuoyicikou10fen");
                case "buanguidingshiyonganquandaikou100fen":
                    return new MarkingBean("","100", "未按规定使用安全带","buanguidingshiyonganquandaikou100fen");
                case "chelunyadaolubianyuanxiankou100fen":
                    return new MarkingBean("", "100","车轮压道路边缘线","chelunyadaolubianyuanxiankou100fen");
                case "zhongtutingchekou100fen":
                    return new MarkingBean("", "100","中途停车","zhongtutingchekou100fen");
                /*------------------倒车入库------------------------*/
                case "yunxingshijianchaoguoguidingshijian":
                    return new MarkingBean("倒车入库","100", "倒车入库超时","yunxingshijianchaoguoguidingshijian");
                case "cheshenchuxiankou100fen":
                    return new MarkingBean("倒车入库", "100","车身出线","cheshenchuxiankou100fen");
                case "daocheruku":
                    return new MarkingBean("倒车入库",  "0","开始","daocheruku");
                case "daokuburukou100fen":
                    return new MarkingBean("倒车入库", "100","倒库不入","daokuburukou100fen");
//                case "buanguidingluxianxingshikou100fen":
//                    return new MarkingBean("倒车入库","不按规定路线行驶","100");
//                case "zhongtutingchekou100fen":
//                    return new MarkingBean("倒车入库","中途停车","100");
                /*-------------------坡道定点停车和起步---------------*/
//                case "buanguidingluxianxingshikou100fen":
//                    return new MarkingBean("坡道定点停车和起步","不按规定路线行驶","100");
                case "podaodingdiantingcheyuqibu":
                    return new MarkingBean("坡道定点停车和起步","0", "开始","podaodingdiantingcheyuqibu");
                case "tingcheweizhibuzhunquewuchadayu50limikou100fen":
                    return new MarkingBean("坡道定点停车和起步", "100","停车位置不准确误差大于50公分","tingcheweizhibuzhunquewuchadayu50limikou100fen");
                case "tingcheweizhibuzhunquewuchaxiaoyu50limikou10fen":
                    return new MarkingBean("坡道定点停车和起步", "10","停车位置不准确误差小于50公分","tingcheweizhibuzhunquewuchaxiaoyu50limikou10fen");
                case "tingchehouchenshenjulilubianyuanxiandayu30limikou10fen":
                    return new MarkingBean("坡道定点停车和起步", "10","车身距路边缘线大于30公分","tingchehouchenshenjulilubianyuanxiandayu30limikou10fen");
                case "qibushishijianchaoguoguidingshijiankou100fen":
                    return new MarkingBean("坡道定点停车和起步","100", "起步时间超过30秒","qibushishijianchaoguoguidingshijiankou100fen");
                case "weianguidingshiyongzhuanxiangdengkou10fen":
                    return new MarkingBean("坡道定点停车和起步",  "10","未按规定使用转向灯","weianguidingshiyongzhuanxiangdengkou10fen");
                 /*------------------侧方停车------------------------*/
                case "chelunyadaolubianyuanxiankou10fen":
                    return new MarkingBean("侧方停车","10", "车轮压道路边缘线","chelunyadaolubianyuanxiankou10fen");
//                case "chelunyadaolubianyuanxiankou100fen":
//                    return new MarkingBean("侧方停车","车轮压道路边缘线","100");
                case "cefangtingche":
                    return new MarkingBean("侧方停车", "0","开始","cefangtingche");
//                case "zhongtutingchekou100fen":
//                    return new MarkingBean("侧方停车","中途停车","100");
                case "rukutingzhihoucheshenchuxiankou100fen":
                    return new MarkingBean("侧方停车", "100","车身出线","rukutingzhihoucheshenchuxiankou100fen");
                case "weianguidingshiyongzhuanxiangdengkou10fenkou10fen":
                    return new MarkingBean("侧方停车", "10","未按规定使用转向灯","weianguidingshiyongzhuanxiangdengkou10fenkou10fen");
                /*--------------------曲线行驶--------------------------*/
//                case "chelunyadaolubianyuanxiankou100fen":
//                    return new MarkingBean("曲线行驶","车轮压道路边缘线","100");
//                case "zhongtutingchekou100fen":
//                    return new MarkingBean("曲线行驶","中途停车","100");
//                case "buanguidingluxianxingshikou100fen":
//                    return new MarkingBean("曲线行驶","不按规定路线行驶","100");
                case "quxianxingshi":
                    return new MarkingBean("曲线行驶", "0","开始","quxianxingshi");
                /*-------------------直角转弯------------------------------*/
                case "zhijiaozhuanwan":
                    return new MarkingBean("直角转弯", "0","开始","zhijiaozhuanwan");
                /*--------------模拟湿滑路行驶-------------------------*/
                case "jinrushihualuqianweijiansu":
                    return new MarkingBean("模拟湿滑路行驶","100", "进入湿滑路前未减速","jinrushihualuqianweijiansu");
                case "weinengshiyongdidangpingwentongguo":
                    return new MarkingBean("模拟湿滑路行驶", "100","未能使用低速挡平稳通过","weinengshiyongdidangpingwentongguo");
                case "monishihualuxingshi":
                    return new MarkingBean("模拟湿滑路行驶",  "0","开始","monishihualuxingshi");
                /*------------模拟隧道行驶----------------*/
                case "shidisuidaorukoushiweiminglabadengkou10fen":
                    return new MarkingBean("模拟隧道行驶", "10","驶抵隧道入口时未鸣喇叭","shidisuidaorukoushiweiminglabadengkou10fen");
                case "shidisuidaoshiweijiansuhuoweikaiqiqianzhaoming":
                    return new MarkingBean("模拟隧道行驶", "100","驶抵隧道时未减速或未开启前照灯","shidisuidaoshiweijiansuhuoweikaiqiqianzhaoming");
                case "shirusuidaohoubuanguidingchedaoxingshibiandao":
                    return new MarkingBean("模拟隧道行驶", "100","驶入隧道后不按规定车道行驶、变道","shirusuidaohoubuanguidingchedaoxingshibiandao");
                case "monisuidaoxingshi":
                    return new MarkingBean("模拟隧道行驶", "0","开始","monisuidaoxingshi");
                case "shidisuidaochukoushiweiminglabadengkou10fen":
                    return new MarkingBean("模拟隧道行驶", "10","驶抵隧道出口时未鸣喇叭灯","shidisuidaochukoushiweiminglabadengkou10fen");
                case "shichusuidaohouweiguanbiqianzhaodengkou10fen":
                    return new MarkingBean("模拟隧道行驶", "10","驶出隧道后未关闭前照灯","shichusuidaohouweiguanbiqianzhaodengkou10fen");
            }
        }
        return null;
    }
}
