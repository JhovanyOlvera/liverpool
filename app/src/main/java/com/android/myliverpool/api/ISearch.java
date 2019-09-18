package com.android.myliverpool.api;

import com.android.myliverpool.models.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ISearch {

    //TODO POR LA ESTRUCTURA DE DATOS DEL SERVICIO  SOLO SE CONSULTA POR  force-plp & search-string POR FUNCIONALIDAD
    @GET("plp/")
    Call<SearchResult> getSearch(@Query("force-plp") Boolean force,
                                 @Query("search-string") String search,
                                 @Query("page-number") String pageNumber,
                                 @Query("number-of-items-per-page") String numberGetItem);

}
