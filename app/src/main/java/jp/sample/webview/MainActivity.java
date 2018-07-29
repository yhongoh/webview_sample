package jp.sample.webview;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {


    WebView webView;
    private String accessUrl = "https://XXX";
    //private String accessUrl = "https://XXX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // WebView
                setContentView(R.layout.activity_main);
                webView = findViewById(R.id.web_view);

        // JavaScriptを有効化
        webView.getSettings().setJavaScriptEnabled(true);

        // Web Storage を有効化
        webView.getSettings().setDomStorageEnabled(true);

        // HTML5 Video support のためHardware acceleration on
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        webView.setWebChromeClient(new WebChromeClient() {
                                       @Override
                                       public void onPermissionRequest(final PermissionRequest request) {
                                           runOnUiThread(new Runnable() {
                                               @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                                               @Override
                                               public void run() {
                                                   if (request.getOrigin().toString().equals("https://lab.dkj.jp/temp/qr/video.html")) {
                                                       request.grant(request.getResources());
                                                   } else {
                                                       request.deny();
                                                   }
                                               }
                                           });
                                       }
                                   });


        //WebViewページ内で画面遷移
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                return false;
            }
        });

        webView.loadUrl(accessUrl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 戻るページがある場合
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_BACK){
            if(webView.canGoBack()){
                webView.goBack();
            }
            else{
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode,  event);
    }
}