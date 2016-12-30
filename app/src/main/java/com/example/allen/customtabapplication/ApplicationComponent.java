package com.example.allen.customtabapplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by allen on 2016/12/2.
 */
@Singleton
@Component(modules = AndroidModule.class)
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
}
