package com.ascendant.thegade.Model.Response;

import androidx.annotation.Nullable;

import com.ascendant.thegade.Model.DataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseArrayObject {
    @SerializedName("status")
    @Nullable
    public String status;

    @SerializedName("code")
    @Nullable
    public Integer code;

    @SerializedName("message")
    @Nullable
    public String message;

    @SerializedName("data")
    @Nullable
    List<DataModel> data;

    @Nullable
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nullable String status) {
        this.status = status;
    }

    @Nullable
    public List<DataModel> getData() {
        return data;
    }

    public void setData(@Nullable List<DataModel> data) {
        this.data = data;
    }

    @Nullable
    public Integer getCode() {
        return code;
    }

    public void setCode(@Nullable Integer code) {
        this.code = code;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }
}
