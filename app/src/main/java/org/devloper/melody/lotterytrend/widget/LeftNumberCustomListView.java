package org.devloper.melody.lotterytrend.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by fuqiang on 2014/12/30.
 * 自定义的ListView防止ScrollView嵌套导致ListView显示不正常....
 */
public class LeftNumberCustomListView extends ListView {
    public LeftNumberCustomListView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightExpand=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>1,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightExpand);
    }
}
