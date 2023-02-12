package com.lakala.pos.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lakala.pos.R;
import com.lakala.pos.interfaces.ICustomerServiceView;
import com.lakala.pos.presente.CustomerServicePresenter;
import com.lakala.pos.ui.MVPActivity;
import com.lakala.pos.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CustomerServiceActivity extends MVPActivity<ICustomerServiceView, CustomerServicePresenter> implements ICustomerServiceView, View.OnKeyListener {


    @BindView(R.id.webview)
    WebView mWebView;
    String URL = "";

    private ProgressDialog progressDialog;

    @Override
    protected CustomerServicePresenter createPresenter() {
        return new CustomerServicePresenter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        ButterKnife.bind(this);


        mPresenter.onCustomerService("");
    }


    @OnClick({R.id.back_tv,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_tv:// 返回

                finish();

                break;

        }
    }


    @Override
    public void customerServiceResult(String result) {
        LogUtil.i("获取地址： " + result);
        init(result);
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        LogUtil.e(" webView.goBack() " + event.getRepeatCount());

        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack() && event.getRepeatCount() == 0 && event.getAction() == KeyEvent.ACTION_DOWN) {
            LogUtil.e(" webView.goBack() " + mWebView.copyBackForwardList().getSize());
            mWebView.goBack();
            return true;
        }
        return false;
    }


    public void showProgress(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);//设置点击不消失
        }
        if (progressDialog.isShowing()) {
            progressDialog.setMessage(message);
        } else {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    public void removeProgress() {
        if (progressDialog == null) {
            return;
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void init(String url) {

        LogUtil.e("加载   url : " + url);

        mWebView = findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {

                sslErrorHandler.proceed();  // 如果证书一致，忽略错误

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtil.i("webView加载完成");
                removeProgress();
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                showProgress("加载中......");
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

//            View myVideoView;
//            View myNormalView;
//            WebChromeClient.CustomViewCallback callback;

//
//            /**
//             * 全屏播放配置
//             */
//            @Override
//            public void onShowCustomView(View view,
//                                         WebChromeClient.CustomViewCallback customViewCallback) {
//                FrameLayout normalView = (FrameLayout) findViewById(R.id.webview);
//                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
//                viewGroup.removeView(normalView);
//                viewGroup.addView(view);
//                myVideoView = view;
//                myNormalView = normalView;
//                callback = customViewCallback;
//            }
//
//            @Override
//            public void onHideCustomView() {
//                if (callback != null) {
//                    callback.onCustomViewHidden();
//                    callback = null;
//                }
//                if (myVideoView != null) {
//                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
//                    viewGroup.removeView(myVideoView);
//                    viewGroup.addView(myNormalView);
//                }
//            }

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });

        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
                LogUtil.d("WebView", "url: " + arg0);
                new AlertDialog.Builder(CustomerServiceActivity.this)
                        .setTitle("allow to download？")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText(
                                                CustomerServiceActivity.this,
                                                "fake message: i'll download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("no",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(CustomerServiceActivity.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener() {

                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                CustomerServiceActivity.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
            }
        });


        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
//        webSetting.setMixedContentMode(WebSettings.LOAD_NORMAL);

        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        long time = System.currentTimeMillis();
        mWebView.loadUrl(url);
        LogUtil.d("time-cost", "cost time: "
                + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }


    @Override
    public void onBackPressed() {
        WebBackForwardList webBackForwardList = mWebView.copyBackForwardList();
        if (mWebView.canGoBack()) {
            int index = webBackForwardList.getCurrentIndex();
            if (index == 0 || index == 1) {
                mWebView.goBack();

            } else {
                if (mWebView.canGoBackOrForward(-index)) {
                    mWebView.goBackOrForward(-index);

                } else {
                    mWebView.goBack();

                }
            }

        } else {
            super.onBackPressed();
        }
    }

}