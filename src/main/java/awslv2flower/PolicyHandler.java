package awslv2flower;

import awslv2flower.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.Ordered;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCanceled_PaymentCancellation(@Payload OrderCanceled orderCanceled){

        if(orderCanceled.isMe()){
            System.out.println("##### listener PaymentCancellation : " + orderCanceled.toJson());

            Payment payment = new Payment();
            payment.setOrderId(orderCanceled.getId());
            payment.setStatus("PaymentCancelled");
//            payment.setStatus(orderCanceled.getStatus());

            paymentRepository.save(payment);
        }
    }

}
