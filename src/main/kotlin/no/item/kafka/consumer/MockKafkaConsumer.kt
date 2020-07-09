package no.item.kafka.consumer

import java.time.Duration
import java.util.*
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main(args: Array<String>) {
  MockKafkaConsumer().run()
}

class MockKafkaConsumer {
  private val logger: Logger = LoggerFactory.getLogger(javaClass)
  fun run() {
    logger.info("Starting consumer")
    val props: Properties = getProperties()
    val consumer: KafkaConsumer<String, String> = KafkaConsumer<String, String>(props)
    consumer.subscribe(Collections.singleton("EnturMockKafka-topic1"))

    while (true) {
      val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofMillis(1000))
      if (records.count() == 0) {
        continue
      }
      logger.info("Got some records...")
      records.iterator().forEach {
        val record: String = it.value()
        println(record)

      }
      consumer.close()
    }
  }
}
