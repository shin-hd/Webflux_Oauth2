package com.randompicker.pobaba.common

enum class CommonResponse(
    val code: Int,
    val msg: String
) {
    SUCCESS(0, "Success"), FAIL(-1, "Fail")
}