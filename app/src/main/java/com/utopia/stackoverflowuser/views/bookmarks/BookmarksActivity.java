package com.utopia.stackoverflowuser.views.bookmarks;

import static com.utopia.stackoverflowuser.views.main.MainActivity.CODE_REMOVE_BOOKMARK;
import static com.utopia.stackoverflowuser.views.main.MainActivity.EXTRA_CODE;
import static com.utopia.stackoverflowuser.views.main.MainActivity.EXTRA_USER;
import static com.utopia.stackoverflowuser.views.main.MainActivity.FILTER_ACTION_MAIN;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.utopia.stackoverflowuser.R;
import com.utopia.stackoverflowuser.databinding.ActivityBookmarksBinding;
import com.utopia.stackoverflowuser.utils.MyDialog;
import com.utopia.stackoverflowuser.views.main.MainAdapter;
import com.utopia.stackoverflowuser.views.user.UserActivity;

public class BookmarksActivity extends AppCompatActivity {

  BookmarksViewModel mViewModel;
  ActivityBookmarksBinding mBinding;
  private MainAdapter mAdapter;
  private MyDialog myDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mViewModel = ViewModelProviders.of(this).get(BookmarksViewModel.class);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bookmarks);

    setSupportActionBar(mBinding.toolbar);
    mBinding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

    myDialog = MyDialog.getInstance();
    myDialog.showProgress(this, false);

    mBinding.recyclerView.setHasFixedSize(true);
    mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

    mAdapter = new MainAdapter((view, item, position) -> {
      if (view.getId() == R.id.layout_main) {
        startActivity(new Intent(this, UserActivity.class).putExtra(EXTRA_USER, item));
        overridePendingTransition(0, 0);
      } else if (view.getId() == R.id.bookmark) {
        mViewModel.removeBookmark(item);
        mAdapter.getData().remove(position);
        mAdapter.notifyDataSetChanged();
        mBinding.layoutEmpty.setVisibility(mAdapter.getData().isEmpty() ? View.VISIBLE : View.GONE);

        // send broadcast
        Intent intent = new Intent(FILTER_ACTION_MAIN);
        intent.putExtra(EXTRA_USER, item);
        intent.putExtra(EXTRA_CODE, CODE_REMOVE_BOOKMARK);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
      }
    });
    mAdapter.setLoading(false);
    mBinding.recyclerView.setAdapter(mAdapter);

    mViewModel.getBookmarks();
    mViewModel.getObservableBookmarks().observe(this, list -> {
      mAdapter.setData(list);
      mAdapter.notifyDataSetChanged();
      myDialog.hideProgress();
      mBinding.layoutEmpty.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
    });
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
    overridePendingTransition(0, 0);
  }
}
