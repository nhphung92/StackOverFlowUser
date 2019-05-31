package com.utopia.stackoverflowuser.views.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.utopia.stackoverflowuser.data.AppRepository;
import com.utopia.stackoverflowuser.data.remote.pojo.UserListResponse;
import com.utopia.stackoverflowuser.data.remote.pojo.UserResponse;

public class MainViewModel extends AndroidViewModel {

  private MutableLiveData<UserListResponse> mUsers = new MutableLiveData<>();

  MutableLiveData<UserListResponse> getUsers() {
    return mUsers;
  }

  public MainViewModel(@NonNull Application application) {
    super(application);
  }

  void getUserList(int page, int pagesize, String site) {
    AppRepository.getInstance().getUserList(mUsers, page, pagesize, site);
  }

  void addBookmark(UserResponse user) {
    AppRepository.getInstance().removeAddBookmark(user);
  }
}
