package com.utopia.stackoverflowuser.data.remote.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class ReputationResponse implements Parcelable {

  @SerializedName("reputation_history_type")
  private String reputationHistoryType;

  @SerializedName("reputation_change")
  private int reputationChange;

  @SerializedName("post_id")
  private int postId;

  @SerializedName("creation_date")
  private long creationDate;

  @SerializedName("user_id")
  private int userId;

  protected ReputationResponse(Parcel in) {
    reputationHistoryType = in.readString();
    reputationChange = in.readInt();
    postId = in.readInt();
    creationDate = in.readLong();
    userId = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(reputationHistoryType);
    dest.writeInt(reputationChange);
    dest.writeInt(postId);
    dest.writeLong(creationDate);
    dest.writeInt(userId);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ReputationResponse> CREATOR = new Creator<ReputationResponse>() {
    @Override
    public ReputationResponse createFromParcel(Parcel in) {
      return new ReputationResponse(in);
    }

    @Override
    public ReputationResponse[] newArray(int size) {
      return new ReputationResponse[size];
    }
  };

  public String getReputationHistoryType() {
    return reputationHistoryType;
  }

  public void setReputationHistoryType(String reputationHistoryType) {
    this.reputationHistoryType = reputationHistoryType;
  }

  public int getReputationChange() {
    return reputationChange;
  }

  public void setReputationChange(int reputationChange) {
    this.reputationChange = reputationChange;
  }

  public int getPostId() {
    return postId;
  }

  public void setPostId(int postId) {
    this.postId = postId;
  }

  public long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(long creationDate) {
    this.creationDate = creationDate;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}