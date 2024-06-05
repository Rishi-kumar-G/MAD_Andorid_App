package com.example.mad_admin.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mad_admin.Utils
import com.example.mad_admin.models.Constants
import com.example.mad_admin.models.HomeWork
import com.example.mad_admin.models.Notification
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.util.Util
import com.google.firebase.storage.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID

class MainViewModel : ViewModel() {

    private var firebaseStoreInstance = Firebase.firestore

    val _imageUriData = MutableStateFlow<List<Uri>?>(null)
    val _imageRecieve = MutableStateFlow<Boolean>(false)
    val _imageUploded = MutableStateFlow<Boolean>(false)
    val _homeWorkUploded = MutableStateFlow<Boolean>(false)
    val _NotificationUploded = MutableStateFlow<Boolean>(false)
    val _homeWorkData = MutableStateFlow<ArrayList<HomeWork>>(ArrayList())

    val Notificationuploded = _NotificationUploded
    val imageUriData = _imageUriData.value
    val imageRecieve = _imageRecieve

    val imageUploded = _imageUploded
    val homeWorkUploded = _homeWorkUploded

    private val _isHomeWorkUploaded = MutableLiveData<Boolean>(false)
    var isHomeWorkUploaded = _isHomeWorkUploaded
    fun addHomeWork(context: Context ,homeWork: HomeWork){
        Utils.showProgress(context,"Uploading HomeWork...")
        getFireStoreInstance().collection(Constants.CollectionHomeWork).document(homeWork.uid).set(homeWork).addOnSuccessListener{
            _isHomeWorkUploaded.value = true
            Utils.hideProgressDialog()
        }
    }



    fun getFireStoreInstance(): FirebaseFirestore {
        if (firebaseStoreInstance==null){
            firebaseStoreInstance = Firebase.firestore

        }
        return firebaseStoreInstance
    }

    fun uploadImages(context: Context,imageUris: List<Uri>, homeWork: HomeWork) {
        val storage = Firebase.storage
        Utils.showProgress(context,"Uploading All Images...")
        val downloadUris = ArrayList<String>()
        for (i in 0..<imageUris.size) {
            val fileName = "$i.jpg"
            val storageRef = storage.reference.child("images/${homeWork.uid}/$fileName")
            val uploadTask = storageRef.putFile(imageUris[i])
            uploadTask.addOnSuccessListener {
                it.metadata?.reference?.downloadUrl?.addOnSuccessListener{
                    downloadUris.add(it.toString())

                    if (downloadUris.size == imageUris.size){
                        Utils.hideProgressDialog()

                        homeWork.urls = downloadUris
                        uploadHomeWork(context,homeWork)

                    }
                    else{
                        Utils.hideProgressDialog()
                        Log.d("rishi","not uplkodede ")
                    }
                }


            }
        }



    }

    fun uploadHomeWork(context: Context, homeWork: HomeWork) {
        Utils.hideProgressDialog()
        Utils.showProgress(context,"Uploading HomeWork...")
        val firestore = FirebaseFirestore.getInstance()
        val homeworkRef = firestore.collection(homeWork.standard.toString()+homeWork.section.toString()).document(homeWork.uid)// Replace with your collection name

        homeworkRef.set(homeWork).addOnSuccessListener{
            Utils.hideProgressDialog()
            _homeWorkUploded.value = true



        }.addOnFailureListener{
            Utils.showToast(context,it.toString())
        }
    }

    fun uploadNotification(context: Context, notification: Notification) {
        Utils.hideProgressDialog()
        Utils.showProgress(context,"Uploading Notification...")
        val firestore = FirebaseFirestore.getInstance()
        val homeworkRef = firestore.collection(Constants.CollectionNotification).document(notification.uid)// Replace with your collection name

        homeworkRef.set(notification).addOnSuccessListener{
            Utils.hideProgressDialog()
            _NotificationUploded.value = true

        }.addOnFailureListener{
            Utils.showToast(context,it.toString())
        }
    }

//    fun fetchHomeWork(standard: String,section:String,date: String): ArrayList<HomeWork> {
//
//        val dataList = ArrayList<HomeWork>()
//        Log.d("rishi","$standard$section $date")
//        Firebase.firestore.collection("$standard$section")
//            .whereEqualTo("date", date)
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val data = HomeWork(uid=document.getString("uid")!!,
//                        standard=document.getString("standard"),
//                        title = document.getString("title"),
//                        section = document.getString("section"),
//                        date = document.getString("date"),
//                        urls = document.get("urls") as ArrayList<String>,
//                        auther  = document.getString("auther"),
//                        subject = document.getString("subject")
//                    )
//                    dataList.add(data)
//
//                }
//
//
//            }
//            .addOnFailureListener { exception ->
//                // Handle data fetching failure
//                Log.w("Firestore", "Error fetching data", exception)
//            }
//
//        return dataList
//
//
//
//
//    }

    fun fetchData(standard: String,section:String,date: String):Flow<ArrayList<HomeWork>> = callbackFlow {
        val db = Firebase.firestore.collection("$standard$section")


       db.get()

            .addOnSuccessListener { result ->
                val dataList = ArrayList<HomeWork>()
                for (documents   in result) {
                    val hw = documents.toObject<HomeWork>()
                    if (hw.date == date){
                        dataList.add(hw)
                    }

                }

                trySend(dataList)

        }.addOnFailureListener { exception ->
            // Handle data fetching failure
            Log.w("Firestore", "Error fetching data", exception)
        }
        awaitClose()




    }





}