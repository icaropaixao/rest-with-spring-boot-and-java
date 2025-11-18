package br.com.icaro.paixao.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;


// Para formatar as respostas da requisição
public class GenderSerializer extends JsonSerializer<String> {


    @Override
    public void serialize(String gender, JsonGenerator gen,
           SerializerProvider serializerProvider) throws IOException {

                                                    //IF E ELSE
        String formatedGender = "Male".equals(gender) ? "M" : "F";

        gen.writeString(formatedGender); // pegando a resposta
    }
}
