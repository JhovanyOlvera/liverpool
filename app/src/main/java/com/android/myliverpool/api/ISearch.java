package com.android.myliverpool.api;

import com.android.myliverpool.models.SearchResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ISearch {

    @GET(ClienteHttp.BAKING)
    Call<ArrayList<SearchResult>> getSearch(@Query("force-plp") Boolean force,
                                            @Query("search-string") String search);

}
