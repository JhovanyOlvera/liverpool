package com.android.myliverpool;

import com.android.myliverpool.models.Product;

import java.util.ArrayList;

public interface IMainCallbacks {

    void setProduct(ArrayList<Product> products);

    void noResultFound();

    void initSearch();
}
