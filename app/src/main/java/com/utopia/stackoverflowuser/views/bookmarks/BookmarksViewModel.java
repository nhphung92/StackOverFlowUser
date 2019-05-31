package com.utopia.stackoverflowuser.views.bookmarks;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.utopia.stackoverflowuser.data.AppRepository;
import com.utopia.stackoverflowuser.data.remote.pojo.UserResponse;
import java.util.List;

public class BookmarksViewModel extends AndroidViewModel {

  public BookmarksViewModel(@NonNull Application application) {
    super(application);
  }

  private MutableLiveData<List<UserResponse>> bookmarks = new MutableLiveData<>();

  MutableLiveData<List<UserResponse>> getObservableBookmarks() {
    return bookmarks;
  }

  void getBookmarks() {
    AppRepository.getInstance().getBookmarks(bookmarks);
  }

  void removeBookmark(UserResponse user) {
    user.setBookmarked(false);
    AppRepository.getInstance().removeAddBookmark(user);
  }
}
