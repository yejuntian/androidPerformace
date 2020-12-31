package com.aixuexi.androidperformace.task.delayinittask;


import com.aixuexi.androidperformace.launchstarter.task.MainTask;
import com.aixuexi.androidperformace.utils.LogUtils;

public class DelayInitTaskB extends MainTask {

    @Override
    public void run() {
        // 模拟一些操作

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.i("DelayInitTaskB finished");
    }
}
