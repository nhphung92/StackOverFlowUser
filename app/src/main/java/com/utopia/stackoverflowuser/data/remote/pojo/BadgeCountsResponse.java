package com.utopia.stackoverflowuser.data.remote.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class BadgeCountsResponse implements Parcelable {

  @SerializedName("bronze")
  private int bronze;

  @SerializedName("silver")
  private int silver;

  @SerializedName("gold")
  private int gold;

  protected BadgeCountsResponse(Parcel in) {
    bronze = in.readInt();
    silver = in.readInt();
    gold = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(bronze);
    dest.writeInt(silver);
    dest.writeInt(gold);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<BadgeCountsResponse> CREATOR = new Creator<BadgeCountsResponse>() {
    @Override
    public BadgeCountsResponse createFromParcel(Parcel in) {
      return new BadgeCountsResponse(in);
    }

    @Override
    public BadgeCountsResponse[] newArray(int size) {
      return new BadgeCountsResponse[size];
    }
  };

  public int getBronze() {
    return bronze;
  }

  public void setBronze(int bronze) {
    this.bronze = bronze;
  }

  public int getSilver() {
    return silver;
  }

  public void setSilver(int silver) {
    this.silver = silver;
  }

  public int getGold() {
    return gold;
  }

  public void setGold(int gold) {
    this.gold = gold;
  }
}
