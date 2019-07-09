package com.veepoo.szcarlottery.util

import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Response


fun parse(response: Response<ResponseBody>): Pair<Boolean, String> {
    val str = response.body().string()
//    Log.i("HTML", "isGetCarLotter=$str")
    val doc = Jsoup.parse(str)
//    Log.i("HTML", "doc=${doc}")
    val select = doc.select("tr.content_data")
    val tagName = select.tagName("td")
    for (i in 0..tagName.size - 1) {
        val text = tagName[i].text()
    }
    if (tagName.size == 1) {
        if (tagName[0].text().contains("无此数据")) {
            return Pair(false, "");
        } else {
            return Pair(true, tagName[0].text().toString());
        }
    }
    return Pair(false, "");
}