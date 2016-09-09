package org.devloper.melody.lotterytrend.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.devloper.melody.lotterytrend.R;
import org.devloper.melody.lotterytrend.dao.ScrollChangeCallback;
import org.devloper.melody.lotterytrend.widget.HeadCustomGridView;
import org.devloper.melody.lotterytrend.widget.LeftNumberCustomListView;
import org.devloper.melody.lotterytrend.widget.LeftNumberSynchScrollView;
import org.devloper.melody.lotterytrend.widget.TrendScrollViewWidget;
import org.devloper.melody.lotterytrend.widget.HeaderHorizontalScrollView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.devloper.melody.lotterytrend.R.id.tv_content;

/**
 * 走势图activity
 */
public class TrendActivity extends AppCompatActivity implements ScrollChangeCallback {
    //容器区域
    private LeftNumberSynchScrollView mLeftScroll;
    private TrendScrollViewWidget mContentScroll;
    private HeaderHorizontalScrollView mHeadScroll;
    private HeaderHorizontalScrollView mFooterScroll;
    //数据区域
    private LeftNumberCustomListView mListView;
    private GridView  mHeadGridView;
    private GridView mFooterGridView;
    private List mList = null;
    private List mHeadData=null;
    //选好了
    private ImageView mTrendSelectBtn;
    private TextView mTvResult;
    //当前手机屏幕的密度:基准mdpi
    private int mDenisty=160;

    //key为item中设置背景色view的hashCode,唯一;
    //value为-1的时候是未选中;
    //value为1的时候是选中;
    private HashMap<Object,Integer> mContainer=new HashMap<>();
    //选择了多少个球
    private String[] mSelectData=new String[49];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trend);
        mDenisty=getScreenDenisty();
        mListView = (LeftNumberCustomListView) findViewById(R.id.lv_number);
        mHeadGridView= (HeadCustomGridView) findViewById(R.id.grid_trend_header);
        mFooterGridView= (GridView) findViewById(R.id.grid_trend_footer);
        mLeftScroll = (LeftNumberSynchScrollView) findViewById(R.id.scroll_left);
        mContentScroll = (TrendScrollViewWidget) findViewById(R.id.scroll_content);
        mHeadScroll= (HeaderHorizontalScrollView) findViewById(R.id.trend_header_scroll);
        mFooterScroll= (HeaderHorizontalScrollView) findViewById(R.id.trend_footer_scroll);
        mTrendSelectBtn= (ImageView) findViewById(R.id.iv_trend_yes);
        mTvResult= (TextView) findViewById(R.id.tv_result);
        //左边期号的监听器
        mLeftScroll.setScrollViewListener(this);
        //中间走势图的监听器
        mContentScroll.setScrollViewListener(this);
        //走势图顶部的监听器
        mHeadScroll.setScrollViewListener(this);
        //走势图底部的旋球滚动监听器
        mFooterScroll.setScrollViewListener(this);

        mTrendSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealSelectData();
            }
        });
        //绑定显示期号数据
        bindQiHaoData();
        //走势图顶部区域数据显示
        bindHeaderData();
        bindFooterData();
    }



    /***
     * 同步X轴位置
     * @param left
     */
    @Override
    public void changeXScroll(int left) {
        //顶部和底部容器滑动的回调;
        //此时需要同步中间走势的View的位置;
        mContentScroll.scrollTo(left, mContentScroll.getScrollY());
        //同步顶部自身的位置;
        mHeadScroll.scrollTo(left,0);
        mFooterScroll.scrollTo(left,0);
    }

    /***
     * 同步Y轴的位置
     * @param top
     */
    @Override
    public void changeYScoll(int top) {
        //中间走势View滑动位置的改变回调;
        //同步左边期号的Y轴的位置
        mLeftScroll.scrollTo(0, top);
        //同步中间走势View的位置
        mContentScroll.scrollTo(mContentScroll.getScrollX(), top);
        //有走势图头部...
        mHeadScroll.scrollTo(mContentScroll.getScrollX(),0);
        //走势图底部
        mFooterScroll.scrollTo(mContentScroll.getScrollX(),0);
    }

    /**
     * 测试期号的数据绑定显示
     */
    private void bindQiHaoData() {
        mList = new ArrayList();
        //新浪彩票的期号是获取前30期
        for (int i = 0; i < 30; i++) {
            mList.add((20140 + "" + (i+1)));
        }
        DataAdapter adapter = new DataAdapter(R.layout.items);
        adapter.bindData(mList,false);
        mListView.setAdapter(adapter);
    }

    /**
     * 绑定顶部数据显示:顶部数据只显示在一行;
     */
    private void bindHeaderData(){
        mHeadData=new ArrayList();
        //33个红球+16个蓝球;
        for (int i=1;i<=49;i++){
            if(i<=33){
                //红球区域
                mHeadData.add(""+i);
            }else{
                //蓝球区域
                mHeadData.add(""+(i-33));
            }
        }

        DataAdapter adapter= new DataAdapter(R.layout.gridview_item);
        adapter.bindData(mHeadData,false);
        int deltaDp=getResources().getDimensionPixelSize(R.dimen.item_wh);
        //下面的代码是重新定位布局参数;让gridView数据都显示在一行;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adapter.getCount() * deltaDp,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mHeadGridView.setLayoutParams(params);
        mHeadGridView.setColumnWidth(deltaDp);//列宽
        mHeadGridView.setStretchMode(GridView.NO_STRETCH);//伸展模式
        mHeadGridView.setNumColumns(adapter.getCount());//共有多少列
        mHeadGridView.setAdapter(adapter);
    }

    /**
     * 绑定底部选号数据
     */
    private void bindFooterData(){
        DataAdapter adapter= new DataAdapter(R.layout.gridview_item);
        adapter.bindData(mHeadData,true);//使用头部的数据
        int deltaDp=getResources().getDimensionPixelSize(R.dimen.item_wh);
        //下面的代码是重新定位布局参数;让gridView数据都显示在一行;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adapter.getCount() * deltaDp,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mFooterGridView.setLayoutParams(params);
        mFooterGridView.setColumnWidth(deltaDp);//列宽
        mFooterGridView.setStretchMode(GridView.NO_STRETCH);//伸展模式
        mFooterGridView.setNumColumns(adapter.getCount());//共有多少列
        mFooterGridView.setAdapter(adapter);
        /**设置item的点击事件*/
        mFooterGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
               Log.i("TrendActivity","onItemClickListener:"+position);
               final TextView tv_content = (TextView)parent.getChildAt(position).findViewById(R.id.tv_content);

               //取得当前view的hashCode
               int tvHashCode=tv_content.hashCode();
               Log.i("TrendActivity","content_hashCode:"+tvHashCode);
               if(position<33){
                   //犯了低级错误啊。hashMap的key为0的时候,put不进去,郁闷...
                   if(mContainer.get(tvHashCode)==-1){
                       changeSelectColorNumber(tv_content,R.drawable.trend_red_down);
                       mContainer.put(tvHashCode,1);
                       mSelectData[position]=tv_content.getText().toString();
                       Log.i("TrendActivity","==========>down效果");
                   }else{
                       changeSelectColorNumber(tv_content,R.drawable.trend_red_up);
                       mContainer.put(tvHashCode,-1);
                       mSelectData[position]=null;
                       Log.i("TrendActivity","==========>up效果");
                   }
               }else {
                   if(mContainer.get(tvHashCode)==-1){
                        changeSelectColorNumber(tv_content,R.drawable.trend_blue_down);
                        mContainer.put(tvHashCode,1);
                        mSelectData[position]=tv_content.getText().toString();
                   }else{
                        changeSelectColorNumber(tv_content,R.drawable.trend_blue_up);
                        mContainer.put(tvHashCode,-1);
                        mSelectData[position]=null;
                   }
               }
            }
        });
    }

    /***
     * 数据适配器(含期号,走势图顶部数据,走势图底部数据显示)....
     */
    private class DataAdapter extends BaseAdapter {
        private  List listData=null;
        private boolean isFooter=false;
        private int layoutId;
        private int mLeftItemH;
        public DataAdapter(int layoutID){
            this.layoutId=layoutID;
            mLeftItemH=getResources().getDimensionPixelSize(R.dimen.item_wh);
        }
        protected  void bindData(List data,boolean footer){
            this.listData=data;
            this.isFooter=footer;
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(TrendActivity.this).inflate(layoutId, parent, false);
            }
            final TextView tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            if(layoutId==R.layout.items){
                //最近才想起来那时候写的走势图，没有共享出来
                //14年写的，现在是16年了，两年前写的代码了，不想改了，就在这里适配下分割线了,读者可以自行“自定义”item也是比较简单的，
                //两年前我自定义了item，代码找不到了，现在就是为了上传到github上，就这样，凑合看吧
                View line1=convertView.findViewById(R.id.v_line1);
                View line2=convertView.findViewById(R.id.v_line2);
                ViewGroup.LayoutParams vP1=line1.getLayoutParams();
                vP1.height= (int) (getScreenDenisty()*0.6f/160);
                line1.setLayoutParams(vP1);
                line2.setLayoutParams(vP1);
                int diff=mLeftItemH-vP1.height*2;
                ViewGroup.LayoutParams contentParams=tv_content.getLayoutParams();
                contentParams.height=diff;
                tv_content.setLayoutParams(contentParams);
            }else{
                //R.layout.gridview_item
                View line3=convertView.findViewById(R.id.v_line3);
                View line4=convertView.findViewById(R.id.v_line4);
                ViewGroup.LayoutParams vP3=line3.getLayoutParams();
                vP3.width= (int) (getScreenDenisty()*0.6f/160);
                line3.setLayoutParams(vP3);
                line4.setLayoutParams(vP3);
                int diff=mLeftItemH-vP3.width*2;
                RelativeLayout rlContent= (RelativeLayout) convertView.findViewById(R.id.relative_layout);
                ViewGroup.LayoutParams contentParams=rlContent.getLayoutParams();
                contentParams.width=diff;
                rlContent.setLayoutParams(contentParams);
            }
            if(isFooter){
                ViewGroup.LayoutParams contentParams=tv_content.getLayoutParams();
                contentParams.width=getResources().getDimensionPixelSize(R.dimen.footer_itemwh);
                contentParams.height=getResources().getDimensionPixelSize(R.dimen.footer_itemwh);
                tv_content.setLayoutParams(contentParams);
                //设置背景图片:
                if(position<33){
                    changeSelectColorNumber(tv_content,R.drawable.trend_red_up);
                }else{
                    changeSelectColorNumber(tv_content,R.drawable.trend_blue_up);
                }
                mContainer.put(tv_content.hashCode(),-1);
            }
            tv_content.setText(listData.get(position).toString());
            return convertView;
        }
    }

    /***
     * 改变底部选号选中和未被选中的背景色
     * @param tv TextView
     * @param redId drawble中的资源id
     */
    private void changeSelectColorNumber(TextView tv,int redId) {
        tv.setBackgroundDrawable(getResources().getDrawable(redId));
    }

    /**
     * 获取当前屏幕的密度
     * @return
     */
    private int getScreenDenisty(){
        DisplayMetrics dm=getResources().getDisplayMetrics();
        return dm.densityDpi;
    }
    /**
     * 处理选号的球码
     */
    private void dealSelectData() {
        StringBuilder redSb = new StringBuilder();
        StringBuilder blueSb=new StringBuilder();
        for (int i = 0; i < mSelectData.length; i++) {
            String data = mSelectData[i];
            if (null!=data) {
                if (i < 33) {
                    redSb.append(data+",");
                } else {
                    blueSb.append(data+",");
                }
            }
        }
        String rs=redSb.toString();
        String bs=blueSb.toString();
        String redStr="";
        String blueStr="";
        int redNumber=0;
        int blueNumber=0;
        if(!"".equals(rs)){
            redStr =rs.substring(0,rs.lastIndexOf(","));
        }
        if(!"".equals(bs)){
            blueStr=bs.substring(0,bs.lastIndexOf(","));
        }
        if(!"".equals(redStr)){
            redNumber=redStr.split("[,+]").length;
        }
        if(!"".equals(blueStr)){
            blueNumber=blueStr.split("[,+]").length;
        }
        if(redNumber<6){
            Toast.makeText(this, "红球至少选择6个", Toast.LENGTH_SHORT).show();
        }else if(blueNumber<1){
            Toast.makeText(this,"蓝球至少选择1个",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"选好了:"+redStr+"|"+blueStr,Toast.LENGTH_SHORT).show();
        }
        mTvResult.setText(redStr+"|"+blueStr);
    }
}