package com.utopia.stackoverflowuser.data;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import com.utopia.stackoverflowuser.data.local.AppDatabase;
import com.utopia.stackoverflowuser.data.local.dao.UserDao;
import com.utopia.stackoverflowuser.data.remote.LookApiClient;
import com.utopia.stackoverflowuser.data.remote.pojo.ReputationHistoryListResponse;
import com.utopia.stackoverflowuser.data.remote.pojo.UserListResponse;
import com.utopia.stackoverflowuser.data.remote.pojo.UserResponse;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class AppRepository {

  private final UserDao mUserDao;

  public UserDao getUserDao() {
    return mUserDao;
  }

  private Application mApplication = MyApplication.getInstance();

  private static AppRepository mInstance;

  public static AppRepository getInstance() {
    if (mInstance == null) {
      mInstance = new AppRepository();
    }
    return mInstance;
  }

  private AppRepository() {
    AppDatabase appDatabase = AppDatabase.getDatabase(mApplication);
    mUserDao = appDatabase.userDao();
  }

  public void getUserList(MutableLiveData<UserListResponse> liveData, int page, int pagesize,
      String site) {
    LookApiClient.getApiClient().getUserList(page, pagesize, site)
        .map(userListResponse -> {
          List<UserResponse> list = mUserDao.getAll();
          for (int i = 0; i < userListResponse.getItems().size(); i++) {
            UserResponse user = userListResponse.getItems().get(i);
            for (UserResponse book : list) {
              if (book.getUserId() == user.getUserId()) {
                user.setBookmarked(true);
                userListResponse.getItems().set(i, user);
                break;
              }
            }
          }
          return userListResponse;
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<UserListResponse>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(UserListResponse userListResponse) {
            liveData.setValue(userListResponse);
          }

          @Override
          public void onError(Throwable e) {
            liveData.setValue(null);
          }

          @Override
          public void onComplete() {

          }
        });
  }

  public void getReputationHistory(MutableLiveData<ReputationHistoryListResponse> liveData,
      int userId,
      int page,
      int pagesize, String site) {
    LookApiClient.getApiClient().getReputationHistory(userId, page, pagesize, site)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<ReputationHistoryListResponse>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(ReputationHistoryListResponse userListResponse) {
            liveData.setValue(userListResponse);
          }

          @Override
          public void onError(Throwable e) {
            liveData.setValue(null);
          }

          @Override
          public void onComplete() {

          }
        });
  }

  public void getBookmarks(MutableLiveData<List<UserResponse>> liveData) {
    Observable.fromCallable(mUserDao::getAll)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<UserResponse>>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(List<UserResponse> userListResponse) {
            liveData.setValue(userListResponse);
          }

          @Override
          public void onError(Throwable e) {
            liveData.setValue(new ArrayList<>());
          }

          @Override
          public void onComplete() {

          }
        });
  }

  public void removeAddBookmark(UserResponse user) {
    Observable.fromCallable(() -> {
      if (user.isBookmarked()) {
        AppRepository.getInstance().getUserDao().insert(user);
      } else {
        AppRepository.getInstance().getUserDao().delete(user);
      }
      return true;
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Boolean>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(Boolean userListResponse) {
          }

          @Override
          public void onError(Throwable e) {
          }

          @Override
          public void onComplete() {

          }
        });
  }
}
