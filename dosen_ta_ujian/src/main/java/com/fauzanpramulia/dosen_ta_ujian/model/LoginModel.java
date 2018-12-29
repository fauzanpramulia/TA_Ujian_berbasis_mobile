package com.fauzanpramulia.dosen_ta_ujian.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginModel implements Parcelable {
    String access_token;
    String token_type;
    int expires_in;

    public LoginModel(String access_token, String token_type, int expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }

    public LoginModel() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.access_token);
        dest.writeString(this.token_type);
        dest.writeInt(this.expires_in);
    }

    protected LoginModel(Parcel in) {
        this.access_token = in.readString();
        this.token_type = in.readString();
        this.expires_in = in.readInt();
    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel source) {
            return new LoginModel(source);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };
}
