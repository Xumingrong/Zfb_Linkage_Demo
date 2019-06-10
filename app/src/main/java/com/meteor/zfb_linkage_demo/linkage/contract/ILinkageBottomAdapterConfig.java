package com.meteor.zfb_linkage_demo.linkage.contract;

import android.content.Context;
import android.view.View;

import com.meteor.zfb_linkage_demo.linkage.bean.BaseGroupedItem;
import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageBottomFooterViewHolder;
import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageBottomHeaderViewHolder;
import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageBottomViewHolder;


/**
 * 作者：Meteor on 2019/6/5 11:44
 * 邮箱：15537171227@163.com
 */
public interface ILinkageBottomAdapterConfig<T extends BaseGroupedItem.ItemInfo> {

    void setContext(Context context);

    int getGridLayoutId();

    int getLinearLayoutId();

    int getHeaderLayoutId();

    int getFooterLayoutId();

    int getHeaderTextViewId();

    int getSpanCountOfGridMode();

    void onBindViewHolder(LinkageBottomViewHolder holder, BaseGroupedItem<T> item, int position);

    void onBindHeaderViewHolder(LinkageBottomHeaderViewHolder holder, BaseGroupedItem<T> item, int position);

    void onBindFooterViewHolder(LinkageBottomFooterViewHolder holder, BaseGroupedItem<T> item, int position);
}
