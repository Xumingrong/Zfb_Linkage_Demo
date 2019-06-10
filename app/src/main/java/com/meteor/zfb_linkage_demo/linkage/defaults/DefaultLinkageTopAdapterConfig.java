package com.meteor.zfb_linkage_demo.linkage.defaults;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.meteor.zfb_linkage_demo.R;
import com.meteor.zfb_linkage_demo.linkage.contract.ILinkageTopAdapterConfig;
import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageTopViewHolder;

/**
 * 作者：Meteor on 2019/6/5 11:57
 * 邮箱：15537171227@163.com
 */
public class DefaultLinkageTopAdapterConfig implements ILinkageTopAdapterConfig {
    private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
    private static final int MARQUEE_REPEAT_NONE_MODE = 0;

    private Context context;

    private OnTopItemClickListener onTopItemClickListener;
    private OnTopItemBindListener onTopItemBindListener;

    public void setListener(OnTopItemClickListener onTopItemClickListener, OnTopItemBindListener onTopItemBindListener) {
        this.onTopItemClickListener = onTopItemClickListener;
        this.onTopItemBindListener = onTopItemBindListener;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.linkage_view_top_item;
    }

    @Override
    public int getGroupTitleViewId() {
        return R.id.linkage_view_primary_item_text;
    }

    @Override
    public int getRootViewId() {
        return R.id.linkage_view_primary_item_linear;
    }

    @Override
    public void onBindViewHolder(LinkageTopViewHolder holder, boolean selected, String title, int position) {
        TextView tvTitle = (TextView) holder.textTitle;
        tvTitle.setText(title);//设置字
//        tvTitle.setBackgroundColor(context.getResources().getColor(selected ? R.color.color_black : R.color.color_white));//设置选中背景色
        tvTitle.setTextColor(context.getResources().getColor(selected ? R.color.color_319EEC : R.color.color_5A5A5A));//设置选中后字体颜色
        tvTitle.setEllipsize(selected ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);//设置滚动效果
        tvTitle.setFocusable(selected);//获得焦点
        tvTitle.setFocusableInTouchMode(selected);//获得焦点
        tvTitle.setMarqueeRepeatLimit(selected ? MARQUEE_REPEAT_LOOP_MODE : MARQUEE_REPEAT_NONE_MODE);//是否设置跑马灯效果
        if (onTopItemBindListener != null) {
            onTopItemBindListener.onBindViewHolder(holder, title, position);
        }
    }

    @Override
    public void onItemClick(View view, String title, int position) {
        if (onTopItemClickListener != null) {
            onTopItemClickListener.onItemClick(view, title, position);
        }
    }

    public interface OnTopItemClickListener {
        void onItemClick(View view, String title, int position);
    }

    public interface OnTopItemBindListener {
        void onBindViewHolder(LinkageTopViewHolder topHolder, String title, int position);
    }
}
