package anand.deepak.tasktimer

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_add_edit.*


private const val TAG = "AddEditFragment"

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TASK = "task"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddEditFragment.OnSaveClicked] interface
 * to handle interaction events.
 * Use the [AddEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AddEditFragment : Fragment() {
    private var task: Task? = null
    private var listener: OnSaveClicked? = null
    private val viewModel by lazy { ViewModelProviders.of(requireActivity()).get(TaskTimerViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: starts")
        super.onCreate(savedInstanceState)
        task = arguments?.getParcelable(ARG_TASK)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: starts")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated: starts")
        super.onActivityCreated(savedInstanceState)

        addedit_save.setOnClickListener {
            saveTask()
            listener?.onSaveClicked()
        }
    }

    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach: starts")
        super.onAttach(context)
        if (context is OnSaveClicked) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnSaveClicked")
        }
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach: starts")
        super.onDetach()
        listener = null
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG,"onViewCreated: started")
        val task = task
        if (savedInstanceState == null) {
            if (task != null) {
                Log.d(TAG,"onViewCreated: task found ${task.id}")
                addedit_name.setText(task.name)
                addedit_description.setText(task.description)
                addedit_sortorder.setText(Integer.toString(task.sortOrder))
            } else {
                Log.d(TAG,"onViewCreated: new task")
            }
        }
    }

    private fun taskFromUi(): Task {
        val sortOrder = if (addedit_sortorder.text.isNotEmpty()) {
            Integer.parseInt(addedit_sortorder.text.toString())
        } else {
            0
        }

        val newTask = Task(addedit_name.text.toString(), addedit_description.text.toString(), sortOrder)
        newTask.id = task?.id ?: 0

        return newTask
    }

    private fun saveTask() {
        // Create a newTask object to save, then call the viewModel's saveTask function to save it.
        // Task is a data class, so we can compare the new details with the original task,
        // and only save if there is a change.

        val newTask = taskFromUi()
        if (newTask != task) {
            Log.d(TAG, "saveTask: saving a task with id ${newTask.id}")
            task = viewModel.saveTask(newTask)
            Log.d(TAG, "saveTask: save task id ${task?.id}")
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnSaveClicked {
        fun onSaveClicked()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param task The task to be edited, or null to add a new task.
         * @return A new instance of fragment AddEditFragment.
         */
        @JvmStatic
        fun newInstance(task: Task?) =
            AddEditFragment().apply {
                    arguments = Bundle().apply {
                    putParcelable(ARG_TASK, task)
                }
            }
    }
}

//fun createFrag(task: Task) {
//    val args = Bundle()
//    args.putParcelable(ARG_TASK, task)
//    val fragment = AddEditFragment()
//    fragment.arguments = args
//}
//
//fun createFrag2(task: Task) {
//    val fragment = AddEditFragment().apply {
//        arguments = Bundle().apply {
//            putParcelable(ARG_TASK, task)
//        }
//    }
//}
//
//fun simpler(task: Task) {
//    val fragment = AddEditFragment.newInstance(task)
//}