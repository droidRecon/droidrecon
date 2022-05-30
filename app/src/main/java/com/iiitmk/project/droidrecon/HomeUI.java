package com.iiitmk.project.droidrecon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class HomeUI extends AppCompatActivity {

    Button btnSaved,btnNormal,btnStaged,btnWifi,btnLogout;
    EditText edTarget;
    TextView txt;
    ProgressBar mProgressbar;

    Handler ObjHandler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            mProgressbar = (ProgressBar) findViewById(R.id.progressBarHomeUi);
            Bundle Obj2Bundle = msg.getData();
//            String myMsg2 = Obj2Bundle.getString("ADC");
//            if (myMsg2.equals("1")){
//                mProgressbar.setVisibility(View.VISIBLE);
//            }
            //String[] myMsg = Obj2Bundle.getStringArray("EFG");
            //Map<String,Map> myMsg = (Map<String, Map>) Obj2Bundle.get("EFG");
            String myMsg = Obj2Bundle.getString("EFG");
            txt = (TextView) findViewById(R.id.textview);
            txt.setText(myMsg.toString());
           // mProgressbar.setVisibility(View.INVISIBLE);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ui);

        edTarget = (EditText) findViewById(R.id.targetHomeUI);
        btnSaved = (Button) findViewById(R.id.saveHomeUI);
        btnNormal = (Button) findViewById(R.id.normalScanHomeUI);
        btnStaged = (Button) findViewById(R.id.stagedScanHomeUI);
        btnWifi = (Button) findViewById(R.id.wifiScanHomeUI);
        btnLogout = (Button) findViewById(R.id.logoutHomeUI);
        mProgressbar = (ProgressBar) findViewById(R.id.progressBarHomeUi);
        mProgressbar.setVisibility(View.INVISIBLE);


        btnSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SavedResults.class));
            }
        });
        btnStaged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),StagedHome.class));
            }
        });
        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),WifiActivity.class));
            }
        });

        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        PyObject pyObject = py.getModule("main");


        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Scanning on normal", Toast.LENGTH_SHORT).show();

                String host = edTarget.getText().toString().trim();

                //making new thread...



                Runnable ObjRunnable = new Runnable() {
                    Message ObjMessage = ObjHandler.obtainMessage();
                    Bundle ObjBundle = new Bundle();

                    @Override
                    public void run() {
                        //mProgressbar.setVisibility(View.VISIBLE);

                        //String domain = "x";
                        //String subDomains = "x";
                        //String innerDomain = "x";
                        //String status = "x";
                        //String sdomain = "x";
                        //btnNormal.setEnabled(false);
                        //Maing the ProgressBar Visible and Button Invisible...
                        ObjHandler.post(new Runnable() {
                            @SuppressLint("WrongConstant")
                            @Override
                            public void run() {
                                mProgressbar.setVisibility(0);
                                btnNormal.setEnabled(false);
                            }
                        });


                        //ObjBundle.putString("ADC","1");

                        Map<PyObject, PyObject> obj = pyObject.callAttr("main",host).asMap();
//
//
//                        //getting the domain name...
//                        String domain = obj.get("domain").toString();
//                        ObjBundle.putString("EFGx", domain);
//                        //getting the 1st subdomain detials...
//                        List<PyObject> subDomains = obj.get("subDomains").asList();
//                        ObjBundle.putString("EFGx", String.valueOf(subDomains.get(0)));
//                        //getting the detials of sub domains...
//                        PyObject innerdomainName = obj.get("subDomains");
//                        ObjBundle.putString("EFGx", innerdomainName.toString());
//
//                        Map<PyObject,PyObject> innerdomainDet = obj.get("subDomains").asList().get(0).asMap();
//                        String inn = innerdomainDet.get("status").toString();
                        //#1.PyObject obj = pyObject.callAttr("main",host);
                        //List<PyObject> subDomains = obj.get(0).asList();
                        ObjBundle.putString("EFG", obj.toString());


                        ObjMessage.setData(ObjBundle);

                        ObjHandler.sendMessage(ObjMessage);


                        //Maing the ProgressBar INVisible and Button Visible...
                        ObjHandler.post(new Runnable() {
                            @SuppressLint("WrongConstant")
                            @Override
                            public void run() {
                                btnNormal.setEnabled(true);
                                mProgressbar.setVisibility(4);
                            }
                        });
                    }
                };
                Thread ObjBgThread = new Thread(ObjRunnable);
                ObjBgThread.start();
            }
        });

    }

}