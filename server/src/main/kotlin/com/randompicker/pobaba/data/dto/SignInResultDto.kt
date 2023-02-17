package com.randompicker.pobaba.data.dto

import com.randompicker.pobaba.common.CommonResponse

data class SignInResultDto(
    val success: Boolean,
    val code: Int,
    val msg: String,
    val token: String,
) {
    companion object {
        fun from(token: String): SignInResultDto = SignInResultDto(
            success = true,
            code = CommonResponse.SUCCESS.code,
            msg = CommonResponse.SUCCESS.msg,
            token = token,
        )
    }
}