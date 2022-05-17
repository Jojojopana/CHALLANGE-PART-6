package com.binar.challange_part5.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailMovie(
    val title: String?=null,
    val keterangan: String?=null,
    val image: String?=null,
    val rating: String?=null,
): Parcelable