package org.devloper.melody.lotterytrend.dao;

/**
 * Created by fuqiang on 2014/12/30.
 *
 * 在ScrollView的onScrollChanged函数中回调下面的方法....
 */
public interface ScrollChangeCallback {
    //水平方向的改变
    void changeXScroll(int left);

    //垂直方向的改变
    void changeYScoll(int top);
}
