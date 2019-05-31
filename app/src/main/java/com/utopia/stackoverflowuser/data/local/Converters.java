package com.utopia.stackoverflowuser.data.local;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utopia.stackoverflowuser.data.remote.pojo.BadgeCountsResponse;
import java.util.List;

public class Converters {

  @TypeConverter
  public static List<String> stringToList(String s) {
    return new Gson().fromJson(s, new TypeToken<List<String>>() {
    }.getType());
  }

  @TypeConverter
  public static String listToString(List<String> list) {
    return new Gson().toJson(list);
  }

  @TypeConverter
  public static String badgeCountsToString(BadgeCountsResponse response) {
    return new Gson().toJson(response);
  }

  @TypeConverter
  public static BadgeCountsResponse stringToBadgeCounts(String s) {
    return new Gson().fromJson(s, new TypeToken<BadgeCountsResponse>() {
    }.getType());
  }
}