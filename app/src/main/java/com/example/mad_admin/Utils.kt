package com.example.mad_admin

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Filterable
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.mad_admin.databinding.ProgressDialogBinding
import com.example.mad_admin.models.Users
import com.gtappdevelopers.kotlingfgproject.ViewPagerAdapter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.regex.Pattern

object Utils {

    private var alert : AlertDialog? = null

    fun showProgress(context: Context, message: String) {

        val progress = ProgressDialogBinding.inflate(LayoutInflater.from(context))

        progress.messageTextView.text = message
        alert = AlertDialog.Builder(context).setView(progress.root).setCancelable(true).create()

        alert!!.show()

    }

    fun showToast(context: Context,messege: String){
        Toast.makeText(context,messege,Toast.LENGTH_LONG).show()
    }

    fun checkEmpty(view: TextView):Boolean{
        return (view.text=="")
    }

    fun setError(view: TextView,messege:String){
        view.setError(messege)
    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*"
        )
        val matcher = emailPattern.matcher(email)
        return matcher.matches()
    }

    fun hideProgressDialog(){
        alert?.dismiss()

    }

    fun getCurrentDate(): String {
        val today = LocalDate.now()
        return today.toString()
    }

    fun getCurrentTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")  // Change format as needed
        return current.format(formatter)
    }

    fun setListAdapter(context: Context,list:List<String>,view:AutoCompleteTextView){
        val adapter
                = ArrayAdapter(context,
            R.layout.simple_list_item_1, list)
        view.setAdapter(adapter)

        view.setOnFocusChangeListener { v, hasFocus ->
            run {
                if (hasFocus) view.showDropDown()
            }
        }

    }

    fun openMultiImagePicker(activity: Activity, requestCode: Int) {
        val intent = Intent.createChooser(
            Intent(Intent.ACTION_GET_CONTENT).setType("image/*").putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true),
            "Select Images"
        )
        // Check if there's an activity to handle the intent
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun setPagerAdapter(context:Context,viewPager: ViewPager,list:List<Uri>) {

        val adapter = ViewPagerAdapter(context,list)
        viewPager.adapter = adapter


    }


    fun putUser(context: Context,users: Users){
        val sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name",users.name)
        editor.putString("email",users.email)
        editor.putString("phone",users.phone)
        editor.putString("uid",users.uid)
        editor.putString("section",users.section)
        editor.putString("standard",users.standard)
        editor.apply()


    }

    fun getUser(context: Context) : Users{
        val sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE)

        val name = sharedPreferences.getString("name","")
        val email = sharedPreferences.getString("email","")
        val phone = sharedPreferences.getString("phone","")
        val uid = sharedPreferences.getString("uid","")
        val section = sharedPreferences.getString("section","")
        val standard = sharedPreferences.getString("standard","")
        val user = Users(uid=uid!!,name=name,email=email,phone=phone,section=section, standard = standard)
        return user
    }

    fun datePicker(context: Context,view:Button){

    }

    fun getCurrentMonth(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MMMM")  // Change format as needed
        return current.format(formatter)
    }

    fun getCurrentDay(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd")  // Change format as needed
        return current.format(formatter)
    }

    fun getCurrentYear(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("YY")  // Change format as needed
        return current.format(formatter)
    }

}