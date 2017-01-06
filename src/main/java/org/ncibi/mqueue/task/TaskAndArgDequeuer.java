package org.ncibi.mqueue.task;

import org.ncibi.db.ws.Task;

public class TaskAndArgDequeuer<T>
{
    private final TaskArgRetriever<T> argRetriever;
    private final PersistentTaskDequeuer taskDequeuer;

    public TaskAndArgDequeuer(PersistentTaskDequeuer taskDequeuer, TaskArgRetriever<T> argRetriever)
    {
        this.taskDequeuer = taskDequeuer;
        this.argRetriever = argRetriever;
    }
    
    public T retrieveArgsForNextTask()
    {
        Task t = taskDequeuer.dequeue();
        T args = argRetriever.retrieveArgsForTask(t);
        return args;
    }
}
