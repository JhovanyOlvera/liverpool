package com.android.myliverpool.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myliverpool.IMainCallbacks;
import com.android.myliverpool.R;
import com.android.myliverpool.models.tables.HistorySearch;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistorySearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HistorySearch> historySearchs = new ArrayList<>();
    private IMainCallbacks iMainCallbacks;
    private Context ctx;

    public HistorySearchAdapter(Context ctx, IMainCallbacks iMainCallbacks) {
        this.ctx = ctx;
        this.iMainCallbacks = iMainCallbacks;
    }

    public void setHistorySearchs(List<HistorySearch> historySearchs) {
        this.historySearchs.clear();
        this.historySearchs.addAll(historySearchs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistorySearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HistorySearchViewHolder) holder).setData(historySearchs.get(position));
    }

    @Override
    public int getItemCount() {
        return (!historySearchs.isEmpty()) ? historySearchs.size() : 0;
    }

    class HistorySearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_historySearch)
        TextView tvHistorySearch;
        @BindView(R.id.cv_historySearch)
        CardView cvHistorySearch;

        public HistorySearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(HistorySearch historySearch) {
            tvHistorySearch.setText(historySearch.getHistory());
            cvHistorySearch.setOnClickListener(v -> iMainCallbacks.setSearch(historySearch.getHistory()));
        }
    }

}

