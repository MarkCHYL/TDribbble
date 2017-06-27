package com.tyc.tdribbble.ui.oauth;

import android.content.Intent;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.tyc.tdribbble.R;
import com.tyc.tdribbble.api.ApiConstants;
import com.tyc.tdribbble.base.BaseActivity;

import butterknife.BindView;

public class OAuthActivity extends BaseActivity {

    @BindView(R.id.wv_oauth)
    WebView mWvOauth;
    @BindView(R.id.pb_oauth)
    ProgressBar mPbOauth;
    @Override
    protected int layoutResID() {
        return R.layout.activity_oauth;
    }

    @Override
    protected void initData() {
        mPbOauth.setVisibility(View.VISIBLE);
        WebSettings settings= mWvOauth.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        mWvOauth.loadUrl(getIntent().getStringExtra("url"));
        mWvOauth.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mPbOauth.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url=request.getUrl().toString();
                if(url!=null&&url.contains(ApiConstants.OAuth.REDIRECT_URI)&&url.contains("code"))
                {
                    String[] strs= url.split("&");
                    for(String str:strs)
                    {
                        if(str.contains("code"))
                        {
                            String codeStr=str.replace("code=","");
                            Intent intent=new Intent();
                            intent.putExtra("code",codeStr);
                            setResult(RESULT_OK,intent);
                            break;
                        }
                    }
                }else{
                    view.loadUrl(url);
                }

                return true;
            }
        });
    }
}
