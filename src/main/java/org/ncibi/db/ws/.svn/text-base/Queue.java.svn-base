package org.ncibi.db.ws;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name = "ws.Queue")
@Table(name = "Queue")
public class Queue
{
    private int id;
    private String name;
    private QueueStatus status;
    private Set<QMessage> messages = new HashSet<QMessage>();

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Column(name = "name")
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public QueueStatus getStatus()
    {
        return this.status;
    }

    public void setStatus(QueueStatus status)
    {
        this.status = status;
    }

    @OneToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    }, fetch = FetchType.LAZY, mappedBy = "queue")
    public Set<QMessage> getMessages()
    {
        return this.messages;
    }

    public void setMessages(Set<QMessage> messages)
    {
        this.messages = messages;
    }

    @Transient
    public boolean isAcceptingRequests()
    {
        switch (status)
        {
            case ACCEPTING:
            case ACCEPTING_AND_PROCESSING:
                return true;
            default:
                return false;
        }
    }

    @Transient
    public boolean isProcessing()
    {
        switch (status)
        {
            case ACCEPTING_AND_PROCESSING:
            case PROCESSING:
                return true;
            default:
                return false;
        }
    }
}
