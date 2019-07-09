package com.veepoo.szcarlottery.http

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import rx.Observable

interface HttpService {
    companion object {
        val BASE_URL = "https://apply.jtys.sz.gov.cn/"
    }

    @FormUrlEncoded
    @POST("apply/app/status/norm/person")
    fun getHtmlResult(@FieldMap map: Map<String, String>): Observable< Response<ResponseBody>>

    @FormUrlEncoded
    @POST("apply/app/status/norm/person")
    fun getHtmlResults(@Field("pageNo") value0: String, @Field("issueNumber") value1: String, @Field("applyCode") value2: String): Observable< Response<ResponseBody>>
}