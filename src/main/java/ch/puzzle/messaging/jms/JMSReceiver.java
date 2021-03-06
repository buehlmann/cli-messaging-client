package ch.puzzle.messaging.jms;

import ch.puzzle.messaging.Configuration;

import org.hornetq.api.core.client.ClientMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by ben on 18.11.16.
 */
public class JMSReceiver {
    private final Logger logger = LoggerFactory.getLogger(JMSReceiver.class);

    private Configuration configuration;
    private JMSInitializer initializer;

    public JMSReceiver(Configuration configuration) {
        this.configuration = configuration;
        this.initializer = new JMSInitializer(configuration);
    }

    public void process() {
        Session session = null;
        MessageConsumer consumer = null;

        try {
            session = initializer.createJMSSession();
            Queue queue = initializer.createJMSQueue();
            logger.info("Successfully connected to message broker {}", session);

            consumer = session.createConsumer(queue);

            for (int i = 0; i < configuration.getCount(); i++) {
                try {
                    TextMessage message;
                    if(configuration.getReceiveTimeout() == null) {
                        message = (TextMessage) consumer.receive();
                    } else {
                        message = (TextMessage) consumer.receive(configuration.getReceiveTimeout());
                    }
                    if(message == null) {
                        logger.error("Did not receive any expected message during configured timeout of {} ms", configuration.getReceiveTimeout());                        
                        System.exit(1);
                    }
                    if (i % configuration.getLoginterval() == 0) {
                        logger.info("Received message #{}: {}", i + 1, message);
                    }
                } catch (JMSException e) {
                    logger.error("Error occured while trying to receive message: {}", e.getMessage());
                    System.exit(1);                    
                }
                if (configuration.getSleep() > 0) {
                    try {
                        Thread.currentThread().sleep(configuration.getSleep());
                    } catch (InterruptedException e) {
                        System.exit(1);                        
                    }
                }
            }
        } catch (JMSException e) {
            logger.error("Error occurred while trying to creating producer: {}", e.getMessage());
            System.exit(1);            
        } finally {
            try {
                if (consumer != null)
                    consumer.close();
                if (session != null)
                    session.close();
            } catch (JMSException e) {
            }
        }
    }
}
