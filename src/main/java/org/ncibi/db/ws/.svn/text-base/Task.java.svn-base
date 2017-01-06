package org.ncibi.db.ws;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ncibi.task.TaskStatus;

@Entity(name = "ws.Task")
@Table(name = "Task")
public class Task
{
    private int id;
    private String uuid;
    private TaskStatus status;    
    private TaskType taskType;
    private Object data;
    
    public Task()
    {    
    }
    
    public Task(String uuid, TaskStatus status, TaskType taskType)
    {
        this.uuid = uuid;
        this.status = status;
        this.taskType = taskType;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Column(name = "uuid")
    public String getUuid()
    {
        return this.uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public TaskStatus getStatus()
    {
        return this.status;
    }

    public void setStatus(TaskStatus status)
    {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public TaskType getTaskType()
    {
        return this.taskType;
    }

    public void setTaskType(TaskType type)
    {
        this.taskType = type;
    }
    
    @Transient
    public Object getData()
    {
        return this.data;
    }
    
    public void setData(Object data)
    {
        this.data = data;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Task [id=" + this.id + ", uuid=" + this.uuid + ", status=" + this.status
                    + ", type=" + this.taskType + "]";
    }
}
