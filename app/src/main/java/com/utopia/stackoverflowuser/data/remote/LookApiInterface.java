package com.utopia.stackoverflowuser.data.remote;

import com.utopia.stackoverflowuser.data.remote.pojo.ReputationHistoryListResponse;
import com.utopia.stackoverflowuser.data.remote.pojo.UserListResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LookApiInterface {

  @GET("users?")
  Observable<UserListResponse> getUserList(
      @Query("page") int page,
      @Query("pagesize") int pagesize,
      @Query("site") String site);

  @GET("users/{userid}/reputation-history")
  Observable<ReputationHistoryListResponse> getReputationHistory(
      @Path("userid") int userId,
      @Query("page") int page,
      @Query("pagesize") int pagesize,
      @Query("site") String site
  );
}
