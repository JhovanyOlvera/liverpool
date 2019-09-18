package com.android.myliverpool;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class MainActivity extends AppCompatActivity implements IMainCallbacks, IMainAdapterCallbacks {

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
    private IMainAdapterCallbacks iMainAdapterCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        vm = ViewModelProviders.of(this).get(MainViewModel.class);
        vm.iMainCallbacks = this;
        iMainAdapterCallbacks = this;
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

        btnSearch.setOnClickListener(v -> {
            vm.pager = 1;
            vm.search(etSearch.getText().toString());
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        rvProducts.setLayoutManager(mLayoutManager);
        rvProducts.setHasFixedSize(true);
        adapter = new ProductAdapter(this,iMainAdapterCallbacks );
        rvProducts.setAdapter(adapter);

        final int[] pastVisiblesItems = {0};
        final int[] visibleItemCount = {0};
        final int[] totalItemCount = {0};

        rvProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount[0] = mLayoutManager.getChildCount();
                    totalItemCount[0] = mLayoutManager.getItemCount();
                    pastVisiblesItems[0] = mLayoutManager.findFirstVisibleItemPosition();

                    if (!vm.loading) {
                        if (visibleItemCount[0] + pastVisiblesItems[0] >= totalItemCount[0]) {
                            vm.loading = true;
                            vm.search(etSearch.getText().toString());
                            vm.pager = vm.pager + 1;
                        }
                    }
                }
            }
        });


        rvHistorySearch.setHasFixedSize(true);
        historySearchAdapter = new HistorySearchAdapter(this, vm.iMainCallbacks);
        rvHistorySearch.setAdapter(historySearchAdapter);
    }

    @Override
    public void setProduct(ArrayList<Product> products, boolean isNewSearch) {
        rvProducts.setVisibility(View.VISIBLE);
        tvNoFountResult.setVisibility(View.GONE);
        pbLoader.setVisibility(View.GONE);
        adapter.setProduct(products,isNewSearch);
    }

    @Override
    public void noResultFound() {
        tvResult.setVisibility(View.GONE);
        tvNoFountResult.setVisibility(View.VISIBLE);
        pbLoader.setVisibility(View.GONE);
    }

    @Override
    public void initSearch() {
        rvProducts.setVisibility(View.VISIBLE);
        tvNoFountResult.setVisibility(View.GONE);
        pbLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void setSearch(String parameter) {
        etSearch.setText(parameter);
        vm.pager = 1;
        vm.search(parameter);
    }

    @Override
    public void setHistorySearch(List<HistorySearch> historySearchList) {
        runOnUiThread(() -> {
            rvHistorySearch.setVisibility(View.VISIBLE);
            historySearchAdapter.setHistorySearchs(historySearchList);
        });
    }

    @Override
    public void updateNumberResult(String number) {
        tvResult.setVisibility(View.VISIBLE);
        tvResult.setText(getString(R.string.number_result, number));
    }
}
