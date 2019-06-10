package com.meteor.zfb_linkage_demo.linkage.defaults;

import android.content.Context;
import android.widget.TextView;

import com.meteor.zfb_linkage_demo.R;
import com.meteor.zfb_linkage_demo.linkage.bean.BaseGroupedItem;
import com.meteor.zfb_linkage_demo.linkage.bean.DefaultGroupedItem;
import com.meteor.zfb_linkage_demo.linkage.contract.ILinkageBottomAdapterConfig;
import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageBottomFooterViewHolder;
import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageBottomHeaderViewHolder;
import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageBottomViewHolder;

/**
 * 作者：Meteor on 2019/6/5 17:20
 * 邮箱：15537171227@163.com
 */
public class DefaultLinkageBottomAdapterConfig implements ILinkageBottomAdapterConfig<DefaultGroupedItem.ItemInfo> {
    private Context context;
    private OnBottomItemBindListener itemBindListener;
    private OnBottomHeaderBindListener headerBindListener;
    private OnBottomFooterBindListener footerBindListener;
    private static final int SPAN_COUNT = 4;

    public void setItemBindListener(OnBottomItemBindListener itemBindListener, OnBottomHeaderBindListener headerBindListener, OnBottomFooterBindListener footerBindListener) {
        this.itemBindListener = itemBindListener;
        this.headerBindListener = headerBindListener;
        this.footerBindListener = footerBindListener;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getGridLayoutId() {
        return R.layout.default_adapter_linkage_bottom_grider;
    }

    @Override
    public int getLinearLayoutId() {
        return R.layout.default_adapter_linkage_bottom_linear;
    }

    @Override
    public int getHeaderLayoutId() {
        return R.layout.default_adapter_linkage_bottom_header;
    }

    @Override
    public int getFooterLayoutId() {
        return 0;
    }

    @Override
    public int getHeaderTextViewId() {
        return R.id.bottom_header;
    }

    @Override
    public int getSpanCountOfGridMode() {
        return SPAN_COUNT;
    }

    @Override
    public void onBindViewHolder(LinkageBottomViewHolder holder, BaseGroupedItem<DefaultGroupedItem.ItemInfo> item, int position) {
        ((TextView) holder.getView(R.id.linkage_view_bottom_item_title)).setText(item.info.getTitle());
//        ((TextView) holder.getView(R.id.linkage_view_bottom_item_content)).setText(item.info.getContent());
        if (itemBindListener != null) {
            itemBindListener.onBindViewHolder(holder, item, position);
        }
    }

    @Override
    public void onBindHeaderViewHolder(LinkageBottomHeaderViewHolder holder, BaseGroupedItem<DefaultGroupedItem.ItemInfo> item, int position) {
        ((TextView) holder.getView(R.id.bottom_header)).setText(item.header);
        if (headerBindListener != null) {
            headerBindListener.onBindHeaderViewHolder(holder, item, position);
        }
    }

    @Override
    public void onBindFooterViewHolder(LinkageBottomFooterViewHolder holder, BaseGroupedItem<DefaultGroupedItem.ItemInfo> item, int position) {

    }

    public interface OnBottomItemBindListener {
        void onBindViewHolder(LinkageBottomViewHolder secondaryHolder, BaseGroupedItem<DefaultGroupedItem.ItemInfo> item, int position);
    }

    public interface OnBottomHeaderBindListener {
        void onBindHeaderViewHolder(LinkageBottomHeaderViewHolder headerHolder, BaseGroupedItem<DefaultGroupedItem.ItemInfo> item, int position);
    }

    public interface OnBottomFooterBindListener {
        void onBindFooterViewHolder(LinkageBottomFooterViewHolder footerHolder, BaseGroupedItem<DefaultGroupedItem.ItemInfo> item, int position);
    }
}
