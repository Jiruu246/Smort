package au.com.smort.activities.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import au.com.smort.R

class ErrorDialog(val activity: Activity) {

    lateinit var dialog: AlertDialog
    fun startDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        val inflater: LayoutInflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.error_dialog, null))
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()
    }

    fun dismissDialog(){
        dialog.dismiss()
    }
}