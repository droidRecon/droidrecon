package com.iiitmk.project.droidrecon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapterWifi extends RecyclerView.Adapter<CustomAdapterWifi.MyViewHolderWifi> {
    Context context;
    List<WifiData> wifiDataList;
    public CustomAdapterWifi(List<WifiData> wifiAdapterList, Context applicationContext) {
        this.context=applicationContext;
        this.wifiDataList=wifiAdapterList;
    }

    @NonNull
    @Override
    public MyViewHolderWifi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wifi,parent,false);
        return new MyViewHolderWifi(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderWifi holder, int position) {
        final WifiData wifiData = wifiDataList.get(position);
        holder.txtIp.setText(wifiData.ip);
    }


    @Override
    public int getItemCount() {
        return wifiDataList.size();
    }

    public class MyViewHolderWifi extends RecyclerView.ViewHolder{
        TextView txtIp;
        public MyViewHolderWifi(@NonNull View itemView) {
            super(itemView);
            txtIp = itemView.findViewById(R.id.ipItemWifi);
        }
    }
}
