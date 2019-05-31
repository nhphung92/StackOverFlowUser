package com.utopia.stackoverflowuser.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.utopia.stackoverflowuser.data.remote.pojo.UserResponse;
import java.util.List;

@Dao
public interface UserDao {

  @Query("SELECT * FROM user")
  List<UserResponse> getAll();

  @Insert
  void insert(UserResponse user);

  @Insert
  void insertAll(List<UserResponse> list);

  @Delete
  void delete(UserResponse user);
}