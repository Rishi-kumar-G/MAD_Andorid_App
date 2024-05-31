package com.example.mad_admin.models

data class Users(


    var uid: String = null.toString(),
    var name: String ?= null,
    var email: String ?= null,
    var phone: String ?=null

){}