package com.utopia.stackoverflowuser.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.utopia.stackoverflowuser.data.local.dao.UserDao;
import com.utopia.stackoverflowuser.data.remote.pojo.UserResponse;

@Database(entities = {UserResponse.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

  public abstract UserDao userDao();

  private static AppDatabase INSTANCE;

  public static AppDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (AppDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "stackoverflowuser_db").build();
        }
      }
    }
    return INSTANCE;
  }
}