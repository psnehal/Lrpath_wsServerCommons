package org.ncibi.mqueue.persistent;

import java.util.List;

import org.hibernate.Session;
import org.ncibi.db.PersistenceSession;
import org.ncibi.db.ws.QMessage;
import org.ncibi.db.ws.Queue;
import org.ncibi.db.ws.QueueStatus;
import org.ncibi.hibernate.SessionFunction;
import org.ncibi.hibernate.SessionProcedure;
import org.ncibi.hibernate.Sessions;
import org.ncibi.mqueue.Message;
import org.ncibi.mqueue.MessageQueue;
import org.ncibi.mqueue.QueueOperationNotAllowedException;

abstract class AbstractPersistentMessageQueue implements MessageQueue
{
    private final PersistenceSession persistence;
    private Queue queue;
    protected abstract String hqlForNextTask(Queue queue);

    public AbstractPersistentMessageQueue(PersistenceSession persistence, String queueName)
    {
        this.persistence = persistence;
        identifyQueueForQueueNameCreatingIfNeeded(queueName);
    }

    private void identifyQueueForQueueNameCreatingIfNeeded(String queueName)
    {
        queue = persistence.hqlQuery("from ws.Queue where name = '" + queueName + "'").single();
        if (queue == null)
        {
            createQueue(queueName);
        }
    }

    private void createQueue(final String queueName)
    {
        final Queue queueToCreate = new Queue();
        queueToCreate.setName(queueName);
        queueToCreate.setStatus(QueueStatus.ACCEPTING_AND_PROCESSING);
        queue = Sessions.withSession(persistence.session(), new SessionFunction<Queue>()
        {
            @Override
            public Queue apply(Session session)
            {
                session.saveOrUpdate(queueToCreate);
                Queue q = (Queue) session.createQuery(
                        "from ws.Queue where name = '" + queueName + "'").uniqueResult();
                return q;
            }
        });
    }

    @Override
    public void deleteMessage(Message m)
    {
        Session s = persistence.session();
        final QMessage qm = retrieveMessageByUuid(s, m.getMessage());
        if (qm != null)
        {
            persistence.delete(s, qm);
        }
    }

    private QMessage retrieveMessageByUuid(Session session, String uuid)
    {
        String hql = "from ws.QMessage where message = '" + uuid + "' and queueid = "
                + queue.getId();
        final QMessage qm = persistence.hqlQuery(session, hql).single();
        return qm;
    }

    @Override
    public void deleteQueue()
    {
        Sessions.withSession(persistence.session(), new SessionProcedure()
        {
            @Override
            public void apply(Session session)
            {
                session.refresh(queue);
                session.delete(queue);
            }
        });
    }

    @Override
    public void putMessage(final Message m)
    {
        if (queueIsAcceptingRequests())
        {
            addMessageToQueue(m);
        }
        else
        {
            throw new QueueOperationNotAllowedException("Queue " + queue.getName()
                    + " does not currently allow new items to be added.", queue.getStatus());
        }
    }

    private boolean queueIsAcceptingRequests()
    {
        refreshQueueState();
        return queue.isAcceptingRequests();
    }

    private void refreshQueueState()
    {
        Sessions.withSession(persistence.session(), new SessionProcedure()
        {
            @Override
            public void apply(Session session)
            {
                session.refresh(queue);
            }
        });
    }

    private void addMessageToQueue(final Message m)
    {
        Sessions.withSession(persistence.session(), new SessionProcedure()
        {
            @Override
            public void apply(Session session)
            {
                QMessage qm = new QMessage(m.getMessage(), queue);
                session.saveOrUpdate(qm);
            }
        });
    }

    @Override
    public Message retrieveAndDeleteNextMessage()
    {
        if (queueIsProcessing())
        {
            return retrieveAndDeleteSingleMessageWrappingOperationsInSingleTransaction();
        }

        throw new QueueOperationNotAllowedException("Queue " + queue.getName()
                + " doesn't currently allow processing of requests.", queue.getStatus());
    }

    private boolean queueIsProcessing()
    {
        refreshQueueState();
        return queue.isProcessing();
    }

    private Message retrieveAndDeleteSingleMessageWrappingOperationsInSingleTransaction()
    {
        return Sessions.withSession(persistence.session(), new SessionFunction<Message>()
        {
            @Override
            public Message apply(Session session)
            {
                QMessage qm = (QMessage) session.createQuery(
                        "from ws.QMessage where queueid = " + queue.getId()).setMaxResults(1)
                        .uniqueResult();
                deleteFromDatabaseHandlingNull(session, qm);
                return createMessageFromQMessageHandlingNull(qm);
            }
        });
    }

    private Message createMessageFromQMessageHandlingNull(QMessage qm)
    {
        if (qm != null)
        {
            Message m = new Message(qm.getMessage());
            return m;
        }
        else
        {
            return null;
        }
    }

    private void deleteFromDatabaseHandlingNull(Session session, QMessage qm)
    {
        if (qm != null)
        {
            session.delete(qm);
        }
    }

    @Override
    public Message retrieveNextMessage()
    {
        if (queue.isProcessing())
        {
            return retrieveSingleMessage();
        }

        throw new QueueOperationNotAllowedException("Queue " + queue.getName()
                + " doesn't currently allow processing of requests.", queue.getStatus());
    }

    private Message retrieveSingleMessage()
    {
        String hql = hqlForNextTask(queue);
        QMessage qm = persistence.hqlQuery(hql).single();   //"from ws.QMessage where queueid = " + queue.getId())
        if (qm != null)
        {
            Message m = new Message(qm.getMessage());
            return m;
        }

        return null;
    }

    @Override
    public void purgeQueue()
    {
        Sessions.withSession(persistence.session(), new SessionProcedure()
        {
            @Override
            public void apply(Session session)
            {
                @SuppressWarnings("unchecked")
                List<QMessage> messages = session.createQuery("from ws.QMessage").list();
                for (QMessage message : messages)
                {
                    session.delete(message);
                }
            }
        });
    }
}
