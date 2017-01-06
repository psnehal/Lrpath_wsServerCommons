package org.ncibi.db.ws;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ws.TaskLog")
@Table(name = "TaskLog")
public class TaskLog
{
    private int id;
    private String logEntry;
    private String taskUuid; 
    private Date entryTimestamp;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Column(name = "logEntry")
    public String getLogEntry()
    {
        return this.logEntry;
    }

    public void setLogEntry(String logEntry)
    {
        this.logEntry = logEntry;
    }

    @Column(name = "taskUuid")
    public String getTaskUuid()
    {
        return this.taskUuid;
    }

    public void setTaskUuid(String taskUuid)
    {
        this.taskUuid = taskUuid;
    }

    @Column(name = "entryTimestamp")
    public Date getEntryTimestamp()
    {
        return this.entryTimestamp;
    }

    public void setEntryTimestamp(Date timestamp)
    {
        this.entryTimestamp = timestamp;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "TaskLog [id=" + this.id + ", taskUuid=" + this.taskUuid + ", logEntry="
                    + this.logEntry + ", entryTimestamp=" + this.entryTimestamp + "]";
    }
}
