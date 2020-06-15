package no.item.kafka.consumer

import java.util.*
import org.apache.kafka.common.serialization.StringDeserializer

// TODO: gå gjennom configen og finne ut hva som trengs for å knyttes mot entur...
// https://kafka.apache.org/documentation/#consumerconfigs
fun getProperties(): Properties {
  val properties = Properties()
  properties["bootstrap.servers"] = ""
  properties["key.deserializer"] = StringDeserializer::class.java
  properties["value.deserializer"] = StringDeserializer::class.java
  properties["group.id"] = "group-id"
  return properties
}
