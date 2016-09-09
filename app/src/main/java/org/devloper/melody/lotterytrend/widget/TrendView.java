package org.devloper.melody.lotterytrend.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import org.devloper.melody.lotterytrend.R;
import org.devloper.melody.lotterytrend.model.TrendModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuqiang on 2014/12/29.
 * 绘制的走势图View
 */
public class TrendView extends View {
    //小球文字画笔
    private Paint mPaintText = null;

    //小球画笔
    private Paint mBallPaint=null;

    //网格线画笔
    private Paint mPaintLine=null;

    //小球之间的连线画笔
    private Paint mLinkLine=null;

    //红球个数:33个
    private int mRedNum=33;

    //蓝球个数:16个
    private int mBlueNum=16;

    //红球
    private Bitmap mRedBitmapBall = null;

    //蓝球
    private Bitmap mBlueBitmapBall = null;

    //网格的水平间距
    private float mDeltaX;

    //网格垂直间距
    private float mDeltaY;

    //当前View的宽度
    private int mWidth;

    //当前View的高度
    private int mHeight;

    //存储当前蓝球,方便于小球之间画连线
    private String [] mSaveBlueNum;

    //红球和篮球分布数据集合
    private List<TrendModel> mBallList=null;

    //新浪双色球走势图请求结果字符串
    //todo:新浪这个数据串每个中奖号码的串后面都多出了一个号码。应该去掉;
    //@see:请求链接：http://mobile.aicai.com/support/numberChart.do?agentId=2332891&sid=B4A3D075DA67112410EB733CC29A693B-13844409&version=ACP_Android_3.4.1&clientType=1&imei=08002732fa94&appType=1&token=1419816480563&iv=2&macAdrs=08:00:27:32:fa:94&timeTag=1419818216432&gameId=101&dateInterval=30&sign=LRWnZryO903P6mwyCKHVlQP4F9Y=
    private String jsonStr="{\"rss\":{\"-version\":\"2.0\",\"respCode\":\"0000\",\"respMesg\":\"处理成功\",\"list\":{\"item\":[{\"issueNo\":\"2014151\",\"winNumber\":\"04,05,08,11,21,27,08,01\",\"redMiss\":\" 3,3,1,0,0,4,2,0,2,2,0,3,5,1,2,3,11,8,2,5,0,1,6,1,8,4,0,9,5,18,4,1,2 \",\"blueMiss\":\"2,17,33,27,28,11,38,0,1,4,6,24,3,7,45,8\"},{\"issueNo\":\"2014150\",\"winNumber\":\"03,08,14,22,24,32,09,14\",\"redMiss\":\" 2,2,0,12,2,3,1,0,1,1,9,2,4,0,1,2,10,7,1,4,8,0,5,0,7,3,6,8,4,17,3,0,1\",\"blueMiss\":\"1,16,32,26,27,10,37,18,0,3,5,23,2,6,44,7\"},{\"issueNo\":\"2014149\",\"winNumber\":\"07,09,10,15,19,33,01,05\",\"redMiss\":\" 1,1,5,11,1,2,0,8,0,0,8,1,3,9,0,1,9,6,0,3,7,2,4,8,6,2,5,7,3,16,2,2,0\",\"blueMiss\":\"0,15,31,25,26,9,36,17,12,2,4,22,1,5,43,6\"},{\"issueNo\":\"2014148\",\"winNumber\":\"01,02,05,12,15,16,13,01\",\"redMiss\":\" 0,0,4,10,0,1,1,7,4,3,7,0,2,8,0,0,8,5,12,2,6,1,3,7,5,1,4,6,2,15,1,1,8 \",\"blueMiss\":\"2,14,30,24,25,8,35,16,11,1,3,21,0,4,42,5\"},{\"issueNo\":\"2014147\",\"winNumber\":\"06,07,22,26,31,32,10,07\",\"redMiss\":\" 1,12,3,9,3,0,0,6,3,2,6,2,1,7,8,6,7,4,11,1,5,0,2,6,4,0,3,5,1,14,0,0,7\",\"blueMiss\":\"1,13,29,23,24,7,34,15,10,0,2,20,5,3,41,4\"},{\"issueNo\":\"2014146\",\"winNumber\":\"01,06,13,20,29,32,01,02\",\"redMiss\":\" 0,11,2,8,2,0,18,5,2,1,5,1,0,6,7,5,6,3,10,0,4,4,1,5,3,1,2,4,0,13,8,0,6\",\"blueMiss\":\"0,12,28,22,23,6,33,14,9,5,1,19,4,2,40,3\"},{\"issueNo\":\"2014145\",\"winNumber\":\"10,12,13,23,26,29,11,13\",\"redMiss\":\" 6,10,1,7,1,1,17,4,1,0,4,0,0,5,6,4,5,2,9,2,3,3,0,4,2,0,1,3,0,12,7,9,5\",\"blueMiss\":\"10,11,27,21,22,5,32,13,8,4,0,18,3,1,39,2\"},{\"issueNo\":\"2014144\",\"winNumber\":\"03,05,06,09,10,27,14,15\",\"redMiss\":\" 5,9,0,6,0,0,16,3,0,0,3,1,6,4,5,3,4,1,8,1,2,2,2,3,1,1,0,2,5,11,6,8,4\",\"blueMiss\":\"9,10,26,20,21,4,31,12,7,3,5,17,2,0,38,1\"},{\"issueNo\":\"2014143\",\"winNumber\":\"03,12,18,20,25,26,16\",\"redMiss\":\" 4,8,0,5,9,1,15,2,2,3,2,0,5,3,4,2,3,0,7,0,1,1,1,2,0,0,7,1,4,10,5,7,3\",\"blueMiss\":\"8,9,25,19,20,3,30,11,6,2,4,16,1,12,37,0\"},{\"issueNo\":\"2014142\",\"winNumber\":\"06,21,22,23,25,28,13\",\"redMiss\":\" 3,7,5,4,8,0,14,1,1,2,1,15,4,2,3,1,2,10,6,3,0,0,0,1,0,8,6,0,3,9,4,6,2\",\"blueMiss\":\"7,8,24,18,19,2,29,10,5,1,3,15,0,11,36,26\"},{" +
            "\"issueNo\":\"2014141\",\"winNumber\":\"08,09,11,16,21,24,10\",\"redMiss\":\" 2,6,4,3,7,1,13,0,0,1,0,14,3,1,2,0,1,9,5,2,0,7,7,0,2,7,5,7,2,8,3,5,1 \",\"blueMiss\":\"6,7,23,17,18,1,28,9,4,0,2,14,3,10,35,25\"},{\"issueNo\":\"2014140\",\"winNumber\":\"06,10,11,14,17,33,06\",\"redMiss\":\" 1,5,3,2,6,0,12,11,3,0,0,13,2,0,1,4,0,8,4,1,9,6,6,10,1,6,4,6,1,7,2,4,0 \",\"blueMiss\":\"5,6,22,16,17,0,27,8,3,4,1,13,2,9,34,24\"},{\"issueNo\":\"2014139\",\"winNumber\":\"01,14,15,20,25,29,11\",\"redMiss\":\" 0,4,2,1,5,1,11,10,2,9,2,12,1,0,0,3,8,7,3,0,8,5,5,9,0,5,3,5,0,6,1,3,1 \",\"blueMiss\":\"4,5,21,15,16,14,26,7,2,3,0,12,1,8,33,23\"},{\"issueNo\":\"2014138\",\"winNumber\":\"04,06,13,29,31,33,13\",\"redMiss\":\" 8,3,1,0,4,0,10,9,1,8,1,11,0,5,6,2,7,6,2,9,7,4,4,8,1,4,2,4,0,5,0,2,0 \",\"blueMiss\":\"3,4,20,14,15,13,25,6,1,2,16,11,0,7,32,22\"},{\"issueNo\":\"2014137\",\"winNumber\":\"03,06,09,11,25,29,09\",\"redMiss\":\" 7,2,0,2,3,0,9,8,0,7,0,10,2,4,5,1,6,5,1,8,6,3,3,7,0,3,1,3,0,4,1,1,2 \",\"blueMiss\":\"2,3,19,13,14,12,24,5,0,1,15,10,4,6,31,21\"},{\"issueNo\":\"2014136\",\"winNumber\":\"03,16,19,27,31,32,10\",\"redMiss\":\" 6,1,0,1,2,4,8,7,7,6,1,9,1,3,4,0,5,4,0,7,5,2,2,6,1,2,0,2,14,3,0,0,1 \",\"blueMiss\":\"1,2,18,12,13,11,23,4,15,0,14,9,3,5,30,20\"},{\"issueNo\":\"2014135\",\"winNumber\":\"02,04,11,13,25,33,01\",\"redMiss\":\" 5,0,27,0,1,3,7,6,6,5,0,8,0,2,3,1,4,3,19,6,4,1,1,5,0,1,8,1,13,2,2,4,0 \",\"blueMiss\":\"0,1,17,11,12,10,22,3,14,5,13,8,2,4,29,19\"},{\"issueNo\":\"2014134\",\"winNumber\":\"05,16,22,23,26,28,02\",\"redMiss\":\" 4,4,26,45,0,2,6,5,5,4,8,7,1,1,2,0,3,2,18,5,3,0,0,4,17,0,7,0,12,1,1,3,2 \",\"blueMiss\":\"8,0,16,10,11,9,21,2,13,4,12,7,1,3,28,18\"},{\"issueNo\":\"2014133\",\"winNumber\":\"13,14,16,23,30,31,13\",\"redMiss\":\" 3,3,25,44,1,1,5,4,4,3,7,6,0,0,1,0,2,1,17,4,2,2,0,3,16,8,6,2,11,0,0,2,1 \",\"blueMiss\":\"7,4,15,9,10,8,20,1,12,3,11,6,0,2,27,17\"},{\"issueNo\":\"2014132\",\"winNumber\":\"05,06,14,15,18,33,08\",\"redMiss\":\" 2,2,24,43,0,0,4,3,3,2,6,5,11,0,0,6,1,0,16,3,1,1,5,2,15,7,5,1,10,2,7,1,0 \",\"blueMiss\":\"6,3,14,8,9,7,19,0,11,2,10,5,28,1,26,16\"},{\"issueNo\":\"2014131\",\"winNumber\":\"05,17,21,22,28,32,14\",\"redMiss\":\" 1,1,23,42,0,5,3,2,2,1,5,4,10,15,6,5,0,3,15,2,0,0,4,1,14,6,4,0,9,1,6,0,1 \",\"blueMiss\":\"5,2,13,7,8,6,18,35,10,1,9,4,27,0,25,15\"},{\"issueNo\":\"2014130\",\"winNumber\":\"01,02,10,24,30,33,10\",\"redMiss\":\" 0,0,22,41,1,4,2,1,1,0,4,3,9,14,5,4,2,2,14,1,3,4,3,0,13,5,3,1,8,0,5,1,0 \",\"blueMiss\":\"4,1,12,6,7,5,17,34,9,0,8,3,26,2,24,14\"},{\"issueNo\":\"2014129\",\"winNumber\":\"05,08,09,20,28,32,02\",\"redMiss\":\" 6,2,21,40,0,3,1,0,0,2,3,2,8,13,4,3,1,1,13,0,2,3,2,1,12,4,2,0,7,8,4,0,5 \",\"blueMiss\":\"3,0,11,5,6,4,16,33,8,15,7,2,25,1,23,13\"},{\"issueNo\":\"2014128\",\"winNumber\":\"05,07,08,17,18,24,14\",\"redMiss\":\" 5,1,20,39,0,2,0,0,6,1,2,1,7,12,3,2,0,0,12,4,1,2,1,0,11,3,1,5,6,7,3,3,4 \",\"blueMiss\":\"2,17,10,4,5,3,15,32,7,14,6,1,24,0,22,12\"},{\"issueNo\":\"2014127\",\"winNumber\":\"02,10,12,21,23,27,12\",\"redMiss\":\" 4,0,19,38,9,1,7,16,5,0,1,0,6,11,2,1,1,9,11,3,0,1,0,3,10,2,0,4,5,6,2,2,3 \",\"blueMiss\":\"1,16,9,3,4,2,14,31,6,13,5,0,23,28,21,11\"},{\"issueNo\":\"2014126\",\"winNumber\":\"06,11,16,17,22,27,01\",\"redMiss\":\" 3,2,18,37,8,0,6,15,4,1,0,6,5,10,1,0,0,8,10,2,14,0,6,2,9,1,0,3,4,5,1,1,2 \",\"blueMiss\":\"0,15,8,2,3,1,13,30,5,12,4,43,22,27,20,10\"},{\"issueNo\":\"2014125\",\"winNumber\":\"10,11,15,26,31,32,06\",\"redMiss\":\" 2,1,17,36,7,2,5,14,3,0,0,5,4,9,0,3,1,7,9,1,13,4,5,1,8,0,18,2,3,4,0,0,1 \",\"blueMiss\":\"31,14,7,1,2,0,12,29,4,11,3,42,21,26,19,9\"},{\"issueNo\":\"2014124\",\"winNumber\":\"02,17,20,24,31,33,04\",\"redMiss\":\" 1,0,16,35,6,1,4,13,2,7,1,4,3,8,6,2,0,6,8,0,12,3,4,0,7,5,17,1,2,3,0,9,0 \",\"blueMiss\":\"30,13,6,0,1,14,11,28,3,10,2,41,20,25,18,8\"},{\"issueNo\":\"2014123\",\"winNumber\":\"01,06,11,17,28,33,05\",\"redMiss\":\" 0,2,15,34,5,0,3,12,1,6,0,3,2,7,5,1,0,5,7,1,11,2,3,11,6,4,16,0,1,2,10,8,0 \",\"blueMiss\":\"29,12,5,3,0,13,10,27,2,9,1,40,19,24,17,7\"},{\"issueNo\":\"2014122\",\"winNumber\":\"06,09,11,16,20,29,11\",\"redMiss\":\" 1,1,14,33,4,0,2,11,0,5,0,2,1,6,4,0,3,4,6,0,10,1,2,10,5,3,15,1,0,1,9,7,8 \",\"blueMiss\":\"28,11,4,2,29,12,9,26,1,8,0,39,18,23,16,6\"}]}}}";


   private int mBallWH=0;

   public TrendView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mBallWH=getResources().getDimensionPixelSize(R.dimen.trend_ball_wh);
        initSource();
        initData();
   }
   /* public TrendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSource();
        initData();
    }*/

    /***
     * 初始化资源
     */
   private void initSource(){
        int dpValue=getScreenDenisty();
        //网格线画笔
        mPaintLine=new Paint();
        mPaintLine.setColor(Color.GRAY);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStrokeWidth(dpValue*0.6f/160);

        //小球上面的文字画笔
        mPaintText=new Paint();
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextSize((dpValue*12/160));
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(2f);

        //小球之间连线画笔
        mLinkLine=new Paint();
        mLinkLine.setColor(Color.BLUE);
        mLinkLine.setAntiAlias(true);
        mLinkLine.setStrokeWidth(dpValue*0.6f/160);

        //小球画笔
       mBallPaint=new Paint();
       mBallPaint.setAntiAlias(true);
       mRedBitmapBall= BitmapFactory.decodeResource(getResources(), R.drawable.aicai_lottery_trend_red_down);
       mBlueBitmapBall=BitmapFactory.decodeResource(getResources(),R.drawable.aicai_lottery_trend_blue_down);

       //设置单个网格的水平和垂直间距
       this.mDeltaY=mBallWH*2f;

       Log.i("delta","deltay:"+mDeltaY);//高度;50

       this.mDeltaX=this.mDeltaY;
   }

    /****
     * 初始化数据,取得红球和蓝球分布在网格的位置
     */
   private void initData(){
        try {
            JSONObject jsonObject=new JSONObject(jsonStr);
            JSONObject rss=jsonObject.getJSONObject("rss");
            JSONObject list=rss.getJSONObject("list");
            JSONArray item=list.getJSONArray("item");
            mBallList=new ArrayList<>();
            for (int i=0;i<item.length();i++){
                TrendModel tm=new TrendModel();
                JSONObject childObject=item.getJSONObject(i);
                tm.setIssueNo(childObject.getString("issueNo"));
                tm.setWinNumber(childObject.getString("winNumber"));
                tm.setBlueMiss(childObject.getString("blueMiss"));
                tm.setRedMiss(childObject.getString("redMiss"));
                mBallList.add(tm);
            }
            Log.i("trendtag","行数:"+mBallList.size());
        } catch (JSONException e) {
            Log.i("trendtag","日志:"+e.getMessage());
        }
   }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置测量View的大小:宽度和高度
        setMeasuredDimension((int) ((mRedNum + mBlueNum) * mDeltaX), (int) (mBallList.size() * mDeltaY));
        //取得测量之后当前View的宽度
        this.mWidth = getMeasuredWidth();
        //取得测量之后当前View的高度
        this.mHeight = getMeasuredHeight();
        //重新绘制,不重绘,不会生效;
        invalidate();
   }

   @Override
   protected void onDraw(Canvas canvas) {
       super.onDraw(canvas);
       drawXYLine(canvas);
       drawRedBall(canvas);
       drawBlueBall(canvas);
       drawLinkLine(canvas);
   }

    /***
     * 绘制红球在网格中的分布图
     * @param canvas 画布
     */
   private void drawRedBall(Canvas canvas) {
        mBallPaint.setARGB(255,255,68,68);
       //保存每期的开奖号码;蓝球
       mSaveBlueNum=new String[mBallList.size()];
        //最外面控制期号
        for (int i=0;i<mBallList.size();i++){
            TrendModel trendModel=mBallList.get(i);
            String winNum=trendModel.getWinNumber();
            //拆分中奖号码:红球和蓝球;
            String[] numArr=winNum.split("[,+]");
            //test:04,05,08,11,21,27,08,01
            //源图矩形位置left,top,right,bottom
            Rect src = new Rect();
            //目标位置left,top,right,bottom
            Rect dst = new Rect();
            //最里面控制（绘制）每期所在行,红球和蓝球分布位置
           for (int j=0;j<numArr.length;j++){
               int number=Integer.parseInt(numArr[j]);
               float[] xy = this.translateBallXY(i, number - 1);
               src.left = 0;
               src.top = 0;
               src.bottom = mBallWH;
               src.right =mBallWH;

               dst.left = (int) (xy[0] + this.mDeltaX * 0.05f);
               dst.top = (int)(xy[1]+ this.mDeltaY * 0.1f);
               dst.bottom = (int) (dst.top + mDeltaY * 0.8f);
               dst.right = (int) (dst.left + mDeltaX * 0.85f);
               RectF rf=new RectF(dst.left,dst.top,dst.right,dst.bottom);
               if(j<numArr.length-2){
                   //这个是画圆
                   canvas.drawOval(rf,mBallPaint);
                   //如果有合适的小球图片,就用下面的方法...上面的去掉
                   //这个是画图片
                   //canvas.drawBitmap(mRedBitmapBall,src,dst,mBallPaint);

                   //显示的数字小于10
                   if(number<10){
                       //居中显示:占一个字符
                       canvas.drawText(" " + String.valueOf(number), dst.left + src.right / 2, dst.top + this.mDeltaY * 0.5f, mPaintText);
                   }else{
                       canvas.drawText(String.valueOf(number), dst.left + src.right / 2, dst.top + this.mDeltaY * 0.5f, mPaintText);
                   }
               }else if(j==numArr.length-1){
                   //存储篮球号码...
                   mSaveBlueNum[i]=numArr[j];
               }
           }
        }
   }

    /***
     * 绘制蓝球
     * @param canvas
     */
    private void drawBlueBall(Canvas canvas) {
        mBallPaint.setARGB(255,51,181,229);
        Rect src=new Rect();
        Rect dst=new Rect();
        //最外面控制期号
        for (int i=0;i<mBallList.size();i++){
            int value = Integer.parseInt(mSaveBlueNum[i]);
            float[] xy = this.translateBallXY(i, value - 1);
            //重新定位蓝球在x轴的位置;在红球后面;
            xy[0]=xy[0]+mRedNum*mDeltaX;
            src.left = 0;
            src.top = 0;
            src.bottom =  mBallWH;
            src.right = mBallWH;

            dst.left = (int) (xy[0] + this.mDeltaX * 0.05f);
            dst.top = (int)(xy[1]+ this.mDeltaY * 0.1f);
            dst.bottom = (int) (dst.top + mDeltaY * 0.8f);
            dst.right = (int) (dst.left + mDeltaX * 0.85f);

            RectF rf=new RectF(dst.left,dst.top,dst.right,dst.bottom);
            //画圆
            canvas.drawOval(rf,mBallPaint);

            //如果有合适的小球图片,就用下面的方法...上面的去掉
            //在对应的位置画球球
           // canvas.drawBitmap(mBlueBitmapBall, src, dst, this.mBallPaint);
            if(value<10){
                //居中显示:占一个字符
                canvas.drawText(" " + String.valueOf(value), dst.left + src.right / 2, dst.top + this.mDeltaY * 0.5f, mPaintText);
            }else{
                canvas.drawText(String.valueOf(value), dst.left + src.right / 2, dst.top + this.mDeltaY * 0.5f, mPaintText);
            }
        }
    }

    /**
     * 蓝球之间的连线
     * @param canvas
     */
    private void drawLinkLine(Canvas canvas) {
        //先获取第一个球的x轴和y轴位置
        float[] lastXy = this.translateBallXY(0, Integer.parseInt(mSaveBlueNum[0]) - 1);
        //重新定位:当前X轴的位置还要加上第33号红球的位置...
        lastXy[0]=lastXy[0]+mRedNum*mDeltaX;
        for (int i = 1 ;i < mSaveBlueNum.length;i ++) {
            int value =Integer.parseInt( mSaveBlueNum[i]);
            float[] xy = this.translateBallXY(i, value - 1);
            //重新定位...同上;
            xy[0]=xy[0]+mRedNum*mDeltaX;
            //画两个球的中心点的连线...
            canvas.drawLine(lastXy[0]+ mDeltaX * 0.5f,lastXy[1]+ mDeltaY * 0.5f , xy[0]+ mDeltaX * 0.5f,xy[1]+ mDeltaY * 0.5f , mLinkLine);
            //赋值....
            lastXy[0] = xy[0];
            lastXy[1] = xy[1];
        }
    }

    /***
     * 绘制X轴和Y轴的网格线
     * @param canvas 画布
     */
   private void drawXYLine(Canvas canvas) {
          //期号数:X轴;含顶部和底部的边角线;
          for (int i = 0;i <= mBallList.size();i ++) {
              canvas.drawLine(0, this.mDeltaY * i, this.mWidth, this.mDeltaY * i, mPaintLine);
          }
          //33个红球+16个篮球:Y轴;含最左边的边角和最右边的边角线;
          for (int i = 0;i <= (mRedNum+mBlueNum);i ++) {
              canvas.drawLine(this.mDeltaX * i , 0, this.mDeltaX * i, this.mHeight, mPaintLine);
          }
   }

    /**
     * 返回小球坐标
     * @param rowIndex 行索引X轴;
     * @param valueIndex  当前中奖号码数字:相当于在哪个位置处:Y轴的索引.
     * @return
     */
    private float[] translateBallXY(int rowIndex, int valueIndex) {
        float[] xy = new float[2];
        xy[0] = this.mDeltaX* valueIndex ;
        xy[1] = this.mDeltaY * rowIndex;
        return xy;
    }

    /**
     * 获取当前屏幕的密度
     * @return
     */
    public int getScreenDenisty(){
        DisplayMetrics dm=getResources().getDisplayMetrics();
        return dm.densityDpi;
    }
}