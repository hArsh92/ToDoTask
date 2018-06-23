package com.harsh.todoapp.source.remote;

import com.harsh.todoapp.source.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface TaskRemoteService {

    @POST("v2/5a8e5b372f000048004f25fc")
    Call<List<Task>> getAllTask();
}
