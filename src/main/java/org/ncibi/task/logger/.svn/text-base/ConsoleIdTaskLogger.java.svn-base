package org.ncibi.task.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.ncibi.db.ws.Task;

public final class ConsoleIdTaskLogger implements TaskLogger
{
    private final String id;
    private final DateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final Calendar calendar = Calendar.getInstance();

    public ConsoleIdTaskLogger(String id)
    {
        this.id = id;
    }

    @Override
    public void logStart(Task task)
    {
        System.out.printf("\n%s [%s]: Starting task with uuid:%s, CommandType: %s%n", timeNow(), id,
                task.getUuid(), task.getTaskType());
    }

    @Override
    public void logFinish(Task task)
    {
        System.out.printf("%s [%s]: Finished task with uuid: %s, CommandType: %s%n", timeNow(), id,
                task.getUuid(), task.getTaskType());
    }

    @Override
    public void logError(Task task, Exception e)
    {
        System.out.printf("\n%s [%s]: Error for task with uuid: %s threw exception: ", timeNow(), id,
                task.getUuid(), e.toString());
    }

    @Override
    public void logError(Task task, String reason)
    {
        System.out.printf("\n%s [%s]: Error for task with uuid: %s with message: %s%n", timeNow(), id,
                task.getUuid(), reason);
    }

    @Override
    public void logMessage(Task task, String message)
    {
        System.out.printf("%s [%s]: Message for task with uuid: %s, CommandType %s, Message: %s%n",
                timeNow(), id, task.getUuid(), task.getTaskType(), message);
    }

    @Override
    public void logMessage(String message)
    {
        System.out.printf("%s [%s]: %s%n", timeNow(), id, message);
    }

    private String timeNow()
    {
        return dateFormatter.format(calendar.getTime());
    }

}
