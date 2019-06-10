package com.meteor.zfb_linkage_demo.base;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.gyf.barlibrary.ImmersionBar;
import com.meteor.zfb_linkage_demo.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author：Meteor
 * date：2018/8/1 09:24
 * desc：mvc设计风格的基类activity
 */
public abstract class BaseMvcActivity extends AppCompatActivity {
    protected ImmersionBar immersionBar;//修改状态栏的背景
    public final String ACTIVITY_SIMPLE_NAME = this.getClass().getSimpleName();//获取当前activity名称
    public Unbinder unBinder;//初始化butterKnife
    protected ACTIVITY_STATUS status;//当前activity当前状态
    protected LayoutInflater layoutInflater;
    protected boolean isRegEvent;//是否注册EventBus

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        unBinder = ButterKnife.bind(this);//初始化ButterKnife
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar(R.color.color_white, R.color.color_black, true);
        }
        BaseApplication.getApplication().getActivityControl().addActivity(this);//加入activity管理
        layoutInflater = getLayoutInflater();
        //初始化EventBus
        if (isRegEvent) {
            EventBus.getDefault().register(this);
        }

        initView(savedInstanceState);
        initEvent();
        getData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        status = ACTIVITY_STATUS.START;
    }

    @Override
    protected void onPause() {
        super.onPause();
        status = ACTIVITY_STATUS.PAUSE;
    }

    @Override
    protected void onResume() {
        super.onResume();
        status = ACTIVITY_STATUS.RESUME;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        status = ACTIVITY_STATUS.DESTROY;
        //取消绑定ButterKnife
        if (unBinder != null) {
            unBinder.unbind();
        }
        //取消注册EventBus
        if (isRegEvent) {
            EventBus.getDefault().unregister(this);
        }
        //在BaseActivity里销毁mImmersionBar
        if (immersionBar != null) {
            immersionBar.destroy();
        }
        BaseApplication.getApplication().getActivityControl().removeActivity(this); //移除类
    }

    /**
     * 获取当前activity的状态
     *
     * @return
     */
    public ACTIVITY_STATUS getCurrentStatus() {
        return this.status;
    }

    /**
     * 根据Uri判定跳转到某个页面
     *
     * @return
     */
    protected Uri getUri() {
        if (getIntent() != null) {
            Uri data = getIntent().getData();
            if (data != null) {
                return data;
            }
        }
        return null;
    }

    /**
     * 修改状态栏的背景
     *
     * @param highStatusBarColor
     * @param lowStatusBarColor
     * @param isChange
     */
    protected void initImmersionBar(@ColorRes int highStatusBarColor, @ColorRes int lowStatusBarColor, boolean isChange) {
        //在BaseActivity里初始化
        immersionBar = ImmersionBar.with(this);
        //如果设备支持状态栏变色（背景色默认为白色(可更改,在子类重写initImmersionBar方法，传入第一个参数颜色即可),字体颜色可更改：系统默认为白色；statusBarDarkFont为true时：为黑色；statusBarDarkFont为false时：为白色，本项目大部分地方状态栏字体为黑色，所以默认为true,即为黑色。
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            immersionBar.statusBarDarkFont(isChange)
                    .fitsSystemWindows(true)
                    .statusBarColor(highStatusBarColor)
                    .keyboardEnable(true)
                    .init();
            //如果设备不支持状态栏变色(背景色现在默认为黑色(可更改,在子类重写initImmersionBar方法，传入第二个参数颜色即可)，字体颜色不可更改，所以一直为黑色)
        } else {
            immersionBar.fitsSystemWindows(true)
                    .statusBarColor(lowStatusBarColor)
                    .keyboardEnable(true)
                    .init();
        }
    }

    /**
     * 是否可以使用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }


    /**
     * 获取布局文件
     *
     * @return
     */
    protected abstract int getContentLayout();

    /**
     * 初始化控件
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化点击事件
     */
    protected void initEvent() {
    }

    /**
     * 初始化数据
     */
    protected void getData() {
    }

    /**
     * 保存activity每个生命周期的状态。
     */
    public enum ACTIVITY_STATUS {
        START, RESUME, PAUSE, STOP, DESTROY
    }
}
