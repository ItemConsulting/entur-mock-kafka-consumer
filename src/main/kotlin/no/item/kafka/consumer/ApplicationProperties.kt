package no.item.kafka.consumer

import java.util.*
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.serialization.StringDeserializer

// TODO: gå gjennom configen og finne ut hva som trengs for å knyttes mot entur...
// https://kafka.apache.org/documentation/#consumerconfigs
fun getProperties(): Properties {
  val properties = Properties()
  with(properties) {
    put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "b-3.enturmockkafkacluster.ni8qp8.c3.kafka.eu-north-1.amazonaws.com:9094,b-2.enturmockkafkacluster.ni8qp8.c3.kafka.eu-north-1.amazonaws.com:9094,b-1.enturmockkafkacluster.ni8qp8.c3.kafka.eu-north-1.amazonaws.com:9094")
    put(ConsumerConfig.GROUP_ID_CONFIG, "EnturMockKafkaConsumerGroup")
    put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer().javaClass.name)
    put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer().javaClass.name)
    put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")
  }
  return properties
}
