package com.utopia.stackoverflowuser.views.user;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.utopia.stackoverflowuser.data.AppRepository;
import com.utopia.stackoverflowuser.data.remote.pojo.ReputationHistoryListResponse;
import com.utopia.stackoverflowuser.data.remote.pojo.UserResponse;

public class UserViewModel extends AndroidViewModel {

  private MutableLiveData<ReputationHistoryListResponse> mUsers = new MutableLiveData<>();
  private UserResponse user;

  public UserResponse getUser() {
    return user;
  }

  MutableLiveData<ReputationHistoryListResponse> getUsers() {
    return mUsers;
  }

  public UserViewModel(@NonNull Application application) {
    super(application);
  }

  void getReputationHistory(int userId, int page, int pagesize, String site) {
    AppRepository.getInstance().getReputationHistory(mUsers, userId, page, pagesize, site);
  }

  public void setUser(UserResponse userResponse) {
    this.user = userResponse;
  }
}
