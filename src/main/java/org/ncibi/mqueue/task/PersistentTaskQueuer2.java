package org.ncibi.mqueue.task;

import java.beans.XMLEncoder;

import org.hibernate.Session;
import org.ncibi.commons.lang.PreCond;
import org.ncibi.db.PersistenceSession;
import org.ncibi.db.ws.ServiceArguments;
import org.ncibi.db.ws.Task;
import org.ncibi.hibernate.SessionProcedure;
import org.ncibi.hibernate.Sessions;
import org.ncibi.mqueue.Message;
import org.ncibi.mqueue.MessageQueue;
import org.ncibi.ws.AbstractBeanXMLEncoder;

public class PersistentTaskQueuer2<T> implements TaskQueuer<T>
{
    private final MessageQueue queue;
    private final PersistenceSession persistence;

    public PersistentTaskQueuer2(MessageQueue queue, PersistenceSession persistence)
    {
        this.queue = queue;
        this.persistence = persistence;
    }

    @Override
    public void queue(final Task task, final T args)
    {
        PreCond.require(task != null);
        PreCond.require(args != null);
        addTaskAndArgsToDatabase(task, args);
        addTaskToQueue(task);
    }

    private void addTaskAndArgsToDatabase(final Task task, final T args)
    {
        Sessions.withSession(persistence.session(), new SessionProcedure()
        {
            @Override
            public void apply(Session session)
            {
                ServiceArguments s = createServiceArgumentsFromArgsObject(args);
                session.saveOrUpdate(task);
                session.saveOrUpdate(s);
            }

            private ServiceArguments createServiceArgumentsFromArgsObject(T args)
            {
                String argsXml = translateArgsObjectToXml(args);
                ServiceArguments s = new ServiceArguments();
                s.setClassName(args.getClass().getName());
                s.setUuid(task.getUuid());
                System.out.println("argsXml :"+argsXml.length());
                s.setArgsXml(argsXml);
                return s;
            }

            private String translateArgsObjectToXml(T args)
            {
                AbstractBeanXMLEncoder<T> encoder = new AbstractBeanXMLEncoder<T>()
                {

                    @Override
                    protected void setupPersistenceDelegatesForType(XMLEncoder encoder)
                    {
                        // nothing to do
                    }

                };
                encoder.setObjectToEncode(args);
                return encoder.toXmlString();
            }

        });
    }

    private void addTaskToQueue(Task task)
    {
        Message m = new Message(task.getUuid());
        queue.putMessage(m);
    }

}
