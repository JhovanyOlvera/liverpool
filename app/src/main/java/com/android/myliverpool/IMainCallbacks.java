package com.android.myliverpool;

import com.android.myliverpool.models.Product;
import com.android.myliverpool.models.tables.HistorySearch;

import java.util.ArrayList;
import java.util.List;

public interface IMainCallbacks {

    void setProduct(ArrayList<Product> products);

    void noResultFound();

    void initSearch();

    void setSearch(String parameter);

    void setHistorySearch(List<HistorySearch> historySearchList);
}
