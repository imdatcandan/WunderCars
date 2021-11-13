package com.imdatcandan.wundercars.data

import com.imdatcandan.wundercars.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class BasicAuthInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header(AUTH_HEADER, BuildConfig.AUTH_HEADER).build()
        return chain.proceed(authenticatedRequest)
    }

    private companion object {
        private const val AUTH_HEADER = "Authorization"
    }
}