# Conduktor Playground

## Docker

Basic setup: [docker-compose.yml](docker-compose.yml)

| Component                                    | Port  | API / UI                                 | Description                      | Documentation |
|----------------------------------------------|-------|------------------------------------------|----------------------------------|---------------|
| Kafka broker                                 | 9092  |                                          | Single Node Kafka Cluster        |               |
| Conduktor Plaform                            | 80    | [Conduktor](http://localhost)            | Conduktor                        |               |
| Confluent Schema Registry                    | 8081  | [Schema Registry](http://localhost:8081) | Schema Registry for Avro Schemas |               |

Kafka connect: [docker-compose-kafka-connect.yml](docker-compose-kafka-connect.yml)

| Component                | Port  | API / UI                                            | Description                            | Documentation |
|--------------------------|-------|-----------------------------------------------------|----------------------------------------|---------------|
| Nginx                    | 8083  | [Nginx](http://localhost:8083)                      | Loadbalancer for Kafka connect cluster |               |
| Kafka Connect - Worker 1 | 18083 | [Kafka Connect - Worker 1](http://localhost:18083)  | 3 worker nodes cluster                 |               |
| Kafka Connect - Worker 2 | 28083 | [Kafka Connect - Worker 2](http://localhost:28083)  | "                                      |               |
| Kafka Connect - Worker 3 | 38083 | [Kafka Connect - Worker 3](http://localhost:38083)  | "                                      |               |

Conduktor Proxy: [docker-compose-conduktor-proxy.yml](docker-compose-conduktor-proxy.yml)

| Component                | Port              | Browser                                      | Description                     | Documentation |
|--------------------------|-------------------|----------------------------------------------|---------------------------------|---------------|
| Conduktor Proxy          | 8888, 6969 - 6970 | [Conduktor Proxy API](http://localhost:8888) | Proxy in front of Kafka cluster |               |

Kafka console producer / consumer [doc](docker-compose-console-consumer-producer.yml)


| Component                             | Port | Description | Documentation |
|---------------------------------------|------|-------------|---------------|
| Kafka broker used as console producer | none |             |               |  
| Kafka broker used as console consumer | none |             |               |  

### Single node Kafka cluster

```bash
docker-compose up -d
```

```bash
docker-compose down -v
```

### With Kafka Connect

Up

```bash
docker-compose -f docker-compose.yml -f docker-compose-kafka-connect.yml up -d 
```

Down

```bash
docker-compose -f docker-compose.yml -f docker-compose-kafka-connect.yml down -v
```

[Conduktor](http://localhost/admin/)

* admin:
  * username: `admin@admin.io`
  * password: `admin`

```
docker-compose up -d
```

```
docker-compose down -v
```

## Kafka Connect

```bash
docker-compose -f docker-compose.yml -f docker-compose-kafka-connect.yml up -d
```

```bash
docker-compose -f docker-compose.yml -f docker-compose-kafka-connect.yml down -v
```

Start generating random data using Kafka Connect Data Gen:

[IntelliJ Http Client - Kafka Connect API calls](http-calls/kafka-connect/kafka-connect-data-gen.http)

or using `curl`: 

Create connector:

```bash
curl -X POST http://localhost:8083/connectors -H "Content-Type: application/json" -d '{
  "name": "datagen-stock-quotes",
  "config": {
    "connector.class": "io.confluent.kafka.connect.datagen.DatagenConnector",
    "kafka.topic": "stock-quotes",
    "value.converter.schemas.enable": "true",
    "schema.filename": "/kafka-connect/datagen/avro/StockQuote.avsc",
    "schema.keyfield": "symbol",
    "max.interval": 10,
    "iterations": -1,
    "tasks.max": 1
  }
}'
```

Update configuration:

```bash
curl -X POST http://localhost:8083/connectors/datagen-stock-quotes/config -H "Content-Type: application/json" -d '{
  "connector.class": "io.confluent.kafka.connect.datagen.DatagenConnector",
  "kafka.topic": "stock-quotes",
  "value.converter.schemas.enable": "true",
  "schema.filename": "/kafka-connect/datagen/avro/StockQuote.avsc",
  "schema.keyfield": "symbol",
  "max.interval": 10,
  "iterations": -1,
  "tasks.max": 3
}'
```

## Conduktor proxy

Up:

```bash
docker-compose -f docker-compose.yml -f docker-compose-kafka-connect.yml -f docker-compose-conduktor-proxy.yml up -d
```

Down:

```bash
docker-compose -f docker-compose.yml -f docker-compose-kafka-connect.yml -f docker-compose-conduktor-proxy.yml down -v
```

## Console producer & consumer

Up:

```bash
docker-compose -f docker-compose.yml -f docker-compose-kafka-connect.yml -f docker-compose-conduktor-proxy.yml -f docker-compose-console-consumer-producer.yml up -d
```

Down:

```bash
docker-compose -f docker-compose.yml -f docker-compose-kafka-connect.yml -f docker-compose-conduktor-proxy.yml -f docker-compose-console-consumer-producer.yml down -v
```

## Example Repos

* https://github.com/conduktor/kafka-stack-docker-compose
* https://github.com/conduktor/conduktor-platform/
* https://github.com/conduktor/conduktor-platform/blob/main/example-local/
* https://github.com/conduktor/conduktor-proxy-demos/tree/main/chaos

## Documentation

* https://www.conduktor.io/kafka/how-to-start-kafka-using-docker
* Documentation: https://docs.conduktor.io/platform/

## Docker 

* https://github.com/conduktor/conduktor-platform/blob/main/doc/Configuration.md
* https://registry.hub.docker.com/r/conduktor/conduktor-platform/tags

## Configuration

[https://github.com/conduktor/conduktor-platform/blob/729d6521a785d116dafab20fcffc18abed47c87a/doc/Configuration.md](https://github.com/conduktor/conduktor-platform/blob/729d6521a785d116dafab20fcffc18abed47c87a/doc/Configuration.md)

## Changelog / Release notes

[Conduktor - Changelog](https://www.conduktor.io/changelog)