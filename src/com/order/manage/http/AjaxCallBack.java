/**
 * Copyright (c) 2012-2013, Michael Yang 杨福�?(www.yangfuhai.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.order.manage.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import com.order.manage.bean.LifeCycleListener;
import com.order.manage.bean.MonitoredActivity;

import android.os.AsyncTask;

/**
 * 
 * @author michael
 * 
 * @param <T>
 *            目前泛型支持 String,File, 以后扩展：JSONObject,Bitmap,byte[],XmlDom
 */
public abstract class AjaxCallBack<T> implements LifeCycleListener {

    private boolean progress = true;
    private int rate = 1000 * 1;// 每秒

    // private Class<T> type;
    //
    // public AjaxCallBack(Class<T> clazz) {
    // this.type = clazz;
    // }
    @SuppressWarnings("rawtypes")
    private AsyncTask currTask;

    public boolean isProgress() {
        return progress;
    }

    public int getRate() {
        return rate;
    }

    /**
     * 设置进度,而且只有设置了这个了以后，onLoading才能有效�?
     * 
     * @param progress
     *            是否启用进度显示
     * @param rate
     *            进度更新频率
     */
    public AjaxCallBack<T> progress(boolean progress, int rate) {
        this.progress = progress;
        this.rate = rate;
        return this;
    }

    public void onStart() {
    };

    public void onLoading(long count, long current) {
    };

    public void onSuccess(T t) {
    };

    public void onSuccess(T t, HttpUriRequest request, HttpResponse response) {

    };

    public void onFailure(Throwable t, int errorNo, String strMsg) {
    };

    public void setCurrTask(@SuppressWarnings("rawtypes") AsyncTask task) {
        cancelTask();
        this.currTask = task;
    }

    public void cancelTask() {
        try {
            if (currTask != null) {
                currTask.cancel(true);
                currTask = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // //////////////////////////////////////////////////////////////////////////////
    @Override
    public void onActivityCreated(MonitoredActivity activity) {

    }

    @Override
    public void onActivityDestroyed(MonitoredActivity activity) {
        // 取消任务
        cancelTask();
        // activity.removeLifeCycleListener(this);
    }

    @Override
    public void onActivityPaused(MonitoredActivity activity) {

    }

    @Override
    public void onActivityResumed(MonitoredActivity activity) {

    }

    @Override
    public void onActivityStarted(MonitoredActivity activity) {

    }

    @Override
    public void onActivityStopped(MonitoredActivity activity) {

    }

}
