package com.fpuente.simple_ripley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fpuente.simple_ripley.api.ApiRest;
import com.fpuente.simple_ripley.api.AsyncTaskResult;
import com.fpuente.simple_ripley.component.ItemAdapter;
import com.fpuente.simple_ripley.component.PaginationScroll;
import com.fpuente.simple_ripley.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Context context;
    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private ProgressBar progressBar;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        try {
        context = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);



        mAdapter = new ItemAdapter(context);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new PaginationScroll((LinearLayoutManager) mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                if (!isLastPage) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getProduct(page);
                        }
                    }, 200);
                }
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        mAdapter.setOnItemClicklListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Toast.makeText(context, "Clicked item position: " + position, Toast.LENGTH_LONG).show();
            }
        });


        this.getProduct(page);
        } catch (Exception ex) {
            Log.e("ERRORRR", ex.getMessage());
        }
    }

    private void getProduct(final int page) {
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiRest apiRestAppKeep = new ApiRest();
                try {
                    AsyncTaskResult<JSONObject> asyncTaskResult = apiRestAppKeep.getProducts(page);

                    if (asyncTaskResult.getError() != null) {

                        final String msg = "Valide su conexión a internet, no se puede cargar datos iniciales.";
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            }
                        });


                    }else
                    {

                        JSONObject result = asyncTaskResult.getResult();
                        int code = result.getInt("code");
                        switch (code) {
                            case 200:
                                JSONObject body = (JSONObject) result.get("body");
                                JSONArray data = (JSONArray) body.get("products");
                                final int page = body.getInt("page");
                                final int total_pages= body.getInt("totalPages");
                                String dataString = data.toString();
                                final ArrayList<Product> products = new ArrayList<Product>();
                                for(int i = 0; i< data.length(); i++){
                                    JSONObject product = data.getJSONObject(i);

                                    Product newProduct = new Product();
                                    newProduct.setName(product.getString("name"));
                                    newProduct.setUrl_img(product.getString("img_product"));
                                    newProduct.setSku(product.getString("img_product"));
                                    newProduct.setDisc(product.getInt("discount"));
                                    newProduct.setInternet_price(product.getString("internet_price"));
                                    newProduct.setNormal_price(product.getString("normal_price"));
                                    newProduct.setRipley_card_price(product.getString("ripley_card_price"));
                                    newProduct.setTrademark(product.getString("trademark"));

                                    products.add(newProduct);

                                }
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        // Stuff that updates the UI
                                        resultAction(products,page,total_pages);

                                    }
                                });




                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Error obteniendo datos iniciales de usuario\ncode: [" + code + "]", Toast.LENGTH_SHORT).show();
                                break;
                        }}

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void resultAction(ArrayList<Product> products, int page, int total_pages) {
        progressBar.setVisibility(View.INVISIBLE);
        isLoading = false;
        if (products != null) {
            mAdapter.addItems(products);
            if (page == total_pages) {
                isLastPage = true;
            } else {
                this.page = page + 1;
            }
        }
    }
}
