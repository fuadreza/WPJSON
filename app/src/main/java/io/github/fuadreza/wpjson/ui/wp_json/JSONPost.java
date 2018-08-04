package io.github.fuadreza.wpjson.ui.wp_json;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import io.github.fuadreza.wpjson.R;

/**
 * Dibuat dengan kerjakerasbagaiquda oleh Shifu pada tanggal 03/08/2018.
 */
public class JSONPost extends AppCompatActivity {
    TextView title;
    WebView content;
    ProgressDialog progressDialog;
    Gson gson;
    private List<Object> list;
    Map<String, Object> mapPost;
    Map<String, Object> mapTitle;
    Map<String, Object> mapContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        final String id = getIntent().getExtras().getString("id");
//        Log.d("Kenapa", "ID : " + id);

        title = (TextView) findViewById(R.id.title);
        content = (WebView)findViewById(R.id.content);

        progressDialog = new ProgressDialog(JSONPost.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        String url = "http://mt.infiniteuny.id/wp-json/wp/v2/posts/"+id+"?field=title,content";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                gson = new Gson();
//                list = (List) gson.fromJson(s, List.class);
                mapPost = (Map<String, Object>) gson.fromJson(s, Map.class);
//                mapPost = (Map<String,Object>)list.get(0);
                mapTitle = (Map<String, Object>) mapPost.get("title");
                mapContent = (Map<String, Object>) mapPost.get("content");

                title.setText(mapTitle.get("rendered").toString());
                content.loadData(mapContent.get("rendered").toString(),"text/html","UTF-8");

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(JSONPost.this, id, Toast.LENGTH_LONG).show();
                Log.d("Kenapa", "ID : " + id);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(JSONPost.this);
        rQueue.add(request);
    }
}
