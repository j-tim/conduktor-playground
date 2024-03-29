package nl.jtim.spring.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import nl.jtim.conduktor.spring.kafka.avro.stock.quote.StockQuote;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StockQuoteConsumer {

    public static final String STOCK_QUOTES_TOPIC_NAME = "stock-quotes";

    @KafkaListener(topics = STOCK_QUOTES_TOPIC_NAME)
    public void on(StockQuote stockQuote, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) String partition) {
        log.info("Consumed from partition: {} value: {}", partition, stockQuote);
    }
}
