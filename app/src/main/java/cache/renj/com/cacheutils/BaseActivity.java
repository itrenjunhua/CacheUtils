package cache.renj.com.cacheutils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cache.renj.com.cacheutils.utils.ViewUtils;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-02-02   15:11
 * <p>
 * 描述：Activity 基类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected static BaseActivity foregroundActivity;

    ViewStub vsMainContent;

    @BindView(R.id.title_view_back)
    TextView titleViewBack;
    @BindView(R.id.title_view_title)
    TextView titleViewTitle;
    @BindView(R.id.rl_title_view)
    RelativeLayout rlTitleView;

    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        vsMainContent = findViewById(R.id.vs_main_content);
        vsMainContent.setLayoutResource(getLayoutId());
        vsMainContent.inflate();

        bind = ButterKnife.bind(this);
        isShowTitle(false);

        // 初始化监听
        initListener();
    }

    /**
     * 返回当前Activity的布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化监听
     */
    private void initListener() {
        // 返回按钮监听
        titleViewBack.setOnClickListener(this);
    }

    /**
     * 返回按钮事件，如果需要重新返回键，重写该方法即可
     */
    protected void back() {
        finish();
    }

    /**
     * 是否需要显示标题，默认不显示
     *
     * @param showTitle true：显示 false：不显示
     */
    protected void isShowTitle(boolean showTitle) {
        if (showTitle) {
            ViewUtils.goneView(rlTitleView);
        } else {
            ViewUtils.showView(rlTitleView);
        }
    }

    /**
     * 设置标题内容，强制标题显示
     *
     * @param title
     */
    protected void setTitleContent(@NonNull String title) {
        if (rlTitleView.getVisibility() != View.VISIBLE)
            ViewUtils.showView(rlTitleView);
        titleViewTitle.setText(title + "");
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public static BaseActivity getForegroundActivity() {
        return foregroundActivity;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();

        if (vId == R.id.title_view_back) {
            back();
        } else {
            handClick(vId);
        }
    }

    /**
     * 处理点击事件，所有自雷重写该方法即可，不需要再次实现 {@link View.OnClickListener} 接口
     *
     * @param vId
     */
    protected void handClick(int vId) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}