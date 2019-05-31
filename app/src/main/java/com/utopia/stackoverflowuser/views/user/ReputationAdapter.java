package com.utopia.stackoverflowuser.views.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.utopia.stackoverflowuser.R;
import com.utopia.stackoverflowuser.data.remote.pojo.ReputationResponse;
import com.utopia.stackoverflowuser.databinding.LayoutReputationItemBinding;
import com.utopia.stackoverflowuser.utils.SystemUtils;
import java.util.ArrayList;
import java.util.List;

public class ReputationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<ReputationResponse> mData = new ArrayList<>();
  private boolean mIsLoading = false;
  private int mPage = 1;
  private final OnItemClickListener listener;

  void setLoading(boolean loading) {
    mIsLoading = loading;
  }

  boolean isLoading() {
    return mIsLoading;
  }

  int getPage() {
    return mPage;
  }

  void setPage(int mPage) {
    this.mPage = mPage;
  }

  public void setData(List<ReputationResponse> data) {
    this.mData = data;
  }

  public List<ReputationResponse> getData() {
    return mData;
  }

  ReputationAdapter(OnItemClickListener listener) {
    this.listener = listener;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutReputationItemBinding viewBinding = DataBindingUtil
        .inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_reputation_item, parent,
            false);
    return new MyViewHolder(viewBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ((MyViewHolder) holder).bind(mData.get(position), this.listener);
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  static class MyViewHolder extends RecyclerView.ViewHolder {

    LayoutReputationItemBinding mBinding;

    MyViewHolder(LayoutReputationItemBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }

    void bind(ReputationResponse reputation, OnItemClickListener listener) {
      mBinding.setReputation(reputation);
      mBinding.lastAccessDate.setText(SystemUtils.getTimeAgo(reputation.getCreationDate() * 1000));
      mBinding.layoutMain
          .setOnClickListener(v -> listener.onItemClick(mBinding.layoutMain, reputation));
    }
  }

  public interface OnItemClickListener {

    void onItemClick(View view, ReputationResponse item);
  }

}
