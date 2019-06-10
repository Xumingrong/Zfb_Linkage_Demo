package com.meteor.zfb_linkage_demo.linkage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meteor.zfb_linkage_demo.linkage.contract.ILinkageTopAdapterConfig;
import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageTopViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Meteor on 2019/6/5 11:31
 * 邮箱：15537171227@163.com
 */
public class LinkageTopAdapter extends RecyclerView.Adapter<LinkageTopViewHolder> {
    private List<String> strings;
    private Context context;
    private View view;
    private ILinkageTopAdapterConfig config;
    private OnLinkageTopListener onLinkageTopListener;
    private int selectedPosition;//点击的top中的位置

    public List<String> getStrings() {
        return strings;
    }

    public ILinkageTopAdapterConfig getConfig() {
        return config;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public LinkageTopAdapter(List<String> strings, ILinkageTopAdapterConfig config, OnLinkageTopListener linkageTopListener) {
        this.strings = strings;
        if (strings == null) {
            this.strings = new ArrayList<>();
        }
        this.config = config;
        this.onLinkageTopListener = linkageTopListener;
    }

    //初始化top中的数据
    public void initData(List<String> list) {
        strings.clear();
        if (list != null) {
            strings.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LinkageTopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        this.config.setContext(context);
        view = LayoutInflater.from(context).inflate(this.config.getLayoutId(), parent, false);
        return new LinkageTopViewHolder(view, this.config);
    }

    @Override
    public void onBindViewHolder(@NonNull final LinkageTopViewHolder holder, int position) {
        holder.linearLayout.setSelected(true);
        final int adapterPosition = holder.getAdapterPosition();
        final String title = strings.get(adapterPosition);
        this.config.onBindViewHolder(holder, position == this.selectedPosition, title, adapterPosition);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLinkageTopListener != null) {
                    onLinkageTopListener.onLinkageTopClick(holder, title, adapterPosition);
                }
                config.onItemClick(v, title, adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public interface OnLinkageTopListener {
        void onLinkageTopClick(LinkageTopViewHolder holder, String title, int position);
    }
}
