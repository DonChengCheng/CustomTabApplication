package com.example.allen.customtabapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.soundcloud.lightcycle.ActivityLightCycleDispatcher;
import com.soundcloud.lightcycle.LightCycles;

import javax.inject.Inject;

/**
 * Created by allen on 2016/12/2.
 */

public class MainPresenter extends ActivityLightCycleDispatcher<MainActivity> {
    @Inject
    MainPresenter() {
    }

    @Override
    public void onCreate(MainActivity activity, @Nullable Bundle bundle) {
        super.onCreate(activity, bundle);
        activity.sayHello("hello world");
    }

    public void showInfo(MainActivity activity) {
        activity.sayHello("i success");
    }
}
