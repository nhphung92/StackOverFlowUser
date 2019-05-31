package com.utopia.stackoverflowuser.views.main;

import static com.utopia.stackoverflowuser.utils.MyConstants.PAGE_SIZE;
import static com.utopia.stackoverflowuser.utils.MyConstants.SITE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.utopia.stackoverflowuser.R;
import com.utopia.stackoverflowuser.data.remote.pojo.UserResponse;
import com.utopia.stackoverflowuser.databinding.ActivityMainBinding;
import com.utopia.stackoverflowuser.utils.MyDialog;
import com.utopia.stackoverflowuser.utils.SystemUtils;
import com.utopia.stackoverflowuser.views.bookmarks.BookmarksActivity;
import com.utopia.stackoverflowuser.views.main.MainAdapter.OnItemClickListener;
import com.utopia.stackoverflowuser.views.user.UserActivity;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

  public static final String EXTRA_USER = "EXTRA_USER";
  public static final String FILTER_ACTION_MAIN = "FILTER_ACTION_MAIN";
  public static final String EXTRA_CODE = "code";
  public static final int CODE_REMOVE_BOOKMARK = 1;

  private MainViewModel mViewModel;
  private ActivityMainBinding mBinding;
  private MainAdapter mAdapter;
  private MyDialog myDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    setSupportActionBar(mBinding.toolbar);

    myDialog = MyDialog.getInstance();
    mBinding.contentMain.recyclerView.setHasFixedSize(true);
    mBinding.contentMain.recyclerView.setLayoutManager(new LinearLayoutManager(this));

    mAdapter = new MainAdapter(this);
    mAdapter.setLoading(false);
    mBinding.contentMain.recyclerView.setAdapter(mAdapter);

    myDialog.showProgress(this, false);
    mViewModel.getUserList(mAdapter.getPage(), PAGE_SIZE, SITE);

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
      mAdapter.setLoading(false);
      mBinding.contentMain.swipeContainer.setRefreshing(false);
      mBinding.contentMain.setShowLoading(false);
    });

    mBinding.contentMain.swipeContainer.setOnRefreshListener(() -> {
      if (SystemUtils.isNetworkConnected()) {
        refresh();
      } else {
        mBinding.contentMain.swipeContainer.setRefreshing(false);
        Toast.makeText(this, R.string.toast_no_internet, Toast.LENGTH_SHORT).show();
      }
    });

    mBinding.contentMain.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }

      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!mAdapter.isLoading() && !recyclerView.canScrollVertically(1)) {
          loadMore();
        }
      }
    });

    LocalBroadcastManager.getInstance(this)
        .registerReceiver(mBroadcastReceiver, new IntentFilter(FILTER_ACTION_MAIN));
  }

  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      int code = intent.getIntExtra(EXTRA_CODE, -1);
      if (code == CODE_REMOVE_BOOKMARK && intent.hasExtra(EXTRA_USER)) {
        UserResponse item = intent.getParcelableExtra(EXTRA_USER);
        item.setBookmarked(false);
        mAdapter.setUser(item);
      }
    }
  };

  @Override
  protected void onDestroy() {
    super.onDestroy();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
  }

  private void loadMore() {
    mBinding.contentMain.setShowLoading(true);
    mAdapter.setLoading(true);
    mAdapter.setPage(mAdapter.getPage() + 1);
    mViewModel.getUserList(mAdapter.getPage(), PAGE_SIZE, SITE);
  }

  private void refresh() {
    mBinding.contentMain.swipeContainer.setRefreshing(true);
    mAdapter.getData().clear();
    mAdapter.setPage(1);
    mViewModel.getUserList(mAdapter.getPage(), PAGE_SIZE, SITE);
  }

  @Override
  public void onItemClick(View view, UserResponse item, int position) {
    if (view.getId() == R.id.layout_main) {
      startActivity(new Intent(this, UserActivity.class).putExtra(EXTRA_USER, item));
      overridePendingTransition(0, 0);
    } else if (view.getId() == R.id.bookmark) {
      item.setBookmarked(!item.isBookmarked());
      mAdapter.getData().set(position, item);
      mAdapter.notifyItemChanged(position);
      mViewModel.addBookmark(item);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_bookmarks) {
      startActivity(new Intent(this, BookmarksActivity.class));
      overridePendingTransition(0, 0);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
