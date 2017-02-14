package none.healthaide.common;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import none.healthaide.R;

public class WebViewFragment extends Fragment {

    public static final String TAG = WebViewFragment.class.getSimpleName();
    public static final String TITLE = "title";
    public static final String URL = "url";

    private Unbinder unbinder;
    @BindView(R.id.toolbar_actionbar)
    Toolbar toolbar;
    @BindView(R.id.webview_container)
    ViewGroup webViewContainer;
    @BindView(R.id.webview)
    WebView webView;

    private String title;
    private String url;

    public static WebViewFragment createInstance(String title, String url) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(URL, url);
        WebViewFragment webViewFragment = new WebViewFragment();
        webViewFragment.setArguments(args);
        return webViewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        unbinder = ButterKnife.bind(this, view);
        title = getArguments().getString(TITLE);
        url = getArguments().getString(URL);
        initActionBar();
        initWebView();
        if (url != null) {
            webView.loadUrl(url);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initActionBar() {
        setHasOptionsMenu(true);
        toolbar.setTitle(title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(this.getContext().getCacheDir().getAbsolutePath());
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(false);

//        webView.addJavascriptInterface(new LocalStorageInterface(), NATIVE_LOCAL_STORAGE_INTERFACE);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            @SuppressWarnings("deprecation")
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }
}
