package com.iiitmk.project.droidrecon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.stealthcopter.networktools.SubnetDevices;
import com.stealthcopter.networktools.subnet.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WifiActivity extends AppCompatActivity {

    TextView txtStatus,txtOp;
    Button btnScan;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<String> ipList;

    Handler ObjHandler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        txtStatus = (TextView) findViewById(R.id.statusWifiActivity);
        //txtOp = (TextView) findViewById(R.id.opWifiActivity);
        btnScan = (Button) findViewById(R.id.btnScanWifiWifiActivity);

        recyclerView=findViewById(R.id.recyclerviewWifi);

        ipList = new ArrayList<>();

        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findSubnetDevices();
            }
        });


    }

    private void findSubnetDevices() {

        final long startTimeMillis = System.currentTimeMillis();
        txtStatus.setText("Scanning...");
        SubnetDevices subnetDevices = SubnetDevices.fromLocalAddress().findDevices(new SubnetDevices.OnSubnetDeviceFound() {

            @Override
            public void onDeviceFound(Device device) {

            }

            public void onFinished(ArrayList<Device> devicesFound) {
                float timeTaken = (System.currentTimeMillis() - startTimeMillis) / 1000.0f;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtStatus.setText("Scan Finished");
                        for (Device device : devicesFound) {
                            //txtOp.append("Device " + device.hostname + "\n");
                            //txtOp.append("IP : " + device.ip + "\n");


                            ipList.add(device.ip);

                            //String macAddress = ARPInfo.getMacFromArpCache(device.ip);
                            //txtOp.append("Mac : " + device.mac + "\n");
                            //txtOp.append("\n");


                        }

                        List<WifiPort> portTemp;

                        List<PyObject> Wifiobj = Python.getInstance().getModule("wifiScan").callAttr("main", ipList).asList();
                        txtStatus.setText("Scan Completed");
                        //list for adapter...
                        List<WifiData> wifiAdapterList = new ArrayList<>();
                        if(!Wifiobj.isEmpty()){
                            //contain data...
                            for(int i=0;i< Wifiobj.size();i++){
                                Map<PyObject,PyObject> ipDetials = Wifiobj.get(i).asMap();
                                String IPADDRESS = ipDetials.get("ip").toString();

                                WifiData wifiData = new WifiData();
                                portTemp= new ArrayList<>();
                                wifiData.setIp(IPADDRESS);

                                List<PyObject> portDet = ipDetials.get("ports").asList();
                                if(!portDet.isEmpty()){
                                    //contains ports...
                                    for(int j=0;j<portDet.size();j++){
                                        WifiPort wifiPort = new WifiPort();
                                        Map<PyObject,PyObject> innerPort = portDet.get(j).asMap();
                                        String banner = innerPort.get("banner").toString();
                                        String service = innerPort.get("service").toString();
                                        String portNo = innerPort.get("portNo").toString();

                                        wifiPort.setPortNo(portNo);
                                        wifiPort.setBanner(banner);
                                        wifiPort.setService(service);

                                        portTemp.add(wifiPort);
                                    }
                                }else{
                                    //no ports...
                                    WifiPort wifiPort = new WifiPort();
                                    wifiPort.setService("No-Port Detected");
                                    wifiPort.setPortNo("No-Port Detected");
                                    wifiPort.setBanner("No-Port Detected");

                                    portTemp.add(wifiPort);
                                }

                                wifiData.setWifiPortList(portTemp);
                                wifiAdapterList.add(wifiData);
                            }
                        }

                        //setting recycle view...
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));

                        adapter=new CustomAdapterWifi(wifiAdapterList,getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    }
                });

              /*  appendResultsText("Finished "+timeTaken+" s");
                setEnabled(subnetDevicesButton, true);*/

                ObjHandler.post(new Runnable() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void run() {
                        //mProgressbar.setVisibility(0);
                        //btnStaged.setEnabled(false);
                        Toast.makeText(getApplicationContext(), String.valueOf(ipList), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}