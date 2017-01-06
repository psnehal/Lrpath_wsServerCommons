package org.ncibi.rabbitmq;

import java.io.IOException;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;

final class RabbitMQUtil
{
    private RabbitMQUtil()
    {
    }

    public static Connection openConnection(String host)
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        try
        {
            Connection connection = factory.newConnection();
            return connection;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Unable to create connection to RabbitMQ Server: " + host);
        }
    }

    public static Channel createChannel(Connection connection)
    {
        try
        {
            Channel channel = connection.createChannel();
            return channel;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Unable to create channel to RabbitMQ Server.");
        }
    }

    public static void channelQos(Channel channel, int qos)
    {
        try
        {
            channel.basicQos(qos);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Unable to set qos for channel.");
        }
    }

    public static String openQueue(Channel channel, String queueName)
    {
        try
        {
            boolean durable = true;
            boolean exclusive = false;
            boolean autoDelete = false;
            Map<String, Object> args = null;
            String q = channel.queueDeclare(queueName, durable, exclusive, autoDelete, args).getQueue();
            return q;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Unable to create/open queue: " + queueName);
        }
    }

    public static void closeQueue(Channel channel, Connection connection)
    {
        closeChannel(channel);
        closeConnection(connection);
    }

    public static void closeChannel(Channel channel)
    {
        if (channel != null)
        {
            closeChannelIgnoringException(channel);
        }
    }

    private static void closeChannelIgnoringException(Channel channel)
    {
        try
        {
            channel.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void closeConnection(Connection connection)
    {
        if (connection != null)
        {
            closeConnectionIgnoringException(connection);
        }
    }

    private static void closeConnectionIgnoringException(Connection connection)
    {
        try
        {
            connection.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String basicConsume(String queueName, boolean autoAck, Consumer callback, Channel channel)
    {
        try
        {
            String tag = channel.basicConsume(queueName, autoAck, callback);
            return tag;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Unable to setup consumer.");
        }
    }

    public static void publish(String exchange, String queueName, String message, Channel channel)
    {
        try
        {
            channel.basicPublish(exchange == null ? "" : exchange, queueName,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Unable to publish message.");
        }
    }

    public static String receive(QueueingConsumer consumer)
    {
        try
        {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            return new String(delivery.getBody());
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Unable to receive message.");
        }
    }

    public static void exchangeDeclare(Channel channel, String exchange, String type)
    {
        try
        {
            channel.exchangeDeclare(exchange, type);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Unable to declare exchange: " + exchange + ", with type: "
                    + type + ".");
        }
    }
}
