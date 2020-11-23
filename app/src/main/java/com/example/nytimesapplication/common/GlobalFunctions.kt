package com.example.nytimesapplication.common

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import com.example.nytimesapplication.BuildConfig
import com.example.nytimesapplication.common.models.NewsResponse
import com.example.nytimesapplication.newsDetails.presentation.ui.activities.NewsDetailsActivity

class GlobalFunctions {

    companion object {
        @JvmStatic
        fun printLn(msg: String?) {
            if (BuildConfig.DEBUG) println(msg)
        }
        @JvmStatic
        fun printException(e: Exception) {
            if (BuildConfig.DEBUG) e.printStackTrace()
        }
        @JvmStatic
        fun showToast(activity: Activity?, Message: String?) {
            Toast.makeText(activity, Message, Toast.LENGTH_SHORT).show()
        }
        @JvmStatic
        fun showDialog(activity: Activity, message: String?) {
            try {
                val diag = AlertDialog.Builder(activity)
                diag.setMessage(message)
                diag.setCancelable(false)
                diag.setPositiveButton(
                    "ok",
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                if (!activity.isFinishing) diag.show()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        fun goToNewsDetailsActivity(activity: Activity,newsBean:NewsResponse) {
            val intent = Intent(activity, NewsDetailsActivity::class.java)
            intent.putExtra(GlobalVars.NEWS_BEAN,newsBean)
            activity.startActivity(intent)
        }
    }
}