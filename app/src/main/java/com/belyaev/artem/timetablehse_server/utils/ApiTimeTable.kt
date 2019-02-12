package com.belyaev.artem.timetablehse_server.utils

import com.belyaev.artem.timetablehse_server.BuildConfig
import com.belyaev.artem.timetablehse_server.model.*
import com.belyaev.artem.timetablehse_server.model.response.UserResponse
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.realm.RealmObject
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

    @GET("api/classies/{group_id}")
    fun getExercisesByGroupID(@Path("group_id") groupID: Int): Observable<ExercisesResponse>

    @GET("api/exercises/{group_id}")
    fun getExercisesRealmByGroupID(@Path("group_id") groupID: Int): Observable<ExercisesRealmResponse>

    @GET("api/teachers/{teacher_id}")
    fun getExercisesByTeacherID(@Path("teacher_id") teacherID: Int): Observable<ExercisesResponse>

    @GET("api/teachers")
    fun getTeachers(): Observable<TeachersResponse>

    @GET("api/groups")
    fun getGroups(): Observable<GroupsResponse>

    @POST("login")
    fun login(@Body login: Login): Observable<LoginResponse>

    @POST("register")
    fun register(@Body register: Register): Observable<RegisterResponse>

    @GET("api/users/{user_id}")
    fun getUser(@Path("user_id") userID: Int): Observable<UserResponse>

    companion object {

        fun getApi(): ApiTimeTable   {
            return Retrofit.Builder()
                .baseUrl(Constants.SERVICE_HOST.value)
                .addConverterFactory(GsonConverterFactory.create(gson))
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

        val gson = GsonBuilder().setExclusionStrategies(object: ExclusionStrategy {
            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                return false
            }

            override fun shouldSkipField(f: FieldAttributes?): Boolean {
                return f?.declaredClass == RealmObject::class.java
            }
        }).create()
    }
}