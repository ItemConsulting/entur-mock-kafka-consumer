package no.item.kafka.consumer

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.time.Duration
import java.util.*
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main() {
  MockKafkaConsumer().run()
}

class MockKafkaConsumer {
  private val logger: Logger = LoggerFactory.getLogger(javaClass)
  private val fileName = "logfile.txt"
  private val file = File(fileName)
  @Suppress("UNUSED_VARIABLE")
  fun run() {
    val isNewFileCreated: Boolean = file.createNewFile()
    val startup = "Starting consumer\n"
    writeToFile(startup)

    val props: Properties = getProperties()
    val consumer: KafkaConsumer<String, String> = KafkaConsumer(props)
    consumer.subscribe(Collections.singleton("EnturMockKafka-topic1"))

    while (true) {
      val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofMillis(1000))
      if (records.count() == 0) {
        continue
      }
      val text = "Got some records...."
      writeToFile(text)
      records.iterator().forEach {
        val record: String = it.value()
        writeToFile(record)
      }
      consumer.close()
    }
  }
  private fun writeToFile(message: String) {
    logger.info(message)
    Files.write(Paths.get(file.absolutePath), message.toByteArray(), StandardOpenOption.APPEND)
  }
}
