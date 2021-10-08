package anand.deepak.tasktimer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */

private const val TAG = "MainActivityFragment"
class MainActivityFragment : Fragment(), CursorRecyclerViewAdapter.OnTaskClickListener {

    interface OnTaskEdit {
        fun onTaskEdit(task: Task)
    }

    private val mAdapter = CursorRecyclerViewAdapter(null, this)
    private val viewModel by lazy { ViewModelProviders.of(requireActivity()).get(TaskTimerViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: Starts")
        super.onCreate(savedInstanceState)
        viewModel.cursor.observe(this, Observer { cursor -> mAdapter.swapCursor(cursor)?.close() })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: Starts")
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: called")
        super.onViewCreated(view, savedInstanceState)

        task_list.layoutManager = LinearLayoutManager(context)
        task_list.adapter = mAdapter
    }

    override fun onEditClick(task: Task) {
        (activity as OnTaskEdit?)?.onTaskEdit(task)
    }

    override fun onDeleteClick(task: Task) {
        viewModel.deleteTask(task.id)
    }

    override fun onTaskLongClick(task: Task) {
        TODO("Not yet implemented")
    }
}
