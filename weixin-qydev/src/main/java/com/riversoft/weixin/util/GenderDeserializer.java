package com.riversoft.weixin.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.riversoft.weixin.contact.bean.user.AbstractUser;

import java.io.IOException;

/**
 * Created by exizhai on 10/4/2015.
 */
public class GenderDeserializer extends JsonDeserializer<AbstractUser.Gender> {

    @Override
    public AbstractUser.Gender deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        //为何这里是string，status那里确实数字， 好纠结
        return "1".equals(jsonParser.getText()) ? AbstractUser.Gender.MALE : AbstractUser.Gender.FEMALE;
    }
}