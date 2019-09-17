package com.android.myliverpool;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myliverpool.adapters.HistorySearchAdapter;
import com.android.myliverpool.adapters.ProductAdapter;
import com.android.myliverpool.models.Product;
import com.android.myliverpool.models.tables.HistorySearch;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainCallbacks {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.til_search)
    TextInputLayout tilSearch;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.rv_products)
    RecyclerView rvProducts;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    @BindView(R.id.tv_noFountResult)
    TextView tvNoFountResult;
    @BindView(R.id.rv_historySearch)
    RecyclerView rvHistorySearch;

    private MainViewModel vm;
    private ProductAdapter adapter;
    private HistorySearchAdapter historySearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        vm = ViewModelProviders.of(this).get(MainViewModel.class);
        vm.iMainCallbacks = this;
        vm.ctx = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        initRecyclerView();
        initButtonSearch();
    }

    private void initButtonSearch() {
        etSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                vm.getHistorySearch();
            }
        });

        btnSearch.setOnClickListener(v -> vm.search(etSearch.getText().toString()));
    }

    private void initRecyclerView() {
        rvProducts.setHasFixedSize(true);
        adapter = new ProductAdapter(this);
        rvProducts.setAdapter(adapter);

        rvHistorySearch.setHasFixedSize(true);
        historySearchAdapter = new HistorySearchAdapter(this, vm.iMainCallbacks);
        rvHistorySearch.setAdapter(historySearchAdapter);
    }

    @Override
    public void setProduct(ArrayList<Product> products) {
        tvResult.setVisibility(View.VISIBLE);
        tvResult.setText(getString(R.string.number_result, String.valueOf(products.size())));
        rvProducts.setVisibility(View.VISIBLE);
        tvNoFountResult.setVisibility(View.GONE);
        pbLoader.setVisibility(View.GONE);
        adapter.setProduct(products);
    }

    @Override
    public void noResultFound() {
        tvResult.setVisibility(View.GONE);
        tvNoFountResult.setVisibility(View.VISIBLE);
        pbLoader.setVisibility(View.GONE);
    }

    @Override
    public void initSearch() {
        rvProducts.setVisibility(View.GONE);
        tvNoFountResult.setVisibility(View.GONE);
        pbLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void setSearch(String parameter) {
        etSearch.setText(parameter);
        vm.search(parameter);
    }

    @Override
    public void setHistorySearch(List<HistorySearch> historySearchList) {
        runOnUiThread(() -> {
            rvHistorySearch.setVisibility(View.VISIBLE);
            historySearchAdapter.setHistorySearchs(historySearchList);
        });
    }
}
