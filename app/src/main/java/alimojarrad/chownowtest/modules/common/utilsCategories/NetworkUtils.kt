package com.sidecarhealth.modules.common.utilsCategories

import alimojarrad.chownowtest.models.ErrorDom
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.reactivex.exceptions.UndeliverableException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

object NetworkUtils {
    fun getErrorMessage(throwable: Throwable): String? {
        return if (throwable is HttpException) {
            getErrorMessage(throwable.response())
        } else {
            null
        }
    }

    fun getErrorMessage(response: Response<*>?): String? {
        try {
            response?.let {
                try {
                    var gson = Gson()
                    var model = gson.fromJson<ErrorDom>(response.errorBody()?.string().toString(), ErrorDom::class.java)
                    return model.message
                } catch (e: NullPointerException) {
                    return if (response.message()?.isNotEmpty() == true) {
                        response.message()
                    } else {
                        Timber.e(e)
                        null
                    }

                } catch (e: IllegalStateException) {
                    Timber.e(e)
                    return null
                } catch (e: JSONException) {
                    Timber.e(e)
                    return null
                } catch (e: JsonSyntaxException) {
                    Timber.e(e)
                    return null
                } catch (e: UndeliverableException) {
                    Timber.e(e)
                    return null
                }
            } ?: run {
                Timber.e("null error body")
                return null
            }
        } catch (e: java.lang.NullPointerException) {
            Timber.e("Nullpointer for endpoint body -> ${response?.errorBody()}")
            return null
        }
    }

    private fun isJSONValid(test: String): Boolean {
        try {
            JSONObject(test)
        } catch (ex: JSONException) {
            try {
                JSONArray(test)
            } catch (ex1: JSONException) {
                return false
            }
        }
        return true
    }
}
