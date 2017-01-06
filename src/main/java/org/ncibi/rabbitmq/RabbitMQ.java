package org.ncibi.rabbitmq;

import org.ncibi.mqueue.Message;
import org.ncibi.mqueue.MessageQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RabbitMQ implements MessageQueue
{
    private Connection connection = null;
    private Channel channel = null;
    private QueueingConsumer consumer = null;
    private final String queueName;
    private String exchange;
    
    private RabbitMQ(String queueName, String host, String exchange)
    {
        try
        {
            this.queueName = queueName;
            this.exchange = exchange;
            initializeQueue(queueName, host);
        }
        catch (Exception e)
        {
            RabbitMQUtil.closeQueue(channel, connection);
            throw new IllegalStateException("Unable to initialize queue: " + queueName + "/" + host);
        }
    }
    
    private void initializeQueue(String queueName, String host)
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        connection = RabbitMQUtil.openConnection(host);
        channel = RabbitMQUtil.createChannel(connection);
        RabbitMQUtil.openQueue(channel, queueName);
    }
    
    public static RabbitMQ createQueueAsReceiver(String queueName, String host)
    {
        return new RabbitMQ(queueName, host, null).makeConsumer();
    }
    
    public static RabbitMQ createQueueAsReceiverUsingExchange(String queueName, String host, String exchange)
    {
        RabbitMQ mq = createQueueAsReceiver(queueName, host);
        RabbitMQUtil.exchangeDeclare(mq.channel, exchange, "topic");
        mq.exchange = exchange;
        return mq;
    }
    
    private RabbitMQ makeConsumer()
    {
        RabbitMQUtil.channelQos(channel, 1);
        consumer = new QueueingConsumer(channel);
        boolean autoAck = false;
        RabbitMQUtil.basicConsume(queueName, autoAck, consumer, channel);
        return this;
    }
    
    public static RabbitMQ createQueueAsSender(String queueName, String host)
    {
        return new RabbitMQ(queueName, host, null);
    }  
    
    public static RabbitMQ createQueueAsSenderUsingExchange(String queueName, String host, String exchange)
    {
        RabbitMQ mq = createQueueAsSender(queueName, host);
        RabbitMQUtil.exchangeDeclare(mq.channel, exchange, "topic");
        mq.exchange = exchange;
        return mq;
    }

    @Override
    public void deleteMessage(Message m)
    {
        throw new UnsupportedOperationException("deleteMessage method not supported");
    }

    @Override
    public void deleteQueue()
    {
        throw new UnsupportedOperationException("deleteQueue method not supported.");
    }

    @Override
    public void purgeQueue()
    {
        throw new UnsupportedOperationException("purgeQueue method not supported.");
    }

    @Override
    public void putMessage(Message m)
    {
        RabbitMQUtil.publish(exchange, queueName, m.getMessage(), channel);
    }

    @Override
    public Message retrieveAndDeleteNextMessage()
    {
        String message = RabbitMQUtil.receive(consumer);
        Message m = new Message(message);
        return m;
    }

    @Override
    public Message retrieveNextMessage()
    {
        return retrieveAndDeleteNextMessage();
    }

}
