package com.pangu.directorview;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.pangu.directorview.MainView;
import com.pangu.directorview.R;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public class Longin extends Activity {
  private EditText editText ;
  private TextView Iptext ;
  public static String dip  = "19.18.17.209";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.longin);
        editText = findViewById(R.id.dip);
        Iptext = findViewById(R.id.Ip);
        Iptext.setText("本机IP："+getIP(this));

    }

    public String getIP(Context context){

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


    public void join(View view){
      dip =  editText.getText().toString();

      if (dip.length()<7 ){
          Toast.makeText(this,"18.18.18.1",Toast.LENGTH_LONG).show();
          dip="18.18.18.1";
      }

        Intent intent = new Intent(this, MainView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }



}
