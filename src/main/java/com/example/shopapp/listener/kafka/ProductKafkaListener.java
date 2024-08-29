package com.example.shopapp.listener.kafka;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;


// Annotation này đánh dấu lớp này là một Spring bean.
@Component
// Tự động tạo constructor với tất cả các trường final hoặc trường được đánh dấu bằng @NonNull
@RequiredArgsConstructor
@Slf4j


// lắng nghe key từ confix mới tạo
public class ProductKafkaListener {

  private final IProductService productService;

  @KafkaListener(
      topics = "${application.kafka.topic.debezium.kafka_topic_product}",
      groupId = "consumer-product"
  )
  // Lắng nghe thông điệp từ chủ đề Kafka được cấu hình bởi thuộc tính  topics
  public void consumerProductListener(@Payload(required = false) String message) {
    if (Objects.isNull(message)) {
      return;
    }

    try {
//      var classCDC = MapperUtil.getMapper().readValue(message, ProductCDC.class);
//      var before = classCDC.getBefore();
//      var after = classCDC.getAfter();

      // handle cancel REGULAR class
//      if (UPDATE.equals(classCDC.getOperation())
//              && !ClassRegularStatus.CANCELED.equals(before.getStatus())
//              && ClassRegularStatus.CANCELED.equals(after.getStatus())
//      ) {
//        queueService.push(after.toClassRegularWithoutNotes(),
//                queueEventClassRegularCreateTaskArrangeClassDelay);
//      }
//    } catch (Exception ex) {
//      log.error("(createTaskArrangeClass)ex: {}", getFullStackTrace(ex));
//    }
      log.info("(consumerProductListener)message: {}", message);
//      CDC cdc = MapperUtil.getMapper().readValue(
//              message,
//              CDC.class
//          );
    } catch (Exception exception) {
      log.error(
          "(consumerProductListener)message: {}, exception: {}",
          message,
          ExceptionUtils.getStackTrace(exception)
      );
    }
  }

  @KafkaListener(
      topics = "${application.kafka.topic.create_product}",
      groupId = "create-product"
  )
  //  Lắng nghe thông điệp từ chủ đề Kafka được cấu hình bởi thuộc tính topics
  // @Payload : chỉ ra có thể là null
  public void kafkaCreateProduct(@Payload(required = false) String message) {
    if (Objects.isNull(message)) {
      return;
    }

    try {
      log.info("(kafkaCreateProduct)message: {}", message);
      // chuyển đổi thông điệp JSON từ Kafka thành đối tượng Product
      ProductDTO product = new ObjectMapper().readValue(message, ProductDTO.class);
        productService.createProduct(product);
      log.info("(kafkaCreateProduct)product: {}", product);
    } catch (Exception exception) {
      log.error(
              "(kafkaCreateProduct)message: {}, exception: {}",
              message,
          ExceptionUtils.getStackTrace(exception)
      );
    }
  }

}
