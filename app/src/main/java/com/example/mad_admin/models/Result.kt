package com.example.mad_admin.models

import android.os.Parcel
import android.os.Parcelable

data class Result(

    var uid: String ?= null,
    var title: String ?= null,
    var score: String ?= null,
    var total: String ?= null,
    var percent: String ?= null,
    var date: String ?= null,
    var time: String ?= null,
    var auther: String ?= null,


): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),


    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(title)
        parcel.writeString(score)
        parcel.writeString(total)
        parcel.writeString(percent)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(auther)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Result> {
        override fun createFromParcel(parcel: Parcel): Result {
            return Result(parcel)
        }

        override fun newArray(size: Int): Array<Result?> {
            return arrayOfNulls(size)
        }
    }
}
