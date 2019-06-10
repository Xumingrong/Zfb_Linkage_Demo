package com.meteor.zfb_linkage_demo;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meteor.zfb_linkage_demo.base.BaseMvcActivity;
import com.meteor.zfb_linkage_demo.linkage.LinkageView;
import com.meteor.zfb_linkage_demo.linkage.bean.DefaultGroupedItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvcActivity {
    @BindView(R.id.linkage_view)
    LinkageView linkageView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initLinkageView(linkageView);
    }

    private void initLinkageView(LinkageView view) {
        List<DefaultGroupedItem> items = new Gson().fromJson(getString(R.string.linkage),
                new TypeToken<List<DefaultGroupedItem>>() {
                }.getType());
        view.init(items);
        view.setGridMode(true);
        view.setScrollSmoothly(false);
    }
}
