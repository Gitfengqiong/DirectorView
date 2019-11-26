package com.pangu.directorview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.*;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.pangu.directorview.Longin.dip;
import static com.pangu.directorview.Video_play.ijkMediaPlayers;


public  class MainView extends Activity {
   private HttpControl controls;

    private static final String TAG = "MainActivity";
    private String path ,path1 , path2 , path3;
    //private HashMap<String, String> options;
    private SurfaceView dview , view1s , view2s , view3s;
    private LinearLayout l ;
    private TextView systext;
    private EditText ip,gate,mask,drtmp;
    public IjkMediaPlayer mediaPlayer;
    public  SurfaceHolder viewH[] = new  SurfaceHolder[4];
    public  static boolean test = false ;
    private DrawerLayout drawerLayout ;
    public static String sysconf =" ";
    private Handler uihandl ;
    public  String subconf[] ;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        controls = new HttpControl(dip);
        Log.d("IP",getIP(this));

        dview =  findViewById(R.id.dview);
        view1s =  findViewById(R.id.view1);
        view2s =  findViewById(R.id.view2);
        view3s =  findViewById(R.id.view3);

        drawerLayout =findViewById(R.id.draw);
        l =findViewById(R.id.Mview);
        systext = findViewById(R.id.Statu);
        ip =  findViewById(R.id.ips);
        gate =findViewById(R.id.setgate);
        mask =findViewById(R.id.setmask);
        drtmp = findViewById(R.id.setrtd);
//        IjkMediaPlayer.loadLibrariesOnce(null);
   //     IjkMediaPlayer.native_profileBegin("libijkplayer.so");


        viewH[0] = dview.getHolder();
        viewH[1] = view1s.getHolder();
        viewH[2] = view2s.getHolder();
        viewH[3] = view3s.getHolder();
         uihandl =new Handler();

        String[] uri = new String[4];
        uri[0] = "rtsp://admin:888888@"+dip+":8559/1?tcp";
        uri[1] = "rtsp://admin:888888@"+dip+":8555/1?tcp";
        uri[2] = "rtsp://admin:888888@"+dip+":8557/1?tcp";
      //  uri[0] = "http://192.168.0.34:10000/3";
      //  uri[1] = "http://192.168.0.34:10000/1";
      //  uri[2] = "http://192.168.0.34:10000/2";
      //  uri[3] = "rtsp://192.168.0.101:8554/1" ;
        uri[3] = "/storage/emulated/0/v1.mp4" ;
        test =true ;

        Video_play video_play = new Video_play();
        video_play.play_on(4,viewH,uri);

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                //设置拉出布局的宽度
           //     l.setX(slideOffset * drawerView.getWidth());

               // Log.e(TAG, "onDrawerSlide: " + "滑动时执行");
               // Log.e(TAG, "onDrawerSlide偏移量: " + slideOffset);
               // Log.e(TAG, "onDrawerSlide偏移的宽度: " + drawerView.getWidth());
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
             //   Log.e(TAG, "onDrawerSlide: " + "完全展开时执行");
                controls.post("name44_submit","配置","0","0",getString(R.string.Control_Set_Url));
                synchronized (this){
                    try {
                        wait(300);
                        systext.setText(sysconf);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (sysconf.length() > 30){
                    subconf = sysconf.split("\n");
                    new Thread(
                    ){
                        @Override
                        public void run() {
                            super.run();
                            uihandl.post(conf);
                        }
                    }.start();


                }

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                //Log.e(TAG, "onDrawerSlide: " + "完全关闭时执行");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                //Log.e(TAG, "onDrawerSlide: " + "改变状态时时执行");
            }
        });

    }

     Runnable conf =new Runnable() {
         @Override
         public void run() {
             ip.setHint(subconf[1].substring(3));
             mask.setHint(subconf[2].substring(8));
             gate.setHint(subconf[3].substring(8));
         }
     };
    public  String  getIP(Context context){

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address))
                    {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException ex){
            ex.printStackTrace();
        }
        return null;
    }


    private  void cup1c(){
        new Thread(){
            @Override
            public void run() {
                super.run();

                controls.post("tcp1_s","tcp1_s","0","0",getString(R.string.Control_Det_Url));

            }
        }.start();

    }

    private void  cup2c(){

            new Thread(){
                @Override
                public void run() {
                    super.run();

                    controls.post("tcp2_s","tcp2_s","0","0",getString(R.string.Control_Det_Url));

                }
            }.start();

        }


    private void  cup3c(){

        new Thread(){
            @Override
            public void run() {
                super.run();

                controls.post("tcp3_s","tcp3_s","0","0",getString(R.string.Control_Det_Url));

            }
        }.start();

    }

    //set dev New Ip
    public void setIp(View view){

        EditText getip = findViewById(R.id.ips);
        String ip =  getip.getText().toString();

        if(ip.length() < 7){
            Toast.makeText(this,"IP地址错误",Toast.LENGTH_LONG).show();
            return;
        }

        controls.post("name1_submit","set","ip",ip,getString(R.string.Control_Set_Url));
         dip  = ip;

         synchronized (this){
             try {
                 wait(300);
                 controls.get(getString(R.string.Control_Reb_Url));

             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }

         Toast.makeText(this,"修改IP中...设备重启后请用新地址登录",Toast.LENGTH_LONG).show();

        this.finish();

    }

    public void setmask(View view){

        EditText getip = findViewById(R.id.setmask);
        String ip =  getip.getText().toString();

        if(ip.length() < 7){
            Toast.makeText(this,"Mask地址错误",Toast.LENGTH_LONG).show();
            return;
        }

        controls.post("name2_submit","set","netmask",ip,getString(R.string.Control_Set_Url));
       // dip  = ip;

        synchronized (this){
            try {
                wait(300);
                controls.get(getString(R.string.Control_Reb_Url));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(this,"修改mask中...设备重启后请登录",Toast.LENGTH_LONG).show();

        this.finish();

    }

    public void setgate(View view){

        EditText getip = findViewById(R.id.setgate);
        String ip =  getip.getText().toString();

        if(ip.length() < 7){
            Toast.makeText(this,"Gate地址错误",Toast.LENGTH_LONG).show();
            return;
        }

        controls.post("name45_submit","set","gateway",ip,getString(R.string.Control_Set_Url));
       // dip  = ip;

        synchronized (this){
            try {
                wait(300);
                controls.get(getString(R.string.Control_Reb_Url));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(this,"修改Gate中...设备重启后请登录",Toast.LENGTH_LONG).show();

        this.finish();

    }


    /*********TS  Adrres SET *********/
    /*
    public  void setts_1(View view){


        EditText geturi = findViewById(R.id.setts1);
        String uri =  geturi.getText().toString();
        
        String ip="" , port = "";
        
        for (int i=0 ; i<uri.length() ; ){
            String subs = uri.substring(i,++i);

            if (subs.equals(":")){
                port = uri.substring(i,uri.length());
                break;
            }
            ip = ip+subs;
        }
      //  Log.d("uri:",ip+":"+port);

        synchronized (this){
            try {

                controls.post("namez_submit","","tz",ip,"cgi-bin/vgaic3.cgi");
                wait(300);
                controls.post("namec_submit","","tc",port,"cgi-bin/vgaic3.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }
    public  void setts_2(View view){


        EditText geturi = findViewById(R.id.setts2);
        String uri =  geturi.getText().toString();

        String ip="" , port = "";

        for (int i=0 ; i<uri.length() ; ){
            String subs = uri.substring(i,++i);

            if (subs.equals(":")){
                port = uri.substring(i,uri.length());
                break;
            }
            ip = ip+subs;
        }
        //  Log.d("uri:",ip+":"+port);

        synchronized (this){
            try {

                controls.post("namez_submit","","tz",ip,"cgi-bin/vgaic4.cgi");
                wait(300);
                controls.post("namec_submit","","tc",port,"cgi-bin/vgaic4.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }
    */
    public  void setts_d(View view){


        EditText geturi = findViewById(R.id.settsd);
        String uri =  geturi.getText().toString();

        String ip="" , port = "";

        for (int i=0 ; i<uri.length() ; ){
            String subs = uri.substring(i,++i);

            if (subs.equals(":")){
                port = uri.substring(i,uri.length());
                break;
            }
            ip = ip+subs;
        }
        //  Log.d("uri:",ip+":"+port);

        synchronized (this){
            try {

                controls.post("namez_submit","set","tz",ip,"cgi-bin/vgaic.cgi");
                wait(300);
                controls.post("namec_submit","set","tc",port,"cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }

    /********RTMP  URI  SET  **********/
/*
    public  void setrt_1(View view )
    {
        EditText geturi = findViewById(R.id.setrt1);
        String uri =  geturi.getText().toString();

        if (uri.length() < 7){
            Toast.makeText(this,"URL格式错误",Toast.LENGTH_LONG).show();
            return;
        }

        if (!uri.substring(0,1).equals("r")||!uri.substring(0,1).equals("R")){

            uri = "rtmp://"+uri;

        }

        synchronized (this){
            try {

                wait(300);
                controls.post("rtmpurl_submit","set","rtmpurl",uri,"cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public  void setrt_2(View view){

        EditText geturi = findViewById(R.id.setrt2);
        String uri =  geturi.getText().toString();

        if (uri.length() < 7){
            Toast.makeText(this,"URL格式错误",Toast.LENGTH_LONG).show();
            return;
        }

        if (!uri.substring(0,1).equals("r")||!uri.substring(0,1).equals("R")){

            uri = "rtmp://"+uri;

        }

        synchronized (this){
            try {

                wait(300);
                controls.post("rtmpurl_submit2","set","rtmpurl2",uri,"cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
    */
    public void setrt_d(View view){

        EditText geturi = findViewById(R.id.setrtd);
        String uri =  geturi.getText().toString();

        if (uri.length() < 7){
            Toast.makeText(this,"URL格式错误",Toast.LENGTH_LONG).show();
            return;
        }

        if (!uri.substring(0,1).equals("r")||!uri.substring(0,1).equals("R")){

            uri = "rtmp://"+uri;

        }

        synchronized (this){
            try {

                wait(300);
                controls.post("rtmpurl_submit8","set","rtmpurl8",uri,"cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    /*******STREAM URL SET*********/

    public void setin_s(View view){
        EditText geturi = findViewById(R.id.setrts);
        String uri =  geturi.getText().toString();

        if (uri.length() < 7){
            Toast.makeText(this,"URL格式错误",Toast.LENGTH_LONG).show();
            return;
        }


        synchronized (this){
            try {

                wait(300);
                controls.post("rtmpurl_submit5","set","rtmpurl5",uri,"cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
    // Stream decode delay
    public void setin_d(View view){

        EditText geturi = findViewById(R.id.setdly);
        String uri =  geturi.getText().toString();

        if (Integer.parseInt(uri) > 4000){
            uri ="4000";
        }



        synchronized (this){
            try {

                wait(300);
                controls.post("delay_submit","set","D_delay",uri,"cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /********** Set Dev RUN ...*********/

    public void d_on(View view){

        synchronized (this){
            try {

                wait(300);
                controls.post("decode_submit","set","decode_stream","female","cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void d_off(View v ){

        synchronized (this){
            try {

                wait(300);
                controls.post("decode_submit","set","decode_stream","male","cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void Rt_on(View view){
        synchronized (this){
            try {

                wait(300);
                controls.post("r_stream_submit","set","r_stream","female","cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public  void Rt_off(View view){

        synchronized (this){
            try {

                wait(300);
                controls.post("r_stream_submit","set","r_stream","male","cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void Ts_udp(View view){
        synchronized (this){
            try {

                wait(300);
                controls.post("codec_submit","set","ctrlaz","male","cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void Ts_http(View view){

        synchronized (this){
            try {

                wait(300);
                controls.post("codec_submit","set","ctrlaz","amale","cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void Ts_off(View view){
        synchronized (this){
            try {

                wait(300);
                controls.post("codec_submit","set","ctrlaz","female","cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void Rts_on(View view){
        synchronized (this){
            try {

                wait(300);
                controls.post("rtsp_submit","set","rtsp_stream","female","cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void Rts_off(View view){

        synchronized (this){
            try {

                wait(300);
                controls.post("rtsp_submit","set","rtsp_stream","male","cgi-bin/vgaic.cgi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    //default Button off
    public void  other(View view){


    }


    public void C_handl(View view){
        controls.get(getString(R.string.Control_Hda_Url));
    }

    public void reboot(View  view ){

        controls.get(getString(R.string.Control_Reb_Url));
    }


    /********Cup View to Out Director *********/

    @SuppressLint("NewApi")
    public  void vset(View view){

        /***************单路播放声音功能无法实现，等后期解决************/
        SurfaceView v = (SurfaceView) view;
        int vid =  v.getId();
        if (vid == R.id.dview){
            dview.setBackground(getDrawable(R.drawable.shap_client));
            view1s.setBackground(getDrawable(R.drawable.shap_over));
            view2s.setBackground(getDrawable(R.drawable.shap_over));
            view3s.setBackground(getDrawable(R.drawable.shap_over));
/*
            ijkMediaPlayers[0].setVolume(1f,1f);
            ijkMediaPlayers[1].setAudioStreamType(AudioManager.STREAM_ALARM);
            ijkMediaPlayers[2].setAudioStreamType(AudioManager.STREAM_ALARM);
            ijkMediaPlayers[3].setAudioStreamType(AudioManager.STREAM_ALARM);
            ijkMediaPlayers[1].setVolume(0f,0f);
            ijkMediaPlayers[2].setVolume(0f,0f);
            ijkMediaPlayers[3].setVolume(0f,0f);

            Log.d("case:","dview");
*/
        }
        if (vid == R.id.view1){

            dview.setBackground(getDrawable(R.drawable.shap_over));
            view1s.setBackground(getDrawable(R.drawable.shap_client));
            view2s.setBackground(getDrawable(R.drawable.shap_over));
            view3s.setBackground(getDrawable(R.drawable.shap_over));
            cup1c();
/*
            ijkMediaPlayers[0].setVolume(1f,1f);
            ijkMediaPlayers[1].setVolume(0f,0f);
            ijkMediaPlayers[2].setVolume(1f,1f);
            ijkMediaPlayers[3].setVolume(1f,1f);

            Log.d("case:","view1");
*/
        }

        if (vid == R.id.view2){

            dview.setBackground(getDrawable(R.drawable.shap_over));
            view1s.setBackground(getDrawable(R.drawable.shap_over));
            view2s.setBackground(getDrawable(R.drawable.shap_client));
            view3s.setBackground(getDrawable(R.drawable.shap_over));
            cup2c();
/*
            ijkMediaPlayers[0].setVolume(1f,1f);
            ijkMediaPlayers[1].setVolume(1f,1f);
            ijkMediaPlayers[2].setVolume(0f,0f);
            ijkMediaPlayers[3].setVolume(1f,1f);

            Log.d("case:","view2");
*/
        }

        if (vid == R.id.view3){

            dview.setBackground(getDrawable(R.drawable.shap_over));
            view1s.setBackground(getDrawable(R.drawable.shap_over));
            view2s.setBackground(getDrawable(R.drawable.shap_over));
            view3s.setBackground(getDrawable(R.drawable.shap_client));
            cup3c();
/*
            ijkMediaPlayers[0].setVolume(1f,1f);
            ijkMediaPlayers[1].setVolume(1f,1f);
            ijkMediaPlayers[2].setVolume(1f,1f);
            ijkMediaPlayers[3].setVolume(0f,0f);

            Log.d("case:","view3");
*/
        }
    }

}

