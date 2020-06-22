package house.demo.mq;

import house.demo.Service.CollectionService;
import house.demo.bean.QueryVo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE)
public class MqCollectHandler {

    @Autowired
    CollectionService collectionService;

    @RabbitHandler
    public void handler(QueryVo vo){
        collectionService.addCollection(vo);
    }


}
