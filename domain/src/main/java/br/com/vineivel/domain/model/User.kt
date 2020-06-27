package br.com.vineivel.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String? = "",
    val name: String = "",
    val email: String? = "",
    var profilePicture: String? = "",
    var providerId: String? = ""
) : Parcelable