package com.example.mad_admin.models

import android.os.Parcel
import android.os.Parcelable

data class Student(


    var uid: String = null.toString(),
    var name: String ?= null,
    var email: String ?= null,
    var phone: String ?=null,
    var standard: String ?= null,
    var section: String ?= null,
    var status: String ?= null,





) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }
}