package com.android.myliverpool;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.android.myliverpool.api.ClienteHttp;
import com.android.myliverpool.api.ISearch;
import com.android.myliverpool.models.SearchResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private ISearch iSearch = ClienteHttp.getIRecipe();
    public IMainCallbacks iMainCallbacks;
    protected Context ctx;

    public void search(String parameter) {
        if(!TextUtils.isEmpty(parameter)) {
            iMainCallbacks.initSearch();
            iSearch.getSearch(true, parameter).enqueue(new Callback<SearchResult>() {
                @Override
                public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                    if (response.body().getPlpResults() != null && !response.body().getPlpResults().getRecords().isEmpty()) {
                        iMainCallbacks.setProduct(response.body().getPlpResults().getRecords());
                    } else {
                        iMainCallbacks.noResultFound();
                    }
                }

                @Override
                public void onFailure(Call<SearchResult> call, Throwable t) {

                }
            });
        }else{
            Toast.makeText(ctx, ctx.getString(R.string.empty_search), Toast.LENGTH_SHORT).show();
        }

    }

}
