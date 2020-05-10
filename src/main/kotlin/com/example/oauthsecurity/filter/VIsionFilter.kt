package com.example.oauthsecurity.filter

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.IOException
import java.time.Duration
import java.time.Instant
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class VIsionFilter : OncePerRequestFilter() {

    companion object {
        private val objectMapper = jacksonObjectMapper()
    }


    @Throws(ServletException::class, IOException::class)
    public override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val startTime = Instant.now()

        // Logging condition.
        if (isHealthCheck(request)) {
            chain.doFilter(request, response)
            return
        }

        // uri
        val uriMap: MutableMap<String, String> = HashMap()
        uriMap["URI"] = request.requestURI
        uriMap["Method"] = request.method
        uriMap["Client"] = request.remoteAddr
        request.queryString?.also { uriMap["QueryString"] = it }

        // header
        val headerNameList = request.headerNames
        val headerMap: MutableMap<String, String?> = HashMap()
        while (headerNameList.hasMoreElements()) {
            val headerName = headerNameList.nextElement()
            headerMap[headerName] = request.getHeader(headerName)
        }

        val requestWrapper = ContentCachingRequestWrapper(request)
        val responseWrapper = ContentCachingResponseWrapper(response)
        var requestObject: Any? = null
        var responseObject: Any? = null
        println("$uriMap, $headerMap")
            chain.doFilter(requestWrapper, responseWrapper)

            requestObject = when (requestWrapper.contentType) {
                MediaType.APPLICATION_JSON_VALUE, //application/json
                MediaType.APPLICATION_JSON_UTF8_VALUE, //application/json;charset=UTF-8
                "application/json; charset=utf-8",
                MediaType.APPLICATION_FORM_URLENCODED_VALUE//띄어쓰기문제로 로깅이 안되는 현상(tag server에서 들어오는)
                -> if (String(requestWrapper.contentAsByteArray).isNotEmpty()) objectMapper.readValue<Any>(requestWrapper.contentAsByteArray) else null
                else -> {
                    request.parameterMap
                }
            }
            responseObject = objectMapper.readValue<Any>(String(responseWrapper.contentAsByteArray))
            println("$requestObject, $responseObject")
    }
    private fun isHealthCheck(request: HttpServletRequest): Boolean {
        return "/health_check.html" == request.requestURI
    }

}