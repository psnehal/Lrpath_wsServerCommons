package org.ncibi.db.ws;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "ws.QMessage")
@Table(name = "QMessage")
public class QMessage
{
    private int id;   
    private String message;
    private Queue queue;
    
    public QMessage()
    {      
    }
    
    public QMessage(String message, Queue queue)
    {
        this.message = message;
        this.queue = queue;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Column(name = "message")
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "queueid", nullable = false)
    public Queue getQueue()
    {
        return this.queue;
    }

    public void setQueue(Queue queue)
    {
        this.queue = queue;
    }
}
