package com.iiitmk.project.droidrecon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapterDomain extends RecyclerView.Adapter<CustomAdapterDomain.MyViewHolderNews>  {

    Context context;
    List<Domain> domainList;
    SharedPreferences.Editor editor;

    public CustomAdapterDomain(List<Domain> domainList, Context applicationContext) {
        this.domainList = domainList;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public MyViewHolderNews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_domain,parent,false);
        return new MyViewHolderNews(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderNews holder, int position) {
        final Domain domain = domainList.get(position);
        holder.domainNameTxt.setText(domain.domainName);
        holder.timeTxt.setText(domain.time);
        holder.modeTxt.setText(domain.mode);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context.getApplicationContext(), String.valueOf(domain.getSubDomainList().size()),Toast.LENGTH_LONG).show();

                Intent in=new Intent( view.getContext(),ViewSubDomain.class );
                in.putExtra("domainName",domain.domainName);
                in.putExtra("fullName",domain.fullName);
                in.putExtra("Mode",domain.mode);
                in.putExtra("time",String.valueOf(domain.getSubDomainList().size()));
                view.getContext().startActivity( in );












            }
        });

    }

    @Override
    public int getItemCount() {
        return domainList.size();
    }

    public class MyViewHolderNews extends RecyclerView.ViewHolder {
        TextView domainNameTxt,modeTxt,timeTxt;
        public MyViewHolderNews(View view) {
            super(view);
            domainNameTxt=view.findViewById(R.id.domainItemDomain);
            modeTxt=view.findViewById(R.id.modeItemDomain);
            timeTxt=view.findViewById(R.id.timeItemDomain);

        }
    }
}
