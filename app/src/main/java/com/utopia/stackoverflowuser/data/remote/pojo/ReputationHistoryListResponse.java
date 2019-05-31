package com.utopia.stackoverflowuser.data.remote.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ReputationHistoryListResponse implements Parcelable {

  @SerializedName("items")
  private List<ReputationResponse> items;

  @SerializedName("has_more")
  private boolean hasMore;

//  @SerializedName("quota_max")
//  private int quotaMax;
//
//  @SerializedName("quota_remaining")
//  private int quotaRemaining;

  protected ReputationHistoryListResponse(Parcel in) {
    items = in.createTypedArrayList(ReputationResponse.CREATOR);
    hasMore = in.readByte() != 0;
//    quotaMax = in.readInt();
//    quotaRemaining = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(items);
    dest.writeByte((byte) (hasMore ? 1 : 0));
//    dest.writeInt(quotaMax);
//    dest.writeInt(quotaRemaining);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ReputationHistoryListResponse> CREATOR = new Creator<ReputationHistoryListResponse>() {
    @Override
    public ReputationHistoryListResponse createFromParcel(Parcel in) {
      return new ReputationHistoryListResponse(in);
    }

    @Override
    public ReputationHistoryListResponse[] newArray(int size) {
      return new ReputationHistoryListResponse[size];
    }
  };

  public List<ReputationResponse> getItems() {
    return items;
  }

  public void setItems(
      List<ReputationResponse> items) {
    this.items = items;
  }

  public boolean isHasMore() {
    return hasMore;
  }

  public void setHasMore(boolean hasMore) {
    this.hasMore = hasMore;
  }

//  public int getQuotaMax() {
//    return quotaMax;
//  }
//
//  public void setQuotaMax(int quotaMax) {
//    this.quotaMax = quotaMax;
//  }
//
//  public int getQuotaRemaining() {
//    return quotaRemaining;
//  }
//
//  public void setQuotaRemaining(int quotaRemaining) {
//    this.quotaRemaining = quotaRemaining;
//  }
}