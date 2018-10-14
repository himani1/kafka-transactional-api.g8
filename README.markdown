A [Giter8][g8] template for showcasing implementation of Kafka's idempotent and transactional producer and consumer using Transactional API.

kafka-transactional-api
---

### Steps to install Zookeeper and Apache Kafka:

Step 1: Download Kafka

Download Kafka from [here](https://www.apache.org/dyn/closer.cgi?path=/kafka/2.0.0/kafka-2.0.0-src.tgz)

Step 2: Extract downloaded file

```bash
tar -xzvf kafka_2.11-2.0.0.tgz
cd kafka_2.11-2.0.0
```
### Steps to start Zookeeper and Kafka server :

Start Zookeeper:

```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```

Start Kafka server:

```bash
bin/kafka-server-start.sh config/server.properties
```

---
### Clone Project

```sbt new knoldus/kafka-transactional-api.g8
```
---
### Producing messages transactionally to the Kafka topic queue.

Execute the following command,

```bash
bin/activator "run-main com.knoldus.kafka.examples.demo.ProducerDemo"
```
This starts producing records transactionally into the Kafka topic queue.

---
### Consuming transactional messages which have been committed from the Kafka topic queue.

Step 1:
Execute the following command,

```bash
bin/activator "run-main com.knoldus.kafka.examples.demo.ConsumerDemo"
```

Template license
----------------
Written in <2018> by <HIMANI ARORA> <himani.arora@knoldus.in>

To the extent possible under law, the author(s) have dedicated all copyright and related
and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://creativecommons.org/publicdomain/zero/1.0/>.

[g8]: http://www.foundweekends.org/giter8/
