package cn.hongjitech.vehicle.util;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.hongjitech.vehicle.javaBean.UploadUserInfo;

/**
 * Pull处理XML工具类
 */
public class Pull2XML {

    /**
     * 使用Pull解析器生成XML文件，并写到本地
     */
    public static void writeXmlToLocal(List<UploadUserInfo> list) {

        // 获得序列化对象
        XmlSerializer serializer = Xml.newSerializer();

        try {
            File path = new File(StringUtils.getInnerSDCardPath() + "/Android/data", "test .xml");
            FileOutputStream fos = new FileOutputStream(path);

            // 指定序列化对象输出的位置和编码
            serializer.setOutput(fos, "utf-8");

            // 写开始 <!--?xml version='1.0' encoding='utf-8' standalone='yes' ?-->
            serializer.startDocument("utf-8", true);

            serializer.startTag(null, "uploadUserInfos");

            for (UploadUserInfo uploadUserInfo : list) {

                // 开始写person
                serializer.startTag(null, "uploadUserInfo");
                //设置属性
//                serializer.attribute(null, "id", String.valueOf(person.getId()));

                // 写person的name
                serializer.startTag(null, "examTime");
                serializer.text(uploadUserInfo.getExamTime());
                serializer.endTag(null, "examTime");

                // 写person的age
                serializer.startTag(null, "userTime");
                serializer.text(uploadUserInfo.getUserTime());
                serializer.endTag(null, "userTime");

                // 写person的age
                serializer.startTag(null, "markNumber");
                serializer.text(uploadUserInfo.getMarkNumber());
                serializer.endTag(null, "markNumber");

                serializer.startTag(null, "markProject");
                serializer.text(uploadUserInfo.getMarkProject());
                serializer.endTag(null, "markProject");

                serializer.startTag(null, "markFraction");
                serializer.text(uploadUserInfo.getMarkFraction());
                serializer.endTag(null, "markFraction");

                serializer.startTag(null, "markReason");
                serializer.text(uploadUserInfo.getMarkReason());
                serializer.endTag(null, "markReason");

                serializer.startTag(null, "trainProject");
                serializer.text(uploadUserInfo.getTrainProject());
                serializer.endTag(null, "trainProject");

                serializer.startTag(null, "trainTestNumber");
                serializer.text(uploadUserInfo.getTrainTestNumber());
                serializer.endTag(null, "trainTestNumber");

                serializer.startTag(null, "trainPassNumber");
                serializer.text(uploadUserInfo.getTrainPassNumber());
                serializer.endTag(null, "trainPassNumber");

                serializer.startTag(null, "trainPassing");
                serializer.text(uploadUserInfo.getTrainPassing());
                serializer.endTag(null, "trainPassing");

                serializer.startTag(null, "trainSpeed");
                serializer.text(uploadUserInfo.getTrainSpeed());
                serializer.endTag(null, "trainSpeed");

                serializer.startTag(null, "headPortrait");
                serializer.text(uploadUserInfo.getHeadPortrait());
                serializer.endTag(null, "headPortrait");

                serializer.startTag(null, "studentSignature");
                serializer.text(uploadUserInfo.getStudentSignature());
                serializer.endTag(null, "studentSignature");

                serializer.startTag(null, "adminSignature");
                serializer.text(uploadUserInfo.getAdminSignature());
                serializer.endTag(null, "adminSignature");

                serializer.endTag(null, "uploadUserInfo");
            }

            serializer.endTag(null, "uploadUserInfos");

            serializer.endDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析XML
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static List<UploadUserInfo> parserXmlByPull(InputStream inputStream) throws Exception {
        List<UploadUserInfo> uploadUserInfos = null;
        UploadUserInfo uploadUserInfo = null;

        //    创建XmlPullParserFactory解析工厂
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        //    通过XmlPullParserFactory工厂类实例化一个XmlPullParser解析类
        XmlPullParser parser = factory.newPullParser();
        //    根据指定的编码来解析xml文档
        parser.setInput(inputStream, "utf-8");

        //    得到当前的事件类型
        int eventType = parser.getEventType();
        //    只要没有解析到xml的文档结束，就一直解析
        while (eventType != XmlPullParser.END_DOCUMENT) {

            switch (eventType) {
                //    解析到文档开始的时候
                case XmlPullParser.START_DOCUMENT:
                    uploadUserInfos = new ArrayList<UploadUserInfo>();
                    break;

                //    解析到xml标签的时候
                case XmlPullParser.START_TAG:
                    if ("uploadUserInfo".equals(parser.getName())) {
                        uploadUserInfo = new UploadUserInfo();
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("userTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    } else if ("examTime".equals(parser.getName())) {
                        uploadUserInfo.setExamTime(parser.nextText());
                    }


                    break;

                //    解析到xml标签结束的时候
                case XmlPullParser.END_TAG:
                    if ("person".equals(parser.getName())) {
                        uploadUserInfos.add(uploadUserInfo);
                        uploadUserInfo = null;
                    }
                    break;
            }
            //    通过next()方法触发下一个事件
            eventType = parser.next();
        }

        return uploadUserInfos;
    }

}
