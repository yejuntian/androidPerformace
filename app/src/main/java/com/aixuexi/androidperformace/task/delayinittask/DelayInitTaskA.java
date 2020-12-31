package com.aixuexi.androidperformace.task.delayinittask;


import com.aixuexi.androidperformace.launchstarter.task.MainTask;
import com.aixuexi.androidperformace.utils.LogUtils;

public class DelayInitTaskA extends MainTask {

    @Override
    public void run() {
        // 模拟一些操作
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.i("DelayInitTaskA finished");
    }
}
