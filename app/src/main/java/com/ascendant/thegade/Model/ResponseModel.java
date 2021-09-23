package com.ascendant.thegade.Model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    @SerializedName("status")
    @Expose
    @Nullable
    public String status;

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
}
