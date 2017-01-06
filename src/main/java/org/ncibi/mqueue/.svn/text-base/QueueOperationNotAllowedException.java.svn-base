package org.ncibi.mqueue;

import org.ncibi.db.ws.QueueStatus;

@SuppressWarnings("serial")
public class QueueOperationNotAllowedException extends RuntimeException
{
    private final QueueStatus currentQueueStatus;
    
    public QueueOperationNotAllowedException(QueueStatus currentQueueStatus)
    {
        super();
        this.currentQueueStatus = currentQueueStatus;
    }
    
    public QueueOperationNotAllowedException(final String message, final QueueStatus currentQueueStatus, final Exception e)
    {
        super(message, e);
        this.currentQueueStatus = currentQueueStatus;
    }
    
    public QueueOperationNotAllowedException(final String message, final QueueStatus currentQueueStatus)
    {
        super(message);
        this.currentQueueStatus = currentQueueStatus;
    }
    
    public QueueStatus getCurrentQueueStatus()
    {
        return this.currentQueueStatus;
    }
}
