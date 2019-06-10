package com.meteor.zfb_linkage_demo.linkage.contract;

import android.content.Context;
import android.view.View;

import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageTopViewHolder;

/**
 * 作者：Meteor on 2019/6/5 11:44
 * 邮箱：15537171227@163.com
 */
public interface ILinkageTopAdapterConfig {

    void setContext(Context context);

    int getLayoutId();

    int getGroupTitleViewId();

    int getRootViewId();

    void onBindViewHolder(LinkageTopViewHolder holder, boolean selected, String title, int position);

    void onItemClick(View view, String title, int position);
}
