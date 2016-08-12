package com.example.lkmap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.hongjitech.vehicle.R;

/**
 * Created by hongji on 16/8/1.
 */
public class DrawView extends View {

    private int MySrcHeight = 0;
    private int MySrcWidht = 0;
    private float GYM_ZoomSize = 1.0F;
    private float MyAreaData_GeoCenterX = 0.0F;
    private float MyAreaData_GeoCenterY = 0.0F;
    private float MyAreaData_InitGeoCenterX = 0.0F;
    private float MyAreaData_InitGeoCenterY = 0.0F;
    private int MyTu_Color = -16776961;
    private int MyChe_Color = -65536;
    private int MapShowType = 11;
    DrawView.Point_Site MyPointSite = new DrawView.Point_Site();
    private boolean IsLoadTuFile_Flag = false;
    List<TuData> myTuData_All = new ArrayList();
    DrawView.CheData MyCheData = new DrawView.CheData();
    private boolean IsLoadCheFile_Flag = false;

    static {
        System.loadLibrary("lkmap");
    }

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
//        p.setColor(R.color.blue);
        p.setStrokeWidth(0.5F);
        this.MySrcHeight = this.getHeight();
        this.MySrcWidht = this.getWidth();
        canvas.drawLine(2.0F, 2.0F, 2.0F, (float) (this.MySrcHeight - 2), p);
        canvas.drawLine(2.0F, (float) (this.MySrcHeight - 2), (float) (this.MySrcWidht - 2), (float) (this.MySrcHeight - 2), p);
        canvas.drawLine((float) (this.MySrcWidht - 2), (float) (this.MySrcHeight - 2), (float) (this.MySrcWidht - 2), 2.0F, p);
        canvas.drawLine((float) (this.MySrcWidht - 2), 2.0F, 2.0F, 2.0F, p);
        p.setColor(this.MyTu_Color);
        p.setStrokeWidth(1.0F);
        int mSize;
        int j;
        if (this.IsLoadTuFile_Flag) {
            mSize = this.MyPointSite.list_Area.size();
            if (mSize > 0) {
                for (j = 0; j < this.myTuData_All.size(); ++j) {
                    int tmpAreaData_Num;
                    for (tmpAreaData_Num = 0; tmpAreaData_Num < ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_Num - 1; ++tmpAreaData_Num) {
                        canvas.drawLine(((Float) ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_srcX.get(tmpAreaData_Num)).floatValue(), ((Float) ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_srcY.get(tmpAreaData_Num)).floatValue(), ((Float) ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_srcX.get(tmpAreaData_Num + 1)).floatValue(), ((Float) ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_srcY.get(tmpAreaData_Num + 1)).floatValue(), p);
                    }

                    tmpAreaData_Num = ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_Num;
                    canvas.drawLine(((Float) ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_srcX.get(tmpAreaData_Num - 1)).floatValue(), ((Float) ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_srcY.get(tmpAreaData_Num - 1)).floatValue(), ((Float) ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_srcX.get(0)).floatValue(), ((Float) ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_srcY.get(0)).floatValue(), p);
                }
            }
        }

        p.setColor(this.MyChe_Color);
        p.setStrokeWidth(2.0F);
        if (this.IsLoadCheFile_Flag) {
            mSize = this.MyCheData.CarData_X.size();
            if (mSize > 0) {
                for (j = 0; j < 23; ++j) {
                    canvas.drawLine(((Float) this.MyCheData.CarData_srcX.get(j)).floatValue(), ((Float) this.MyCheData.CarData_srcY.get(j)).floatValue(), ((Float) this.MyCheData.CarData_srcX.get(j + 1)).floatValue(), ((Float) this.MyCheData.CarData_srcY.get(j + 1)).floatValue(), p);
                }

                canvas.drawLine(((Float) this.MyCheData.CarData_srcX.get(23)).floatValue(), ((Float) this.MyCheData.CarData_srcY.get(23)).floatValue(), ((Float) this.MyCheData.CarData_srcX.get(0)).floatValue(), ((Float) this.MyCheData.CarData_srcY.get(0)).floatValue(), p);
                if ((double) this.GYM_ZoomSize < 0.1D) {
                    canvas.drawLine(((Float) this.MyCheData.CarData_srcX.get(24)).floatValue(), ((Float) this.MyCheData.CarData_srcY.get(24)).floatValue(), ((Float) this.MyCheData.CarData_srcX.get(25)).floatValue(), ((Float) this.MyCheData.CarData_srcY.get(25)).floatValue(), p);
                    canvas.drawLine(((Float) this.MyCheData.CarData_srcX.get(26)).floatValue(), ((Float) this.MyCheData.CarData_srcY.get(26)).floatValue(), ((Float) this.MyCheData.CarData_srcX.get(27)).floatValue(), ((Float) this.MyCheData.CarData_srcY.get(27)).floatValue(), p);
                    canvas.drawLine(((Float) this.MyCheData.CarData_srcX.get(28)).floatValue(), ((Float) this.MyCheData.CarData_srcY.get(28)).floatValue(), ((Float) this.MyCheData.CarData_srcX.get(29)).floatValue(), ((Float) this.MyCheData.CarData_srcY.get(29)).floatValue(), p);
                    canvas.drawLine(((Float) this.MyCheData.CarData_srcX.get(30)).floatValue(), ((Float) this.MyCheData.CarData_srcY.get(30)).floatValue(), ((Float) this.MyCheData.CarData_srcX.get(31)).floatValue(), ((Float) this.MyCheData.CarData_srcY.get(31)).floatValue(), p);
                }
            }
        }

    }

    public void SetZoomSize(float z) {
        this.GYM_ZoomSize = z;
        this.InputData(0.0F, 0.0F, 0.0F, this.MyAreaData_GeoCenterX, this.MyAreaData_GeoCenterY, 0.0F);
    }

    public float GetZoomSize() {
        return this.GYM_ZoomSize;
    }

    public void SetShowType(int type) {
        if (this.MapShowType == 11) {
            this.MapShowType = 99;
        } else {
            this.MapShowType = 11;
        }

    }

    public void InputData(float h, float p, float r, float x, float y, float z) {
        this.MyAreaData_GeoCenterX = x;
        this.MyAreaData_GeoCenterY = y;
        if (this.IsLoadCheFile_Flag) {
            this.CalcCheData(this.MyCheData, h, p, r, x, y, z);
        }

        float MyWidthCenter = (float) this.MySrcWidht / 2.0F;
        float MyHeightCenter = (float) this.MySrcHeight / 2.0F;
        if (this.IsLoadTuFile_Flag) {
            this.GeoDataToScreenCoordinate_Tu2(this.MyAreaData_GeoCenterX, this.MyAreaData_GeoCenterY, MyWidthCenter, MyHeightCenter, this.GYM_ZoomSize);
        }

        if (this.IsLoadCheFile_Flag) {
            this.GeoDataToScreenCoordinate_Che(this.MyAreaData_GeoCenterX, this.MyAreaData_GeoCenterY, MyWidthCenter, MyHeightCenter, this.GYM_ZoomSize);
        }

    }

    public int Load_TuData(String path) {
        boolean AreaNum = false;
        int var9 = this.LoadParaFileTu(path, this.MyPointSite);
        if (var9 > 0) {
            this.IsLoadTuFile_Flag = true;
            this.myTuData_All.clear();

            for (int Geo_MinX = 0; Geo_MinX < var9; ++Geo_MinX) {
                int Geo_MaxX = ((DrawView.Point_Site.MyArea) this.MyPointSite.list_Area.get(Geo_MinX)).PointNum;
                DrawView.TuData Geo_MinY = new DrawView.TuData();

                for (int Geo_MaxY = 0; Geo_MaxY < Geo_MaxX; ++Geo_MaxY) {
                    float j = ((DrawView.Point_Site.MyPoint) ((DrawView.Point_Site.MyArea) this.MyPointSite.list_Area.get(Geo_MinX)).area_point.get(Geo_MaxY)).pos_curr_x;
                    float i = ((DrawView.Point_Site.MyPoint) ((DrawView.Point_Site.MyArea) this.MyPointSite.list_Area.get(Geo_MinX)).area_point.get(Geo_MaxY)).pos_curr_y;
                    Geo_MinY.AreaData_X.add(Float.valueOf(j));
                    Geo_MinY.AreaData_Y.add(Float.valueOf(i));
                    Geo_MinY.AreaData_srcX.add(Float.valueOf(j));
                    Geo_MinY.AreaData_srcY.add(Float.valueOf(i));
                    ++Geo_MinY.AreaData_Num;
                }

                this.myTuData_All.add(Geo_MinY);
            }

            if (this.myTuData_All.size() <= 0) {
                this.MyAreaData_InitGeoCenterX = 0.0F;
                this.MyAreaData_InitGeoCenterY = 0.0F;
                this.MyAreaData_GeoCenterX = this.MyAreaData_InitGeoCenterX;
                this.MyAreaData_GeoCenterY = this.MyAreaData_InitGeoCenterY;
            } else {
                float var10 = 99999.99F;
                float var11 = -99999.99F;
                float var12 = 99999.99F;
                float var13 = -99999.99F;

                for (int var14 = 0; var14 < this.myTuData_All.size(); ++var14) {
                    for (int var15 = 0; var15 < ((DrawView.TuData) this.myTuData_All.get(var14)).AreaData_Num; ++var15) {
                        if (var10 > ((Float) ((DrawView.TuData) this.myTuData_All.get(var14)).AreaData_X.get(var15)).floatValue()) {
                            var10 = ((Float) ((DrawView.TuData) this.myTuData_All.get(var14)).AreaData_X.get(var15)).floatValue();
                        }

                        if (var12 > ((Float) ((DrawView.TuData) this.myTuData_All.get(var14)).AreaData_Y.get(var15)).floatValue()) {
                            var12 = ((Float) ((DrawView.TuData) this.myTuData_All.get(var14)).AreaData_Y.get(var15)).floatValue();
                        }

                        if (var11 < ((Float) ((DrawView.TuData) this.myTuData_All.get(var14)).AreaData_X.get(var15)).floatValue()) {
                            var11 = ((Float) ((DrawView.TuData) this.myTuData_All.get(var14)).AreaData_X.get(var15)).floatValue();
                        }

                        if (var13 < ((Float) ((DrawView.TuData) this.myTuData_All.get(var14)).AreaData_Y.get(var15)).floatValue()) {
                            var13 = ((Float) ((DrawView.TuData) this.myTuData_All.get(var14)).AreaData_Y.get(var15)).floatValue();
                        }
                    }
                }

                this.MyAreaData_InitGeoCenterX = (var10 + var11) / 2.0F;
                this.MyAreaData_InitGeoCenterY = (var12 + var13) / 2.0F;
                this.MyAreaData_GeoCenterX = this.MyAreaData_InitGeoCenterX;
                this.MyAreaData_GeoCenterY = this.MyAreaData_InitGeoCenterY;
            }
        }

        return var9;
    }

    public int Load_CheData(String path) {
        boolean CarNum = false;
        int CarNum1 = this.LoadParaFileChe(path, this.MyCheData);
        if (CarNum1 > 0) {
            this.IsLoadCheFile_Flag = true;
            this.InputData(0.0F, 0.0F, 0.0F, this.MyAreaData_InitGeoCenterX, this.MyAreaData_InitGeoCenterY, 0.0F);
        }

        return CarNum1;
    }

    public void SetTu_Color(int color) {
        this.MyTu_Color = color;
    }

    public void SetChe_Color(int color) {
        this.MyChe_Color = color;
    }

    public void SetDrawSize(int H, int W) {
        this.MySrcHeight = H;
        this.MySrcWidht = W;
    }

    private void GeoDataToScreenCoordinate_Che(float geo_center_x, float geo_center_y, float see_center_x, float see_center_y, float resolution_xy) {
        int mSize = this.MyCheData.CarData_X.size();
        if (mSize > 0) {
            this.MyCheData.CarData_srcX.clear();
            this.MyCheData.CarData_srcY.clear();
            this.MyCheData.CarData_srcZ.clear();

            for (int i = 0; i < mSize; ++i) {
                float pos_x = ((Float) this.MyCheData.CarData_X.get(i)).floatValue();
                float pos_y = ((Float) this.MyCheData.CarData_Y.get(i)).floatValue();
                float src_x = see_center_x + (pos_x - geo_center_x) / resolution_xy;
                float src_y = see_center_y - (pos_y - geo_center_y) / resolution_xy;
                this.MyCheData.CarData_srcX.add(Float.valueOf(src_x));
                this.MyCheData.CarData_srcY.add(Float.valueOf(src_y));
            }

        }
    }

    private void GeoDataToScreenCoordinate_Tu(float geo_center_x, float geo_center_y, float see_center_x, float see_center_y, float resolution_xy) {
        int mSize = this.MyPointSite.list_Area.size();
        if (mSize > 0) {
            this.MyPointSite.ClearSrceenPointData();

            for (int j = 0; j < mSize; ++j) {
                int area_point_num = ((DrawView.Point_Site.MyArea) this.MyPointSite.list_Area.get(j)).area_point.size();

                for (int i = 0; i < area_point_num; ++i) {
                    float geoX = ((DrawView.Point_Site.MyPoint) ((DrawView.Point_Site.MyArea) this.MyPointSite.list_Area.get(j)).area_point.get(i)).pos_curr_x;
                    float geoY = ((DrawView.Point_Site.MyPoint) ((DrawView.Point_Site.MyArea) this.MyPointSite.list_Area.get(j)).area_point.get(i)).pos_curr_y;
                    float srcX = see_center_x + (geoX - geo_center_x) / resolution_xy;
                    float srcY = see_center_y - (geoY - geo_center_y) / resolution_xy;
                    this.MyPointSite.UpdateSrceenPointData(j, i, srcX, srcY);
                }
            }

        }
    }

    private void GeoDataToScreenCoordinate_Tu2(float geo_center_x, float geo_center_y, float see_center_x, float see_center_y, float resolution_xy) {
        if (this.myTuData_All.size() > 0) {
            for (int j = 0; j < this.myTuData_All.size(); ++j) {
                ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_srcX.clear();
                ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_srcY.clear();

                for (int i = 0; i < ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_Num; ++i) {
                    float srcX = see_center_x + (((Float) ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_X.get(i)).floatValue() - geo_center_x) / resolution_xy;
                    float srcY = see_center_y - (((Float) ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_Y.get(i)).floatValue() - geo_center_y) / resolution_xy;
                    ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_srcX.add(Float.valueOf(srcX));
                    ((DrawView.TuData) this.myTuData_All.get(j)).AreaData_srcY.add(Float.valueOf(srcY));
                }
            }

        }
    }

    public native int LoadParaFileTu(String var1, DrawView.Point_Site var2);

    public native int UpdateData(DrawView.Point_Site var1, float var2, float var3, float var4, float var5, float var6, float var7);

    public native int SetSrceenSize(int var1, int var2, float var3);

    public native int LoadParaFileChe(String var1, DrawView.CheData var2);

    public native int CalcCheData(DrawView.CheData var1, float var2, float var3, float var4, float var5, float var6, float var7);

    public class CheData {
        int CarPara_Num = 0;
        List<Float> CarData_X = new ArrayList();
        List<Float> CarData_Y = new ArrayList();
        List<Float> CarData_Z = new ArrayList();
        List<Float> CarData_srcX = new ArrayList();
        List<Float> CarData_srcY = new ArrayList();
        List<Float> CarData_srcZ = new ArrayList();

        public CheData() {
            this.CarData_X.clear();
            this.CarData_Y.clear();
            this.CarData_Z.clear();
            this.CarData_srcX.clear();
            this.CarData_srcY.clear();
            this.CarData_srcZ.clear();
        }

        public void UpdateData(int id, float pos_x, float pos_y, float pos_z) {
            this.CarData_X.add(Float.valueOf(pos_x));
            this.CarData_Y.add(Float.valueOf(pos_y));
            this.CarData_Z.add(Float.valueOf(pos_z));
            ++this.CarPara_Num;
        }

        public void ClearData() {
            this.CarPara_Num = 0;
            this.CarData_X.clear();
            this.CarData_Y.clear();
            this.CarData_Z.clear();
            this.CarData_srcX.clear();
            this.CarData_srcY.clear();
            this.CarData_srcZ.clear();
        }
    }

    public class Point_Site {
        public DrawView.Point_Site.MyArea area = new DrawView.Point_Site.MyArea();
        public List<DrawView.Point_Site.MyArea> list_Area = new ArrayList();
        public float GeogCenter_X = 0.0F;
        public float GeogCenter_Y = 0.0F;

        public Point_Site() {
            this.list_Area.clear();
        }

        public void AddPoint(float pos_curr_x, float pos_curr_y) {
            DrawView.Point_Site.MyPoint point = new DrawView.Point_Site.MyPoint();
            point.setPos_curr_xy(pos_curr_x, pos_curr_y);
            this.area.add_point(point);
            ++this.area.PointNum;
        }

        public void EndAddPoint() {
            if (this.area.PointNum > 0) {
                DrawView.Point_Site.MyArea my_area = new DrawView.Point_Site.MyArea();
                my_area.PointNum = this.area.PointNum;
                my_area.area_point.addAll(this.area.area_point);
                my_area.area_point_src.addAll(this.area.area_point_src);
                this.list_Area.add(my_area);
                this.area.AreaID = 0;
                this.area.PointNum = 0;
                this.area.area_point.clear();
                this.area.area_point_src.clear();
            }

        }

        public void ClearPointData() {
            this.list_Area.clear();
        }

        public void UpdateSrceenPointData(int area_id, int point_num, float x, float y) {
            DrawView.Point_Site.MyPoint point = new DrawView.Point_Site.MyPoint();
            point.setPos_curr_xy(x, y);
            ((DrawView.Point_Site.MyArea) this.list_Area.get(area_id)).area_point_src.add(point);
        }

        public void ClearSrceenPointData() {
            for (int i = 0; i < this.list_Area.size(); ++i) {
                ((DrawView.Point_Site.MyArea) this.list_Area.get(i)).area_point_src.clear();
            }

        }

        public class MyArea {
            public int AreaID = 0;
            public int PointNum = 0;
            public List<DrawView.Point_Site.MyPoint> area_point = new ArrayList();
            public List<DrawView.Point_Site.MyPoint> area_point_src = new ArrayList();

            public MyArea() {
                this.area_point.clear();
                this.area_point_src.clear();
            }

            public void add_point(DrawView.Point_Site.MyPoint _point) {
                this.area_point.add(_point);
                this.area_point_src.add(_point);
            }
        }

        public class MyPoint {
            public float pos_curr_x = 0.0F;
            public float pos_curr_y = 0.0F;

            public MyPoint() {
            }

            public void setPos_curr_xy(float x, float y) {
                this.pos_curr_x = x;
                this.pos_curr_y = y;
            }
        }
    }

    public class TuData {
        public int AreaID = 0;
        public int AreaData_Num = 0;
        List<Float> AreaData_X = new ArrayList();
        List<Float> AreaData_Y = new ArrayList();
        List<Float> AreaData_srcX = new ArrayList();
        List<Float> AreaData_srcY = new ArrayList();

        public TuData() {
            this.AreaData_X.clear();
            this.AreaData_Y.clear();
            this.AreaData_srcX.clear();
            this.AreaData_srcY.clear();
        }
    }
}
