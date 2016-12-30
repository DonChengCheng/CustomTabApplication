package com.example.allen.customtabapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.soundcloud.lightcycle.LightCycle;
import com.soundcloud.lightcycle.LightCycleAppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

import javax.inject.Inject;


public class MainActivity extends LightCycleAppCompatActivity<MainActivity> {
    @Inject
    @LightCycle
    MainPresenter mainPresenter;
    private TextView titleText;
    private final static String TAG_PERSON = "person";
    private final static String TAG_NAME = "name";
    private final static String TAG_AGE = "age";
    private final static String TAG_SEX = "sex";


    public MainActivity() {
        DaggerApplicationComponent.builder().build().inject(this);
    }

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_main);

        Proxy.newProxyInstance(getClassLoader(), new Class[]{Animal.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                method.invoke(proxy, args);
                return null;
            }
        });
        titleText = (TextView) this.findViewById(R.id.title_text);
        titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Method[] methods = SecondActivity.class.getDeclaredMethods();
                    for(int i=0; i<methods.length; i++) {
                        if(methods[i].isAnnotationPresent(TagInfo.class)) {
                            TagInfo methodAnnation = methods[i].getAnnotation(TagInfo.class);
                            if(methodAnnation != null) {
                                Log.e("TAG-------", methodAnnation.name()+"----------");
                            }
                        }

                    }
                    /*Method method = SecondActivity.class.getDeclaredMethod("newIntent", Context.class);
                    Intent intent = (Intent) method.invoke(null, MainActivity.this);
                    startActivity(intent);*/
//                mainPresenter.showInfo(MainActivity.this);
            }
        });
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            InputStream inputStream = getAssets().open("person.xml");
            xmlPullParser.setInput(inputStream, "utf-8");
            ArrayList<Person> personList = new ArrayList<>();
            Log.e("--1233",  xmlPullParser.getEventType()+"-------");
            while(xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT) {

                if(xmlPullParser.getEventType() == XmlPullParser.START_TAG
                        && !xmlPullParser.getName().equals(TAG_PERSON)) {
                    Person person = new Person();
                    int attributeCount = xmlPullParser.getAttributeCount();
                    for(int i=0; i<attributeCount; i++) {
                        if(xmlPullParser.getAttributeName(i).equals(TAG_NAME)) {
                            person.setName(xmlPullParser.getAttributeValue(i));
                        } else if(xmlPullParser.getAttributeName(i).equals(TAG_AGE)) {
                            person.setAge(xmlPullParser.getAttributeValue(i));
                        } else {
                            person.setSex(xmlPullParser.getAttributeName(i));
                        }
                    }
                    personList.add(person);

                }
                xmlPullParser.next();
            }
            Log.e("fuyu------------------", personList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sayHello(String msg) {
        titleText.setText(msg);
    }


}

