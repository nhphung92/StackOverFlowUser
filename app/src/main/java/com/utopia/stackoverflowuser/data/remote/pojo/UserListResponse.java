package com.utopia.stackoverflowuser.data.remote.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserListResponse implements Parcelable {

  @SerializedName("items")
  private List<UserResponse> items;

  @SerializedName("has_more")
  private boolean hasMore;

  protected UserListResponse(Parcel in) {
    items = in.createTypedArrayList(UserResponse.CREATOR);
    hasMore = in.readByte() != 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(items);
    dest.writeByte((byte) (hasMore ? 1 : 0));
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<UserListResponse> CREATOR = new Creator<UserListResponse>() {
    @Override
    public UserListResponse createFromParcel(Parcel in) {
      return new UserListResponse(in);
    }

    @Override
    public UserListResponse[] newArray(int size) {
      return new UserListResponse[size];
    }
  };

  public List<UserResponse> getItems() {
    return items;
  }

  public void setItems(List<UserResponse> items) {
    this.items = items;
  }

  public boolean isHasMore() {
    return hasMore;
  }

  public void setHasMore(boolean hasMore) {
    this.hasMore = hasMore;
  }
}
