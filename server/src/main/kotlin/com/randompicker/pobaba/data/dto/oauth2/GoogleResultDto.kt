package com.randompicker.pobaba.data.dto.oauth2

import jakarta.validation.constraints.Email

data class GoogleResultDto(
    val resourceName: String,
    val names: List<GoogleName>,
    val emailAddresses: List<GoogleEmail>,
    val photos: List<GooglePhotos>
) {

    data class GoogleName(
        val metadata: GoogleMetaData,
        val displayName: String,
    ) {}

    data class GoogleEmail(
        val metadata: GoogleMetaData,
        @Email val value: String,
    ) {}

    data class GooglePhotos(
        val metadata:  GoogleMetaData,
        val url: String,
        val default: Boolean,
    )

    data class GoogleMetaData(
        val primary: Boolean,
        val verified: Boolean,
        val source: Source,
        val sourcePrimary: Boolean
    ) {
        data class Source(
            val type: String,
            val id: String,
        ) {}
    }
}