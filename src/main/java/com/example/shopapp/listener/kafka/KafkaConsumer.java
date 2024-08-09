//package com.example.shopapp.listener.kafka;
//
//import com.example.shopapp.models.ProductMessageCDC;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//
//
//@Component
//public class KafkaConsumer {
//    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
//    @KafkaListener(
//            topics = "${application.kafka.topic.debezium.kafka_topic_product}",
//            groupId = "consumer-product"
//    )
//    public void debeziumListener(ProductMessageCDC productMessageCDC){
//
//        if (productMessageCDC.getOp().equals("c")){
//            String temp = productMessageCDC.getAfter().getDescription();
//            logger.info("Debeziumdam create ***** ",temp);
//        }
//        if (productMessageCDC.getOp().equals("u")){
//            if (productMessageCDC.getAfter().getPrice()==0)
//
//            logger.info("Debeziumdam update ***** ",
//                    productMessageCDC.getBefore().getPrice(),
//                    productMessageCDC.getAfter().getPrice());
//        }
//
//    }
//
//}
