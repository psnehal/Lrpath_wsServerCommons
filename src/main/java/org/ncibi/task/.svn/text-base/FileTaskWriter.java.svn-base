package org.ncibi.task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.ncibi.db.ws.Task;

public abstract class FileTaskWriter<T, A> implements TaskWriter<T, A>
{
    protected abstract void writeResults(BufferedWriter out, Task task, T what, A args)
                throws IOException;

    @Override
    public void save(T what, A args, Task task)
    {
        try
        {
            FileWriter fw = new FileWriter(TaskFileUtil.taskFile(task));
            BufferedWriter out = new BufferedWriter(fw);
            writeResults(out, task, what, args);
            out.close();
            fw.close();
        }
        catch (IOException e)
        {
            throw new IllegalStateException("Unable to write results for task: " + task.getUuid());
        }
    }
}
