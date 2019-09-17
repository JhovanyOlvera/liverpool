package com.android.myliverpool.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteHttp {

    public static final String BASE_URL = "https://shoppapp.liverpool.com.mx/appclienteservices/services/v3/plp";
    public static final String BAKING = "baking.json";
    private static Retrofit retrofit;
    private static ISearch iSearch;

    public ClienteHttp() {
    }

    static {
        setupRetrofit();
        setupResources();
    }

    private static void setupResources() {
        iSearch = retrofit.create(ISearch.class);
    }

    private static void setupRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BASE_URL).build();
    }

    public static ISearch getIRecipe() {
        return iSearch;
    }


}
