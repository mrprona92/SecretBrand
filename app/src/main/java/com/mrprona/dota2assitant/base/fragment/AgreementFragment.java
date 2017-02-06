package com.mrprona.dota2assitant.base.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import com.mrprona.dota2assitant.R;

public class AgreementFragment extends SCBaseFragment {

    @BindView(R.id.webView_agreement)
    WebView mWebView;

    private String mUrl;
    public static final String ARG_URL = "arg-url";

    @Override
    public int getToolbarTitle() {
        return R.string.app_name;
    }

    @Override
    public String getToolbarTitleString() {
        return null;
    }


    @Override
    protected int getViewContent() {
        return R.layout.fragment_agreement;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initControls() {
    }

    @Override
    protected void initData() {
    }

    @Override
    public void hideInformation() {

    }

    @Override
    protected void registerListeners() {
    }

    @Override
    protected void unregisterListener() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) mUrl = savedInstanceState.getString(ARG_URL);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        handleArgument(getArguments());
        loadAgreementNews();
        super.onViewCreated(view, savedInstanceState);
    }

    private void handleArgument(Bundle bundle) {
        if (bundle != null && bundle.containsKey(ARG_URL)) {
            mUrl = getArguments().getString(ARG_URL);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_URL, mUrl);
        super.onSaveInstanceState(outState);
    }

    public AgreementFragment() {
    }

    private void loadAgreementNews() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.endsWith(".pdf")) {
                    // Load "url" in google docs
                    Log.d("PDF_Url",url);
                    String googleDocs = "https://docs.google.com/viewer?url=";
                    view.loadUrl(googleDocs + url);
                } else {
                    // Load all other urls normally.
                    view.loadUrl(url);
                }
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if (mActivity != null) {
                    new Handler(mActivity.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideProgressDialog();
                        }
                    }, 1000);
                }
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                hideProgressDialog();
            }
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();
//            }


        });
        mWebView.loadUrl(mUrl);
        showProgressDialog();
    }
}