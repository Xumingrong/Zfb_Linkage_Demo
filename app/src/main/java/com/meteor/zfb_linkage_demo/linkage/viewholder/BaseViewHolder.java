package com.meteor.zfb_linkage_demo.linkage.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * 作者：Meteor on 2019/6/5 11:32
 * 邮箱：15537171227@163.com
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> headerViews = new SparseArray<>();
    private View convertView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.convertView = itemView;
    }

    public <T extends View> T getView(int viewId) {
        View view = headerViews.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            headerViews.put(viewId, view);
        }
        return (T) view;
    }
}
