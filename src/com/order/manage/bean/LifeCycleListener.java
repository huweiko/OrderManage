package com.order.manage.bean;


public interface LifeCycleListener {
    public abstract void onActivityCreated(MonitoredActivity activity);

    public void onActivityDestroyed(MonitoredActivity activity);

    public void onActivityPaused(MonitoredActivity activity);
 
    public void onActivityResumed(MonitoredActivity activity);

    public void onActivityStarted(MonitoredActivity activity);

    public void onActivityStopped(MonitoredActivity activity);
}
