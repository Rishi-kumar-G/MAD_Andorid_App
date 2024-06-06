package com.example.mad_admin.models

import android.os.Parcel
import android.os.Parcelable

data class HomeWork(

    var uid: String = null.toString(),
    var title: String ?= null,
    var desc: String ?= null,
    var section: String ?= null,
    var subject: String ?= null,
    var date: String ?= null,
    var time: String ?= null,
    var auther: String ?= null,
    var standard: String ?=null,
    var urls: ArrayList<String> ?= null

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("urls")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeString(section)
        parcel.writeString(subject)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(auther)
        parcel.writeString(standard)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeWork> {
        override fun createFromParcel(parcel: Parcel): HomeWork {
            return HomeWork(parcel)
        }

        override fun newArray(size: Int): Array<HomeWork?> {
            return arrayOfNulls(size)
        }
    }
}
