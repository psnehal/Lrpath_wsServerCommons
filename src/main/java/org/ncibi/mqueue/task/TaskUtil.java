package org.ncibi.mqueue.task;

import java.util.UUID;

import org.ncibi.db.ws.TaskType;
import org.ncibi.db.ws.Task;
import org.ncibi.task.TaskStatus;

public class TaskUtil
{
    private TaskUtil()
    {
    }

    public static Task newQueuedTask(TaskType commandType)
    {
        UUID uuid = UUID.randomUUID();
        Task task = new Task(uuid.toString(), TaskStatus.QUEUED, commandType);
        return task;
    }
}
