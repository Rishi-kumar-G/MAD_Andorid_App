package com.example.mad_admin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.example.mad_admin.databinding.ProgressDialogBinding
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
        Toast.makeText(context,messege,Toast.LENGTH_LONG)
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

}