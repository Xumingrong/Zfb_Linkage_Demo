package com.meteor.zfb_linkage_demo.linkage.viewholder;

import android.view.View;

import com.meteor.zfb_linkage_demo.linkage.contract.ILinkageTopAdapterConfig;

/**
 * 作者：Meteor on 2019/6/5 11:42
 * 邮箱：15537171227@163.com
 */
public class LinkageTopViewHolder extends BaseViewHolder {
    public View textTitle;
    public View linearLayout;
    private ILinkageTopAdapterConfig config;

    public LinkageTopViewHolder(View itemView, ILinkageTopAdapterConfig config) {
        super(itemView);
        this.config = config;
        textTitle = itemView.findViewById(this.config.getGroupTitleViewId());
        linearLayout = itemView.findViewById(this.config.getRootViewId());
    }
}
