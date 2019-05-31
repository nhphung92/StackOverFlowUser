package com.utopia.stackoverflowuser.data.remote.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.google.gson.annotations.SerializedName;
import com.utopia.stackoverflowuser.data.local.Converters;

@Entity(tableName = "user")
public class UserResponse implements Parcelable {

  @PrimaryKey
  @ColumnInfo(name = "account_id")
  @SerializedName("account_id")
  private int accountId;

  @ColumnInfo(name = "badge_counts")
  @SerializedName("badge_counts")
  @TypeConverters(Converters.class)
  private BadgeCountsResponse badgeCounts;

  @ColumnInfo(name = "last_access_date")
  @SerializedName("last_access_date")
  private long lastAccessDate;

  @ColumnInfo(name = "reputation")
  @SerializedName("reputation")
  private int reputation;

  @ColumnInfo(name = "creation_date")
  @SerializedName("creation_date")
  private long creationDate;

  @ColumnInfo(name = "user_id")
  @SerializedName("user_id")
  private int userId;

  @ColumnInfo(name = "location")
  @SerializedName("location")
  private String location;

  @ColumnInfo(name = "website_url")
  @SerializedName("website_url")
  private String websiteUrl;

  @ColumnInfo(name = "profile_image")
  @SerializedName("profile_image")
  private String profileImage;

  @ColumnInfo(name = "display_name")
  @SerializedName("display_name")
  private String displayName;

  @ColumnInfo(name = "bookmarked")
  private boolean bookmarked;

  public UserResponse() {
  }

  protected UserResponse(Parcel in) {
    accountId = in.readInt();
    badgeCounts = in.readParcelable(BadgeCountsResponse.class.getClassLoader());
    lastAccessDate = in.readLong();
    reputation = in.readInt();
    creationDate = in.readLong();
    userId = in.readInt();
    location = in.readString();
    websiteUrl = in.readString();
    profileImage = in.readString();
    displayName = in.readString();
    bookmarked = in.readByte() != 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(accountId);
    dest.writeParcelable(badgeCounts, flags);
    dest.writeLong(lastAccessDate);
    dest.writeInt(reputation);
    dest.writeLong(creationDate);
    dest.writeInt(userId);
    dest.writeString(location);
    dest.writeString(websiteUrl);
    dest.writeString(profileImage);
    dest.writeString(displayName);
    dest.writeByte((byte) (bookmarked ? 1 : 0));
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<UserResponse> CREATOR = new Creator<UserResponse>() {
    @Override
    public UserResponse createFromParcel(Parcel in) {
      return new UserResponse(in);
    }

    @Override
    public UserResponse[] newArray(int size) {
      return new UserResponse[size];
    }
  };

  public int getAccountId() {
    return accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }

  public BadgeCountsResponse getBadgeCounts() {
    return badgeCounts;
  }

  public void setBadgeCounts(BadgeCountsResponse badgeCounts) {
    this.badgeCounts = badgeCounts;
  }

  public long getLastAccessDate() {
    return lastAccessDate;
  }

  public void setLastAccessDate(long lastAccessDate) {
    this.lastAccessDate = lastAccessDate;
  }

  public int getReputation() {
    return reputation;
  }

  public void setReputation(int reputation) {
    this.reputation = reputation;
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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getWebsiteUrl() {
    return websiteUrl;
  }

  public void setWebsiteUrl(String websiteUrl) {
    this.websiteUrl = websiteUrl;
  }

  public String getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public boolean isBookmarked() {
    return bookmarked;
  }

  public void setBookmarked(boolean bookmarked) {
    this.bookmarked = bookmarked;
  }
}
