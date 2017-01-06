package org.ncibi.task;

import org.ncibi.db.ws.Task;

public interface TaskWriter<T, A>
{
    public void save(T what, A args, Task task);
}
