package com.utopia.stackoverflowuser.views.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.utopia.stackoverflowuser.R;
import com.utopia.stackoverflowuser.data.remote.pojo.UserResponse;
import com.utopia.stackoverflowuser.databinding.LayoutUserItemBinding;
import com.utopia.stackoverflowuser.utils.SystemUtils;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<UserResponse> mData = new ArrayList<>();
  private boolean mIsLoading = false;
  private int mPage = 1;
  private final OnItemClickListener listener;

  public void setLoading(boolean loading) {
    mIsLoading = loading;
  }

  public boolean isLoading() {
    return mIsLoading;
  }

  public int getPage() {
    return mPage;
  }

  public void setPage(int mPage) {
    this.mPage = mPage;
  }

  public void setData(List<UserResponse> data) {
    this.mData = data;
  }

  public List<UserResponse> getData() {
    return mData;
  }

  public MainAdapter(OnItemClickListener listener) {
    this.listener = listener;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutUserItemBinding viewBinding = DataBindingUtil
        .inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_user_item, parent,
            false);
    return new MyViewHolder(viewBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ((MyViewHolder) holder).bind(mData.get(position), position, this.listener);
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  public void setUser(UserResponse user) {
    for (int i = 0; i < mData.size(); i++) {
      if (user.getUserId() == mData.get(i).getUserId()) {
        mData.set(i, user);
        notifyItemChanged(i);
        break;
      }
    }
  }

  static class MyViewHolder extends RecyclerView.ViewHolder {

    LayoutUserItemBinding mBinding;

    MyViewHolder(LayoutUserItemBinding binding) {
      super(binding.getRoot());
      this.mBinding = binding;
    }

    void bind(UserResponse user, int position, OnItemClickListener listener) {
      mBinding.setUser(user);

      mBinding.lastAccessDate.setText(SystemUtils.getTimeAgo(user.getLastAccessDate() * 1000));

      if (!TextUtils.isEmpty(user.getProfileImage())) {
        Glide.with(mBinding.profileImage.getContext()).load(user.getProfileImage())
            .into(mBinding.profileImage);
      } else {
        Glide.with(mBinding.profileImage.getContext()).load(R.drawable.ic_avatar)
            .into(mBinding.profileImage);
      }

      mBinding.bookmark
          .setOnClickListener(v -> listener.onItemClick(mBinding.bookmark, user, position));
      mBinding.layoutMain
          .setOnClickListener(v -> listener.onItemClick(mBinding.layoutMain, user, position));
    }
  }

  public interface OnItemClickListener {

    public void onItemClick(View view, UserResponse item, int position);
  }

}
