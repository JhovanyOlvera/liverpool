package com.android.myliverpool;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.android.myliverpool.api.ClienteHttp;
import com.android.myliverpool.api.ISearch;
import com.android.myliverpool.database.LiverpoolDatabase;
import com.android.myliverpool.models.SearchResult;
import com.android.myliverpool.models.tables.HistorySearch;
import com.android.myliverpool.utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    protected ISearch iSearch = ClienteHttp.getIRecipe();
    protected IMainCallbacks iMainCallbacks;
    protected Context ctx;

    protected void search(String parameter) {
        if (!TextUtils.isEmpty(parameter)) {
            getHistorySearch();
            iMainCallbacks.initSearch();
            iSearch.getSearch(true, parameter.toLowerCase()).enqueue(new Callback<SearchResult>() {
                @Override
                public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                    if (response.body().getPlpResults() != null && !response.body().getPlpResults().getRecords().isEmpty()) {
                        saveHistorySearch(parameter.toLowerCase());
                        iMainCallbacks.setProduct(response.body().getPlpResults().getRecords());
                    } else {
                        iMainCallbacks.noResultFound();
                    }
                }

                @Override
                public void onFailure(Call<SearchResult> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(ctx, ctx.getString(R.string.empty_search), Toast.LENGTH_SHORT).show();
        }
    }

    protected void getHistorySearch() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<HistorySearch> historySearchList = LiverpoolDatabase.getInstance(ctx).historySearchDAO().loadAllHistorySearch();
            if (historySearchList.size() > 0) {
                iMainCallbacks.setHistorySearch(historySearchList);
            }
        });
    }

    protected void saveHistorySearch(String parameter) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            HistorySearch historySearch = LiverpoolDatabase.getInstance(ctx).historySearchDAO().searchHistorySearch(parameter);
            if (historySearch == null) {
                HistorySearch hs = new HistorySearch();
                hs.setHistory(parameter);
                AppExecutors.getInstance().diskIO().execute(() -> LiverpoolDatabase.getInstance(ctx).historySearchDAO().saveHistorySearch(hs));
            }
        });
    }

}
