package com.meteor.zfb_linkage_demo.linkage.manager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;

/**
 * 作者：Meteor on 2019/6/5 16:59
 * 邮箱：15537171227@163.com
 */
public class RecyclerViewScrollHelper {

    public static void smoothScrollToPosition(RecyclerView recyclerView, int snapMode, int position) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
            LinearSmoothScroller mScroller = null;
            if (snapMode == LinearSmoothScroller.SNAP_TO_START) {
                mScroller = new TopSmoothScroller(recyclerView.getContext());
            } else if (snapMode == LinearSmoothScroller.SNAP_TO_END) {
                mScroller = new BottomSmoothScroller(recyclerView.getContext());
            } else {
                mScroller = new LinearSmoothScroller(recyclerView.getContext());
            }
            mScroller.setTargetPosition(position);
            manager.startSmoothScroll(mScroller);
        }
    }

    public static class TopSmoothScroller extends LinearSmoothScroller {
        TopSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }
    }

    public static class BottomSmoothScroller extends LinearSmoothScroller {
        BottomSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_END;
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_END;
        }
    }
}
