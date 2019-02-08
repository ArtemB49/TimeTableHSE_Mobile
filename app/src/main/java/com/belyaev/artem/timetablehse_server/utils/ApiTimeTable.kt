package com.belyaev.artem.timetablehse_server.utils

import com.belyaev.artem.timetablehse_server.BuildConfig
import com.belyaev.artem.timetablehse_server.model.ExercisesResponse
import com.belyaev.artem.timetablehse_server.model.Login
import com.belyaev.artem.timetablehse_server.model.LoginResponse
import com.belyaev.artem.timetablehse_server.model.TeachersResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiTimeTable {

    @GET("api/classies/1")
    fun getExercisesByGroupID(): Observable<ExercisesResponse>

    @GET("api/teachers/{teacher_id}")
    fun getExercisesByTeacherID(@Path("teacher_id") teacherID: Int): Observable<ExercisesResponse>

    @GET("api/teachers")
    fun getTeachers(): Observable<TeachersResponse>

    @POST("login")
    fun login(@Body login: Login): Observable<LoginResponse>

    companion object {

        fun getApi(): ApiTimeTable   {
            return Retrofit.Builder()
                .baseUrl(Constants.SERVICE_HOST.value)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getClient())
                .build()
                .create(ApiTimeTable::class.java)
        }

        private fun getClient(): OkHttpClient {
            val client = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                client.addInterceptor(logging)
            }
            return client.build()
        }
    }
}