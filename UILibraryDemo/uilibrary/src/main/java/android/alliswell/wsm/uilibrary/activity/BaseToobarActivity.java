package android.alliswell.wsm.uilibrary.activity;

import android.alliswell.wsm.uilibrary.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 以Toolbar作为标题栏的基准Activity<br/>
 * 由setToobarTitle设置标题，标题居中显示
 *
 * @author WeiShiming
 */
public abstract class BaseToobarActivity extends AppCompatActivity {

    private ToolbarHelper mToolbarHelper;

    /**
     * setup title for toolbar
     *
     * @param titleResID title resource id
     */
    public void setToobarTitle(int titleResID) {
        if (mToolbarHelper != null) {
            // 隐藏系统固定的标题
            setTitle(null);
            Toolbar toolbar = mToolbarHelper.getToobar();
            toolbar.setTitle(null);

            // 将标题显示在自定义的位置
            ((TextView) toolbar.findViewById(R.id.txt_toolbarTitle)).setText(titleResID);
        }
    }

    /**
     * setup title for toolbar
     *
     * @param title title
     */
    public void setToolbarTitle(CharSequence title) {
        if (mToolbarHelper != null) {
            // 隐藏系统固定的标题
            setTitle(null);
            Toolbar toolbar = mToolbarHelper.getToobar();
            toolbar.setTitle(null);

            // 将标题显示在自定义的位置
            ((TextView) toolbar.findViewById(R.id.txt_toolbarTitle)).setText(title);
        }
    }

    /**
     * 获取包含Toolbar在内的ContentView
     *
     * @return
     */
    public View getContentView() {
        return mToolbarHelper.getContentView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        mToolbarHelper = new ToolbarHelper(this, layoutResID);

        super.setContentView(mToolbarHelper.getContentView());
        setSupportActionBar(mToolbarHelper.getToobar());

        if (getTitle() != null) {
            setToolbarTitle(getTitle());
        }
    }

    private static class ToolbarHelper {
        private Context mContext;

        private CoordinatorLayout mContentView;
        private View mUserView;
        private Toolbar mToolbar;

        private int mStatusbarHeight;

        /*
         * 两个属性
         * 1、toolbar是否悬浮在窗口之上
         * 2、toolbar的高度获取
         **/
        private static int[] ATTRS = {
                R.attr.windowActionBarOverlay,
                R.attr.actionBarSize,
        };

        public ToolbarHelper(Context context, int layoutResID) {
            mContext = context;
            mStatusbarHeight = getStatusBarHeight();

            initContentView();
            initUserView(layoutResID);
            initToolbar();
            setupTranslucentStatusForKitkat();
        }

        public CoordinatorLayout getContentView() {
            return mContentView;
        }

        public Toolbar getToobar() {
            return mToolbar;
        }

        // 直接创建一个帧布局，作为视图容器的父容器
        private void initContentView() {
            mContentView = new CoordinatorLayout(mContext);
            mContentView.setLayoutParams(new CoordinatorLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            mContentView.setFitsSystemWindows(true);
            mContentView.setClipToPadding(false);
        }

        // 初始化用户的View
        private void initUserView(int layoutResID) {
            mUserView = LayoutInflater.from(mContext).inflate(layoutResID, null);

            TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);

            // 获取主题中定义的悬浮标志
            boolean overlay = typedArray.getBoolean(0, false);
            // 获取主题中定义的toolbar的高度
            int toolbarSize = (int) typedArray.getDimension(1, mContext.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));

            typedArray.recycle();

            // 如果是悬浮状态，则不需要设置间距
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            layoutParams.topMargin = overlay ? 0 : toolbarSize;

            mContentView.addView(mUserView, layoutParams);
        }

        // 通过inflater获取toolbar的布局文件
        private void initToolbar() {
            mToolbar = (Toolbar) (LayoutInflater.from(mContext).inflate(R.layout.module_toolbar, mContentView)
                    .findViewById(R.id.toolbar));
        }

        // 如果kitkat以上版本主题设置windowTranslucentStatus为true，则为status bar添加一个背景色
        private void setupTranslucentStatusForKitkat() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowTranslucentStatus, R.attr.colorPrimaryDark });

                boolean windowTranslucentStatus = typedArray.getBoolean(0, false);
                if (windowTranslucentStatus) {
                    int colorPrimaryDark = typedArray.getColor(1, mContext.getResources().getColor(android.R.color.transparent));

                    View statusView = new View(mContext);
                    statusView.setBackgroundColor(colorPrimaryDark);

                    mContentView.addView(statusView, new CoordinatorLayout.LayoutParams(mContentView.getResources().getDisplayMetrics().widthPixels, mStatusbarHeight));
                    statusView.setY(-mStatusbarHeight);
                }

                typedArray.recycle();
            }
        }

        // 获取状态栏的高度
        public int getStatusBarHeight() {
            int result = 0;
            int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = mContext.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }
    }
}
