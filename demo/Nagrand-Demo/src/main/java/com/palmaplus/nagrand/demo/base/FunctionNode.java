package com.palmaplus.nagrand.demo.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Overu on 2015/8/11.
 */
public class FunctionNode implements Parcelable {

  public static final Creator<FunctionNode> CREATOR = new Creator<FunctionNode>() {
    @Override
    public FunctionNode createFromParcel(Parcel parcel) {
      return new FunctionNode(parcel);
    }

    @Override
    public FunctionNode[] newArray(int i) {
      return new FunctionNode[i];
    }
  };

  private String title;
  private String target;
  private Parcelable []childs;

  public FunctionNode(String title, String target, Parcelable []childs) {
    this.title = title;
    this.target = target;
    this.childs = childs;
  }

  private FunctionNode(Parcel parcel) {
    title = parcel.readString();
    target = parcel.readString();
    childs = parcel.readParcelableArray(FunctionNode.class.getClassLoader());
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public Parcelable[] getChilds() {
    return childs;
  }

  public void setChilds(Parcelable[] childs) {
    this.childs = childs;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(title);
    parcel.writeString(target);
    parcel.writeParcelableArray(childs, i);
  }
}
