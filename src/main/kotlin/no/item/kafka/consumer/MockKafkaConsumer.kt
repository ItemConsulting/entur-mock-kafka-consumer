package no.item.kafka.consumer

import java.time.Duration
import java.util.Collections
import java.util.Properties
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer

fun main() {
  MockKafkaConsumer().run()
}

class MockKafkaConsumer {
  fun run() {
    println("Starting consumer")

    val props: Properties = getProperties()
    val consumer: KafkaConsumer<String, String> = KafkaConsumer(props)
    consumer.subscribe(Collections.singleton("EnturMockKafka-topic1"))

    while (true) {
      val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofMillis(1000))
      if (records.count() == 0) {
        continue
      }
      println("Got some records....")
      records.iterator().forEach {
        val record: String = it.value()
        println(record)
      }
      consumer.close()
    }
  }
}
