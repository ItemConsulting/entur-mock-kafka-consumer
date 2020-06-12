package no.item.kafka.consumer

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main(args:Array<String>){
    MockKafkaConsumer().run()
}

class MockKafkaConsumer {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    fun run(){
        logger.debug("running")
    }
}