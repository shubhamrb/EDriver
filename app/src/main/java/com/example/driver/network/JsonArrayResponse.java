package com.example.driver.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by kipl104 on 04-Apr-17.
 */

public class JsonArrayResponse<T>  {
    @SerializedName("data")
    public List<T> list;
    public ArrayList<T> listData;
}
