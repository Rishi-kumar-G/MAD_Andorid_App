package com.example.mad_admin.models

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

)
