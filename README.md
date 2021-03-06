CLI based Messaging Client
==========================
[![Build Status](https://travis-ci.org/buehlmann/cli-messaging-client.svg?branch=master)](https://travis-ci.org/buehlmann/cli-messaging-client)  
Provides a simple command line based client to send and receive Messages from a java based message broker.

## Supported Messaging Protocols
- HornetQ core protocol used with native API (--protocol hornetq)
- HornetQ core protocol used with JMS API (--protocol hornetq-jms)
- ActiveMQ Artemis core protocol used with native API (--procotol artemis)
- ActiveMQ Artemis core protocol used with JMS API (--procotol artemis-jms)

## Build the client
```
mvn clean package
```

## Run the client
```
java -jar messaging-client.jar
```

## Example with parameters
Sends 50 messages in 10ms interval to the message queue "jms.queue.MyQueue":
```
java -jar messaging-client.jar --broker localhost:5445,localhost:5545 --destination jms.queue.MyQueue --method send --password letmein --user jms-user --protocol artemis --count 50 --size 2048 --xa --sleep 10
```

## Supported parameters
```
java -jar messaging-client.jar [options...]
 --broker VAL          : string with the broker(s) and their messaging ports.
                         e.g. broker1.localdomain:5500,broker2.localdomain:5500
 --count N             : repeat <n> times (default: 1)
 --destination VAL     : name of the queue or topic
 --loginterval N       : prints every nth message sent or received (default: 1)
 --message-content VAL : string with the message content
 --method VAL          : send or receive (default: send)
 --password VAL        : password used for authentication
 --print-message-body  : print text body of message to stdout (default: false)
 --protocol VAL        : defines the protocol to use. currently only hornetq is
                         supported (default: hornetq)
 --receive-timeout N   : receive timeout in ms
 --size N              : number of characters of the message payload (default:
                         1024)
 --sleep N             : millisecond sleep period between count (default: 0)
 --ssl                 : enabling / disabling ssl encrypted message transfer
                         (default: false)
 --user VAL            : username used for authentication
 --xa                  : enabling / disabling xa support (default: false)
```

## Planned features
* Close connection gracefully to message broker on ctrl+c
* Failover Handling
