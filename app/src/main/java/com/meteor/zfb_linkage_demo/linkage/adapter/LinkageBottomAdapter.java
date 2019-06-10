package com.meteor.zfb_linkage_demo.linkage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meteor.zfb_linkage_demo.R;
import com.meteor.zfb_linkage_demo.linkage.bean.BaseGroupedItem;
import com.meteor.zfb_linkage_demo.linkage.contract.ILinkageBottomAdapterConfig;
import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageBottomFooterViewHolder;
import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageBottomHeaderViewHolder;
import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageBottomViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Meteor on 2019/6/5 17:12
 * 邮箱：15537171227@163.com
 */
public class LinkageBottomAdapter<T extends BaseGroupedItem.ItemInfo> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<BaseGroupedItem<T>> items;
    private static final int IS_HEADER = 0;
    private static final int IS_LINEAR = 1;
    private static final int IS_GRID = 2;
    private static final int IS_FOOTER = 3;
    private boolean isGridMode;
    private ILinkageBottomAdapterConfig config;

    public ILinkageBottomAdapterConfig getConfig() {
        return config;
    }

    public List<BaseGroupedItem<T>> getItems() {
        return items;
    }

    public boolean isGridMode() {
        return isGridMode && config.getGridLayoutId() != 0;
    }

    public void setGridMode(boolean gridMode) {
        isGridMode = gridMode;
    }

    public LinkageBottomAdapter(List<BaseGroupedItem<T>> items, ILinkageBottomAdapterConfig config) {
        this.items = items;
        if (items == null) {
            this.items = new ArrayList<>();
        }
        this.config = config;
    }

    //初始化数据
    public void initData(List<BaseGroupedItem<T>> list) {
        items.clear();
        if (list != null) {
            items.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).isHeader) {
            return IS_HEADER;
        } else if (TextUtils.isEmpty(items.get(position).info.getTitle()) && !TextUtils.isEmpty(items.get(position).info.getGroup())) {
            return IS_FOOTER;
        } else if (isGridMode()) {
            return IS_GRID;
        } else {
            return IS_LINEAR;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        config.setContext(context);
        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(context).inflate(config.getHeaderLayoutId(), parent, false);
            return new LinkageBottomHeaderViewHolder(view);
        } else if (viewType == IS_FOOTER) {
            int footerLayout = config.getFooterLayoutId() == 0 ? R.layout.default_adapter_linkage_bottom_footer : config.getFooterLayoutId();
            View view = LayoutInflater.from(context).inflate(footerLayout, parent, false);
            return new LinkageBottomFooterViewHolder(view);
        } else if (viewType == IS_GRID && config.getGridLayoutId() != 0) {
            View view = LayoutInflater.from(context).inflate(config.getGridLayoutId(), parent, false);
            return new LinkageBottomViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(config.getLinearLayoutId(), parent, false);
            return new LinkageBottomViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final BaseGroupedItem<T> linkageItem = items.get(holder.getAdapterPosition());
        if (getItemViewType(position) == IS_HEADER) {
            LinkageBottomHeaderViewHolder headerViewHolder = (LinkageBottomHeaderViewHolder) holder;
            config.onBindHeaderViewHolder(headerViewHolder, linkageItem, headerViewHolder.getAdapterPosition());
        } else if (getItemViewType(position) == IS_FOOTER) {
            LinkageBottomFooterViewHolder footerViewHolder = (LinkageBottomFooterViewHolder) holder;
            config.onBindFooterViewHolder(footerViewHolder, linkageItem, footerViewHolder.getAdapterPosition());
        } else {
            LinkageBottomViewHolder bottomViewHolder = (LinkageBottomViewHolder) holder;
            config.onBindViewHolder(bottomViewHolder, linkageItem, bottomViewHolder.getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
