package com.pangu.directorview;

import android.util.Base64;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import static com.pangu.directorview.MainView.sysconf ;

public class HttpControl {
   private String id = "admin:888888";
   private HttpClient httpClient = new DefaultHttpClient();
   private String ip ;

    public   HttpControl(String ips){
        ip = ips ;
    }

    public void post(final String Key , final String Vaule , final String Key1 , final String Vaule1 , final String Codeurl) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                String url = "http://" + ip + "/" + Codeurl;
                String tokenStr = null;
                try

                {
                    tokenStr = "Basic " + Base64.encodeToString(id.getBytes("utf-8"), Base64.NO_WRAP);
                } catch(
                        UnsupportedEncodingException e)

                {
                    e.printStackTrace();
                }

                //第二步：生成使用POST方法的请求对象
                HttpPost httpPost = new HttpPost(url);
                //NameValuePair对象代表了一个需要发往服务器的键值对
                String val = Vaule ;

                    NameValuePair pair1 = new BasicNameValuePair(Key, val);

                //    NameValuePair pair2 = new BasicNameValuePair("password", pwd);
                //将准备好的键值对对象放置在一个List当中
                ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(pair1);
                if (!Key1.equals("0") ){
                    NameValuePair pair2 = new BasicNameValuePair(Key1,Vaule1);
                    pairs.add(pair2);
                }
                //
                try

                {
                    httpPost.addHeader("Host", ip);
                    httpPost.addHeader("Authorization", tokenStr);
                    httpPost.addHeader("User-Agent", "curl/7.63.0");
                    httpPost.addHeader("Accept", "*/*");
                    httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

                    //创建代表请求体的对象（注意，是请求体）
                    HttpEntity requestEntity = new UrlEncodedFormEntity(pairs,HTTP.UTF_8);
                    //将请求体放置在请求对象当中
                    httpPost.setEntity(requestEntity);
                    //执行请求对象
                    try {
                        //第三步：执行请求对象，获取服务器发还的相应对象
                        HttpResponse response = httpClient.execute(httpPost);
                        //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
                        if (response.getStatusLine().getStatusCode() == 200) {
                            //第五步：从相应对象当中取出数据，放到entity当中
                            HttpEntity entity = response.getEntity();
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(entity.getContent()));
                            String line =null;
                            StringBuilder buff =new StringBuilder();
                            while ((line = reader.readLine())!= null){
                                buff.append(line);
                                buff.append("\n");
                            }
                           sysconf  =  buff.toString();
                           // Log.d("HTTP", "POST:" + result);
                        } else {
                            HttpEntity entity1 = response.getEntity();
                            BufferedReader reader1 = new BufferedReader(
                                    new InputStreamReader(entity1.getContent()));
                            String result1 = reader1.readLine();
                            Log.d("Error:", result1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch(
                        Exception e)

                {
                    e.printStackTrace();
                }
            }

        }.start();
    }

    public void get(final String Codeurl) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                String tokenStr = null;
                try {
                    tokenStr = "Basic " + Base64.encodeToString(id.getBytes("utf-8"), Base64.NO_WRAP);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String urlh = "http://" + ip + "/" + Codeurl;
                HttpGet httpget = new HttpGet(urlh);
                httpget.addHeader("Host", ip);
                httpget.addHeader("Authorization", tokenStr);
                httpget.addHeader("User-Agent", "curl/7.63.0");
                httpget.addHeader("Accept", "*/*");
                try {
                    httpClient.execute(httpget);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }

}

