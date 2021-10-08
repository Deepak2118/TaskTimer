package anand.deepak.tasktimer

class Util {

    fun AddTask(taskList: ArrayList<Task>, newTask: Task): Boolean
    {
        var isAdded = false
        if(!newTask.name.isNullOrEmpty() && newTask.sortOrder > 0)
        {
            taskList.add(newTask)
            isAdded = true
        }
        return isAdded
    }
}