package com.windranger.reminder.api

import com.windranger.reminder.model.alarm.Alarm
import com.windranger.reminder.model.desease.Desease
import com.windranger.reminder.model.desease.form.DeseaseForm
import com.windranger.reminder.model.drug.Drug
import com.windranger.reminder.model.drug.DrugType
import com.windranger.reminder.model.efek.Efek
import com.windranger.reminder.model.login.User
import com.windranger.reminder.model.note.Note
import com.windranger.reminder.model.terapi.Terapi
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("auth/login")
    fun login(@Field("email") email: String,
              @Field("password") pass: String
    ): Single<User>

    @FormUrlEncoded
    @POST("register/google")
    fun loginWithGoogle(
            @Field("email") email: String,
            @Field("name") name: String,
            @Field("identifier_id") id: String
    ): Single<User>

    @FormUrlEncoded
    @POST("auth/register")
    fun register(@Field("email") email: String,
                 @Field("name") name: String,
                 @Field("password") pass: String,
                 @Field("gender") gender: String,
                 @Field("role") role: String = "patient"
    ): Single<User>

    @GET("disease")
    fun getDesease(): Single<List<Desease>>

    @POST("user/disease/add")
    fun submitDesease(
            @Header("Authorization") token: String,
            @Body form: DeseaseForm
    ): Completable

    @GET("user/therapy")
    fun getTerapi(@Header("Authorization") token: String): Single<List<Terapi>>

    @GET("user/drug-effect")
    fun getEfekSamping(@Header("Authorization") token: String): Single<List<Efek>>

    @GET("drug-type")
    fun getDrugType(@Header("Authorization") token: String): Single<List<DrugType>>

    @GET("drug/type/{id}")
    fun getDrug(
            @Header("Authorization") token: String,
            @Path("id") id: Int
    ): Single<List<Drug>>

    @Headers("Cache-Control: no-cache")
    @GET("alarm")
    fun getAlarm(@Header("Authorization") token: String): Single<List<Alarm>>

    @POST("alarm/add")
    fun addAlarm(
            @Header("Authorization") token: String,
            @Body form: Alarm
    ): Single<Alarm>

    @DELETE("alarm/delete/{id}")
    fun deleteAlarm(
            @Header("Authorization") token: String,
            @Path("id") id: Int
    ): Completable

    @POST("taking-medication/{id}")
    fun takeMedicine(
            @Header("Authorization") token: String,
            @Path("id") id: Int
    ): Completable

    @PATCH("alarm/edit/{id}")
    fun editAlarm(
            @Header("Authorization") token: String,
            @Path("id") id: Int,
            @Body form: Alarm
    ): Single<Alarm>

    @Headers("Cache-Control: no-cache")
    @GET("notes")
    fun getNotes(@Header("Authorization") token: String): Single<List<Note>>

    @FormUrlEncoded
    @POST("note/add")
    fun addNote(
            @Header("Authorization") token: String,
            @Field("title") title: String,
            @Field("content") content: String
    ): Completable

    @FormUrlEncoded
    @POST("note/edit/{id}")
    fun editNote(
            @Path("id") id: Int,
            @Header("Authorization") token: String,
            @Field("title") title: String,
            @Field("content") content: String
    ): Completable

    @DELETE("note/delete/{id}")
    fun deleteNote(
            @Path("id") id: Int,
            @Header("Authorization") token: String
    ): Completable
}