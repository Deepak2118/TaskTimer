package anand.deepak.tasktimer

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDialogFragment

private val TAG = "AppDialog"

const val DIALOG_ID = "id"
const val DIALOG_MESSAGE = "message"
const val DIALOG_POSITIVE_RID = "positive_rid"
const val DIALOG_NEGATIVE_RID = "negative_rid"

class AppDialog : AppCompatDialogFragment() {

    private var dialogEvents: DialogEvents? = null

    internal interface DialogEvents {
        fun onPositiveDialogResult(dialogId : Int, args: Bundle)
        //fun onNegativeDialogResult(dialogId : Int, args: Bundle)
        //fun onDialogCancelled(dialogId : Int)
    }

    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach: called with context $context")
        super.onAttach(context)
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach: called")
        super.onDetach()
    }
}