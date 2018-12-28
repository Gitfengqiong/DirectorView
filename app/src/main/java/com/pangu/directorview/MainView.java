package com.pangu.directorview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;


public  class MainView extends Activity {
   private HttpControl controls;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.longin);
        controls = new HttpControl("192.168.0.34");
    }

    public void cup(View view){
        Log.d("Cup s","cupon");
        String strRecSmsMsg="收短信测试";
        //提交
     //   RecSmsToPost(strRecSmsMsg);
      //  openToast("提交测试完成");

      new Thread(){
          @Override
          public void run() {
              super.run();

                  controls.post("tcp1_s","tcp1_s",getString(R.string.Control_Det_Url));

          }
      }.start();

    }


    public void cup2(View view){


        new Thread(){
            @Override
            public void run() {
                super.run();

                controls.post("tcp2_s","tcp2_s",getString(R.string.Control_Det_Url));

            }
        }.start();

    }

}

