package cn.hongjitech.vehicle.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 封装常用方法
 */
public class StringUtils {

    private static String hexString = "0123456789ABCDEF";

    /**
     * 获取当前时间String
     *
     * @param format 时间格式
     * @return
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date();
        return formatter.format(curDate);
    }

    /**
     * 获取当前时间Date
     *
     * @param format
     * @return
     */
    public static Date getCurrentDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date();
        return str2Date(formatter.format(curDate), format);
    }

    /**
     * 把日期转为字符串
     */
    public static String converToString(Date date, String type) {
        DateFormat df = new SimpleDateFormat(type, Locale.CHINA);
        return df.format(date);
    }

    /**
     * 生成随机时间
     *
     * @param beginDate
     * @param endDate
     * @param type
     * @return
     */
    public static String randomDate(String beginDate, String endDate, String type) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(type);
            Date start = format.parse(beginDate);//构造开始日期
            Date end = format.parse(endDate);//构造结束日期
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return converToString(new Date(date), type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        //如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    /**
     * 图片bitmap压缩
     *
     * @param image
     * @param pixelW
     * @param pixelH
     * @return
     */
    public static Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {  //如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        return bitmap;
    }

    /**
     * 字符串转换成十六进制字符串
     *
     * @param str 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 十六进制转换字符串
     *
     * @param hexStr Byte字符串(Byte之间无分隔符 如:[616C6B])
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xFF);
        }
        return new String(bytes);
    }

    /**
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String decode(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        //将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
        String bb = "";
        try {
            bb = new String(baos.toByteArray(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bb;
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b byte数组
     * @return String 每个Byte值之间空格分隔
     */
    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * bytes字符串转换为Byte值
     *
     * @param src Byte字符串，每个Byte之间没有分隔符
     * @return byte[]
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        System.out.println(l);
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = Byte.decode("0x" + src.substring(i * 2, m) + src.substring(m, n));
        }
        return ret;
    }

    /**
     * 将bitmap转换成byte[]
     *
     * @param bmp
     * @return
     */
    public static byte[] bitmapToByte(Bitmap bmp, Bitmap.CompressFormat compressFormat) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
        bmp.compress(compressFormat, 100, output);//把bitmap100%高质量压缩 到 output对象里
        bmp.recycle();//自由选择是否进行回收
        byte[] result = output.toByteArray();//转换成功了
        try {
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取android端内置sd卡路径
     *
     * @return
     */
    public static String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 字符串转时间
     *
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static Date str2Date(String dateStr, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 判断是否在此时间段内
     *
     * @param startDate
     * @param endDate
     * @param date
     * @return
     */
    public static boolean betweenDate(Date startDate, Date endDate, Date date) {
        return startDate.before(date) && endDate.after(date);
    }

    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 获取15分钟后时间
     *
     * @param longTime
     * @return
     */
    public static String getAfterTime(int longTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, longTime);
        return converToString(calendar.getTime(), "HH:mm:ss");
    }

    /**
     * Base64转Bitmap
     * @param base64String
     * @return
     */
    public static Bitmap base64ToBitmap(String base64String){
        byte[] bytes = Base64.decode(base64String,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    /**
     * 获取服务器图片
     */
//    private void getImg(){
//        VolleyManager.newInstance().ImageLoaderRequest
//                (, "http://backend.dev.hongjitech.cn/images/uploadImages/578ca48e8ed02.jpg", R.drawable.default_ptr_flip, R.drawable.default_ptr_flip);
//    }

    /**
     * 发送图片到服务器
     */
//    private void sendImg(){
//        Log.d("TAG",Environment.getExternalStorageDirectory().getPath());
//        Request<String> volleyMultipartRequest = new VolleyMultipartRequest("http://backend.dev.hongjitech.cn/uploadImage", new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Error",error.toString());
//            }
//        }, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("Success",response);
//            }
//        },"file",new File(Environment.getExternalStorageDirectory().getPath()+"/Pictures/assess_fail.png"),new HashMap<String,String>());
//        Volley.newRequestQueue(TestDrivesActivity.this, new OkHttp3Stack(new OkHttpClient())).add(volleyMultipartRequest);
//    }
}
