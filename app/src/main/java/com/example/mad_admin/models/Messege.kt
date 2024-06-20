package com.predator.mad_admin.models

import com.google.firebase.firestore.FieldValue

data class Messege(

    val sender_uid:String ?= null,
    val reciver_uid:String ?= null,
    var refference:String ?= null,
    val msg:String ?= null,
    val standard:String ?= null,
    val time:String ?= null,
    var date:String ?= null,
    val section:String ?= null,
    var timeStamp:String ?= null,



    ){}







