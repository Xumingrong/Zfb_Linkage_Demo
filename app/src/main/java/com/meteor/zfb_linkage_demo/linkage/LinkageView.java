package com.meteor.zfb_linkage_demo.linkage;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meteor.zfb_linkage_demo.R;
import com.meteor.zfb_linkage_demo.linkage.adapter.LinkageBottomAdapter;
import com.meteor.zfb_linkage_demo.linkage.adapter.LinkageTopAdapter;
import com.meteor.zfb_linkage_demo.linkage.bean.BaseGroupedItem;
import com.meteor.zfb_linkage_demo.linkage.bean.DefaultGroupedItem;
import com.meteor.zfb_linkage_demo.linkage.contract.ILinkageBottomAdapterConfig;
import com.meteor.zfb_linkage_demo.linkage.contract.ILinkageTopAdapterConfig;
import com.meteor.zfb_linkage_demo.linkage.defaults.DefaultLinkageBottomAdapterConfig;
import com.meteor.zfb_linkage_demo.linkage.defaults.DefaultLinkageTopAdapterConfig;
import com.meteor.zfb_linkage_demo.linkage.manager.RecyclerViewScrollHelper;
import com.meteor.zfb_linkage_demo.linkage.viewholder.LinkageTopViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Meteor on 2019/6/5 11:14
 * 邮箱：15537171227@163.com
 */
public class LinkageView<T extends BaseGroupedItem.ItemInfo> extends RelativeLayout {
    private static final int DEFAULT_SPAN_COUNT = 1;
    private static final int SCROLL_OFFSET = 0;

    private Context context;
    private LinearLayout linkageViewLinear;
    private RecyclerView linkageViewTop;
    private RecyclerView linkageViewBottom;

    private List<String> initTopNames;//顶部数据
    private List<BaseGroupedItem<T>> initBottomItems;//底部数据
    private List<Integer> headerPositions = new ArrayList<>();

    private int firstVisiblePosition;
    private String mLastGroupName;

    private LinkageTopAdapter linkageTopAdapter;
    private LinkageBottomAdapter linkageBottomAdapter;

    private LinearLayoutManager topLayoutManager;
    private LinearLayoutManager bottomLayoutManager;
    private boolean scrollSmoothly = true;     //是否平滑滚动

    public LinkageView(Context context) {
        super(context);
    }

    public LinkageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public LinkageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.linkage_view, this);

        linkageViewLinear = (LinearLayout) view.findViewById(R.id.linkage_view_linear);
        linkageViewTop = (RecyclerView) view.findViewById(R.id.linkage_view_top);

        linkageViewBottom = (RecyclerView) view.findViewById(R.id.linkage_view_bottom);
    }

    public void init(List<BaseGroupedItem<T>> linkageItems) {
        init(linkageItems, new DefaultLinkageTopAdapterConfig(), new DefaultLinkageBottomAdapterConfig());
    }

    public void init(List<BaseGroupedItem<T>> linkageItems, ILinkageTopAdapterConfig topAdapterConfig, ILinkageBottomAdapterConfig bottomAdapterConfig) {
        initRecyclerView(topAdapterConfig, bottomAdapterConfig);
        this.initBottomItems = linkageItems;

        String lastGroupName = null;
        List<String> groupNames = new ArrayList<>();
        if (initBottomItems != null && initBottomItems.size() > 0) {
            for (BaseGroupedItem<T> item1 : initBottomItems) {
                if (item1.isHeader) {
                    groupNames.add(item1.header);
                    lastGroupName = item1.header;
                }
            }
        }

        if (initBottomItems != null) {
            for (int i = 0; i < initBottomItems.size(); i++) {
                if (initBottomItems.get(i).isHeader) {
                    headerPositions.add(i);
                }
            }
        }

        DefaultGroupedItem.ItemInfo info = new DefaultGroupedItem.ItemInfo(null, lastGroupName);
        BaseGroupedItem<T> footerItem = (BaseGroupedItem<T>) new DefaultGroupedItem(info);
        initBottomItems.add(footerItem);

        this.initTopNames = groupNames;
        linkageTopAdapter.initData(initTopNames);
        linkageBottomAdapter.initData(initBottomItems);
        initLinkageSecondary();
    }

    private void initRecyclerView(ILinkageTopAdapterConfig topAdapterConfig, ILinkageBottomAdapterConfig bottomAdapterConfig) {
        //顶部适配器初始化
        linkageTopAdapter = new LinkageTopAdapter(initTopNames, topAdapterConfig, new LinkageTopAdapter.OnLinkageTopListener() {
            @Override
            public void onLinkageTopClick(LinkageTopViewHolder holder, String title, int position) {
                if (isScrollSmoothly()) {
                    RecyclerViewScrollHelper.smoothScrollToPosition(linkageViewBottom, LinearSmoothScroller.SNAP_TO_START, headerPositions.get(position));
                } else {
                    bottomLayoutManager.scrollToPositionWithOffset(headerPositions.get(position), SCROLL_OFFSET);
                }
            }
        });
        //顶部rv初始化
        topLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        linkageViewTop.setLayoutManager(topLayoutManager);
        linkageViewTop.setAdapter(linkageTopAdapter);

        linkageBottomAdapter = new LinkageBottomAdapter(initBottomItems, bottomAdapterConfig);
        setBottomLayoutManager();
        linkageViewBottom.setAdapter(linkageBottomAdapter);
    }

    //linkageViewBottom 的Manager
    private void setBottomLayoutManager() {
        if (linkageBottomAdapter.isGridMode()) {
            bottomLayoutManager = new GridLayoutManager(context, linkageBottomAdapter.getConfig().getSpanCountOfGridMode());
            ((GridLayoutManager) bottomLayoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (((BaseGroupedItem<T>) linkageBottomAdapter.getItems().get(position)).isHeader) {
                        return linkageBottomAdapter.getConfig().getSpanCountOfGridMode();
                    }
                    return DEFAULT_SPAN_COUNT;
                }
            });
        } else {
            bottomLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        }
        linkageViewBottom.setLayoutManager(bottomLayoutManager);

    }

    private void initLinkageSecondary() {
        linkageViewBottom.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = bottomLayoutManager.findFirstVisibleItemPosition();
                List<BaseGroupedItem<T>> items = linkageBottomAdapter.getItems();
                boolean groupNameChanged = false;

                if (firstVisiblePosition != firstPosition && firstPosition >= 0) {
                    firstVisiblePosition = firstPosition;
                    String currentGroupName = items.get(firstVisiblePosition).isHeader ? items.get(firstVisiblePosition).header : items.get(firstVisiblePosition).info.getGroup();
                    if (TextUtils.isEmpty(mLastGroupName) || !mLastGroupName.equals(currentGroupName)) {
                        mLastGroupName = currentGroupName;
                        groupNameChanged = true;
                    }
                }

                if (groupNameChanged) {
                    List<String> groupNames = linkageTopAdapter.getStrings();
                    for (int i = 0; i < groupNames.size(); i++) {
                        if (groupNames.get(i).equals(mLastGroupName)) {
                            linkageTopAdapter.setSelectedPosition(i);
                            RecyclerViewScrollHelper.smoothScrollToPosition(linkageViewTop, LinearSmoothScroller.SNAP_TO_END, i);
                        }
                    }
                }
            }
        });
    }


    public void setDefaultOnItemBindListener(
            DefaultLinkageTopAdapterConfig.OnTopItemClickListener topItemClickListener,
            DefaultLinkageTopAdapterConfig.OnTopItemBindListener topItemBindListener,
            DefaultLinkageBottomAdapterConfig.OnBottomItemBindListener bottomItemBindListener,
            DefaultLinkageBottomAdapterConfig.OnBottomHeaderBindListener headerBindListener,
            DefaultLinkageBottomAdapterConfig.OnBottomFooterBindListener footerBindListener) {

        if (linkageTopAdapter.getConfig() != null) {
            ((DefaultLinkageTopAdapterConfig) linkageTopAdapter.getConfig())
                    .setListener(topItemClickListener, topItemBindListener);
        }
        if (linkageBottomAdapter.getConfig() != null) {
            ((DefaultLinkageBottomAdapterConfig) linkageBottomAdapter.getConfig())
                    .setItemBindListener(bottomItemBindListener, headerBindListener, footerBindListener);
        }
    }

    public void setLayoutHeight(float dp) {
        ViewGroup.LayoutParams lp = linkageViewLinear.getLayoutParams();
        lp.height = dpToPx(getContext(), dp);
        linkageViewLinear.setLayoutParams(lp);
    }


    public boolean isGridMode() {
        return linkageBottomAdapter.isGridMode();
    }

    public void setGridMode(boolean isGridMode) {
        linkageBottomAdapter.setGridMode(isGridMode);
        setBottomLayoutManager();
        linkageViewBottom.requestLayout();
    }

    private int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5f);
    }

    public boolean isScrollSmoothly() {
        return scrollSmoothly;
    }

    public void setScrollSmoothly(boolean scrollSmoothly) {
        this.scrollSmoothly = scrollSmoothly;
    }
}