package no.item.kafka.consumer

import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.*


fun main(args:Array<String>){
    MockKafkaConsumer().run()
}

class MockKafkaConsumer {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    fun run(){
        logger.info("Starting consumer")
        val props: Properties = getProperties()
        val consumer: KafkaConsumer<String, String> = KafkaConsumer<String, String>(props)
        consumer.subscribe(Collections.singleton("entur.vehicle.activity"))

        while(true) {
            val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofMillis(100))
            logger.info("Got some records...")
            records.iterator().forEach {
                val record = it.value()
                //TODO:do something with this record...
            }
        }
    }
}