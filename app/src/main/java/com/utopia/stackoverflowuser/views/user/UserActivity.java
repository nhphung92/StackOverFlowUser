package com.utopia.stackoverflowuser.views.user;

import static com.utopia.stackoverflowuser.utils.MyConstants.PAGE_SIZE;
import static com.utopia.stackoverflowuser.utils.MyConstants.SITE;
import static com.utopia.stackoverflowuser.views.main.MainActivity.EXTRA_USER;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.utopia.stackoverflowuser.R;
import com.utopia.stackoverflowuser.data.remote.pojo.ReputationResponse;
import com.utopia.stackoverflowuser.data.remote.pojo.UserResponse;
import com.utopia.stackoverflowuser.databinding.ActivityUserBinding;
import com.utopia.stackoverflowuser.utils.MyDialog;
import com.utopia.stackoverflowuser.utils.SystemUtils;
import com.utopia.stackoverflowuser.views.user.ReputationAdapter.OnItemClickListener;
import java.util.Objects;

public class UserActivity extends AppCompatActivity implements OnItemClickListener {

  private MyDialog myDialog;
  private UserViewModel mViewModel;
  private ActivityUserBinding mBinding;
  private ReputationAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user);

    setSupportActionBar(mBinding.toolbar);
    mBinding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

    UserResponse userResponse = getIntent().getParcelableExtra(EXTRA_USER);
    mViewModel.setUser(userResponse);
    mBinding.setUser(userResponse);
    Objects.requireNonNull(getSupportActionBar()).setTitle("");

    myDialog = MyDialog.getInstance();
    myDialog.showProgress(this, false);

    mBinding.recyclerView.setHasFixedSize(true);
    mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

    mAdapter = new ReputationAdapter(this);
    mAdapter.setLoading(false);
    mBinding.recyclerView.setAdapter(mAdapter);

    mViewModel.getUsers().observe(this, userList -> {

      if (userList != null) {
        int page = mAdapter.getPage();
        if (page > 0 && userList.getItems().isEmpty()) {
          mAdapter.setPage(page - 1);
        }

        mAdapter.getData().addAll(userList.getItems());
        mAdapter.notifyDataSetChanged();

        if (!userList.isHasMore()) {
          Toast.makeText(this, R.string.no_user_not_found, Toast.LENGTH_LONG).show();
        }
      }

      myDialog.hideProgress();
      mBinding.setShowLoading(false);
      mAdapter.setLoading(false);
      mBinding.swipeContainer.setRefreshing(false);
    });

    mViewModel.getReputationHistory(userResponse.getUserId(), 1, PAGE_SIZE, SITE);

    if (!TextUtils.isEmpty(userResponse.getProfileImage())) {
      Glide.with(mBinding.profileImage.getContext()).load(userResponse.getProfileImage())
          .into(mBinding.profileImage);
    } else {
      Glide.with(mBinding.profileImage.getContext()).load(R.drawable.ic_avatar)
          .into(mBinding.profileImage);
    }

    mBinding.swipeContainer.setOnRefreshListener(() -> {
      if (SystemUtils.isNetworkConnected()) {
        mAdapter.getData().clear();
        mAdapter.setPage(1);
        mViewModel.getReputationHistory(userResponse.getUserId(), 1, PAGE_SIZE, SITE);
      } else {
        mBinding.swipeContainer.setRefreshing(false);
        Toast.makeText(this, R.string.toast_no_internet, Toast.LENGTH_SHORT).show();
      }
    });

    mBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }

      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!mAdapter.isLoading() && !recyclerView.canScrollVertically(1)) {
          mBinding.setShowLoading(true);
          mAdapter.setLoading(true);
          mAdapter.setPage(mAdapter.getPage() + 1);
          mViewModel
              .getReputationHistory(userResponse.getUserId(), mAdapter.getPage(), PAGE_SIZE, SITE);
        }
      }
    });

    mBinding.reputation.setText(getString(R.string.format_reputation,
        SystemUtils.formatNumber(userResponse.getReputation())));

    mBinding.websiteUrl.setOnClickListener(v -> startActivity(
        new Intent(Intent.ACTION_VIEW, Uri.parse(userResponse.getWebsiteUrl()))));

    mBinding.lastAccessDate.setText(getString(R.string.format_last_seen,
        SystemUtils.getTimeAgo(userResponse.getLastAccessDate() * 1000)));
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
    overridePendingTransition(0, 0);
  }

  @Override
  public void onItemClick(View view, ReputationResponse item) {
    startActivity(new Intent(Intent.ACTION_VIEW,
        Uri.parse(getString(R.string.format_postId, item.getPostId()))));
  }
}
