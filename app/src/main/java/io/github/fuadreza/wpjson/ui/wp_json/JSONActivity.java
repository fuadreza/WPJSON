package io.github.fuadreza.wpjson.ui.wp_json;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import io.github.fuadreza.wpjson.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

/**
 * Dibuat dengan kerjakerasbagaiquda oleh Shifu pada tanggal 01/08/2018.
 */
public class JSONActivity extends Activity implements JSONMvpView {

    private String url = "http://mt.infiniteuny.id/wp-json/wp/v2/posts";

    private ListView postList;
    private ProgressDialog progressDialog;

    private Gson gson;
    private List<Object> list;
    private Map<String, Object> mapPost;
    private Map<String, Object> mapTitle;
    private int postID;

    private String postTitle[];

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, JSONActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        //        Log.d("Berhasil", "Bisa jalan");

        postList = (ListView) findViewById(R.id.postList);

        //Progress Dialog
        progressDialog = new ProgressDialog(JSONActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                gson = new Gson();
                list = (List) gson.fromJson(response, List.class);
                postTitle = new String[list.size()];
//                Log.d("Mencoba", String.valueOf(list.size()));

                for (int i = 0; i < list.size(); i++) {
                    mapPost = (Map<String, Object>) list.get(i);
                    mapTitle = (Map<String, Object>) mapPost.get("title");
                    postTitle[i] = (String) mapTitle.get("rendered");
                }

                postList.setAdapter(new ArrayAdapter(JSONActivity.this, android.R.layout.simple_list_item_1, postTitle));
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(JSONActivity.this, "Some error occurred", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(JSONActivity.this);
        rQueue.add(request);

        postList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mapPost = (Map<String,Object>)list.get(position);
                postID = ((Double)mapPost.get("id")).intValue();

                Toast.makeText(JSONActivity.this, "ID = " + id, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),JSONPost.class);
                intent.putExtra("id", ""+postID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPostClicked() {

    }

}
