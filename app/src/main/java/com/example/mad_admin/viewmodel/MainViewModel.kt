package com.example.mad_admin.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable.ConstantState
import android.net.Uri
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mad_admin.R
import com.example.mad_admin.Utils
import com.example.mad_admin.models.Attendence
import com.example.mad_admin.models.ChatUser
import com.example.mad_admin.models.Constants
import com.example.mad_admin.models.HomeWork
import com.example.mad_admin.models.Notification
import com.example.mad_admin.models.Student
import com.example.mad_admin.models.Users
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow

class MainViewModel : ViewModel() {

    private var firebaseStoreInstance = Firebase.firestore

    val _imageUriData = MutableStateFlow<List<Uri>?>(null)
    val _imageRecieve = MutableStateFlow<Boolean>(false)
    val _imageUploded = MutableStateFlow<Boolean>(false)
    val _homeWorkUploded = MutableStateFlow<Boolean>(false)
    val _NotificationUploded = MutableStateFlow<Boolean>(false)
    val _homeWorkData = HomeWork()
    val _AdminName = MutableStateFlow<String>("")
    val AdminName = _AdminName.value
    var _homeWork : HomeWork = HomeWork()

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

    fun setHomeWork(homeWork: HomeWork){

        Log.d("rishi2" , "setHomeWork: "+homeWork.toString())
        _homeWork=homeWork
        Log.d("rishi2" , "setHomeWork _ : "+_homeWork.toString())

    }

    fun getHomeWork(): HomeWork {
        Log.d("rishi2" , "getHomeWork: "+_homeWork.toString())

        return _homeWork
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

    fun fetchUser(uid: String) {
        FirebaseFirestore.getInstance().collection(Constants.CollectionAdminUser).document(uid).get().addOnSuccessListener {
            val user = it.toObject<Users>()
            _AdminName.value = user!!.name.toString()

        }
    }

    fun fetchStudent(uid: String) {
        FirebaseFirestore.getInstance().collection(Constants.CollectionStudents).document(uid).get().addOnSuccessListener {
            val user = it.toObject<Users>()
            _AdminName.value = user!!.name.toString()

        }
    }

    fun fetchStudents(standard: String,section:String):Flow<ArrayList<Student>> = callbackFlow {
        val db = Firebase.firestore.collection(Constants.CollectionStudents).whereEqualTo("standard",standard).whereEqualTo("section",section)


        db.get()

            .addOnSuccessListener { result ->
                val dataList = ArrayList<Student>()
                for (documents   in result) {
                    val hw = documents.toObject<Student>()

                    dataList.add(hw)

                }

                trySend(dataList)

            }.addOnFailureListener { exception ->
                // Handle data fetching failure
                Log.w("Firestore", "Error fetching data", exception)
            }
        awaitClose()




    }

    fun updateHomWork(context: Context, homeWork: HomeWork, old: String) {
        Utils.showProgress(context, "Updating HomeWork...")




        val firestore = Firebase.firestore
        val homeworkRef = firestore.collection(homeWork.standard.toString()+homeWork.section.toString()).document(homeWork.uid)// Replace with your collection name

        firestore.collection(old).document(homeWork.uid).delete().addOnSuccessListener{
            homeworkRef.set(homeWork).addOnSuccessListener{

                Utils.hideProgressDialog()
                _homeWorkUploded.value = true

            }.addOnFailureListener{
                Utils.hideProgressDialog()
                Log.d("rishi2" , it.toString())
                Utils.showToast(context,it.toString())
            }
        }



    }

    fun deleteHomeWork(context: Context, homeWork: HomeWork) {
        Utils.showProgress(context, "Deleting HomeWork...")
        val firestore = FirebaseFirestore.getInstance()
        val homeworkRef = firestore.collection(homeWork.standard!!).document(homeWork.uid)


        homeworkRef.delete().addOnSuccessListener {
            Utils.hideProgressDialog()
            _homeWorkUploded.value = true
        }
            .addOnFailureListener{
                Utils.hideProgressDialog()
                Utils.showToast(context,it.toString())
            }
    }

    fun DeletehomeWorkWithImages(context: Context, homeWork: HomeWork){
        Utils.showProgress(context, "Deleting HomeWork...")
        val firestore = FirebaseFirestore.getInstance()

        val homeworkRef = firestore.collection("${homeWork.standard!!}${homeWork.section!!}")
            .document(homeWork.uid)// Replace with your collection name
        if (homeWork.urls!=null ) {

            for (i in 0..<homeWork.urls!!.size){
                val storage = Firebase.storage.getReferenceFromUrl(homeWork.urls!![i])
                storage.delete().addOnSuccessListener {

                        if (i >= homeWork.urls!!.size - 1) {
                            homeworkRef.delete().addOnSuccessListener {
                                Utils.hideProgressDialog()
                                _homeWorkUploded.value = true
                            }
                                .addOnFailureListener {
                                    Utils.hideProgressDialog()
                                    Utils.showToast(context, it.toString())
                                }
                        }


                    }.addOnFailureListener {
                        Utils.hideProgressDialog()
                        Log.d("rishi", it.toString())
                        Utils.showToast(context, it.toString())
                    }

                }
        }
        else
        {
            homeworkRef.delete().addOnSuccessListener {
                Utils.hideProgressDialog()
                _homeWorkUploded.value = true
            }
                .addOnFailureListener{
                    Utils.hideProgressDialog()
                    Utils.showToast(context,it.toString())
                }
        }




    }

    @SuppressLint("ResourceAsColor")
    fun uploadAttendence(context: Context, data:ArrayList<Student>) {

        val firestore = FirebaseFirestore.getInstance()
        Utils.showProgress(context, "Uploading Attendence...")
        for (s in data){

            val attReffrence = firestore.collection(Constants.CollectionStudents).document(s.uid).collection(Constants.CollectionAttendence).document(Utils.getCurrentDate())// Replace with your collection name
            val att = Attendence(uid = s.uid,
                status = s.status,
                date = Utils.getCurrentDate(),
                month = Utils.getCurrentMonth(),
                year = Utils.getCurrentYear(),
                day = Utils.getCurrentDay())
            attReffrence.set(att).addOnSuccessListener{
            firestore.collection(Constants.CollectionStudents).document(s.uid).update("status",s.status)

            }.addOnFailureListener{
                Utils.showToast(context,it.toString())
                Utils.hideProgressDialog()
            }



        }
        Utils.showToast(context,"Attendence Uploded")
        Utils.hideProgressDialog()


    }

    @SuppressLint("SuspiciousIndentation")
    fun fetchChatList(): Flow<ArrayList<ChatUser>> = callbackFlow {
        val id = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        Log.d("rishi",id.toString())
        val db =getFireStoreInstance().collection(Constants.CollectionAdminUser).document(id).collection(Constants.CollectionMessege)



        db.get().addOnSuccessListener{
            var dataList = ArrayList<ChatUser>()
            dataList = it.toObjects(ChatUser::class.java) as ArrayList<ChatUser>
            trySend(dataList)
        }


        awaitClose()


    }






}