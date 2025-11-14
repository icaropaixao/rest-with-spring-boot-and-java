package br.com.icaro.paixao.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    // Cria um mapeador (mapper) usando o Dozer,
    // que é uma biblioteca para copiar dados de um objeto para outro.
    // Exemplo: converter uma entidade para um DTO (Data Transfer Object).
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    // Metodo genérico que converte (ou "mapeia") um objeto de origem (origin)
    // para um objeto de destino (destination), que pode ser de outro tipo/classe.
    // <O, D> indica que o metodo e generico: O = tipo de origem, D = tipo de destino.
    public static <O, D> D parseObject(O origin, Class<D> destination) {

        // Usa o mapper do Dozer para criar uma nova instância de D,
        // copiando os atributos do objeto origin para o novo objeto.
        return mapper.map(origin, destination);
    }

    // Metodo genérico para converter uma lista de objetos de origem
    // em uma lista de objetos de destino (de outro tipo).
    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {

        // Cria uma nova lista que vai armazenar os objetos convertidos.
        List<D> destinationObjects = new ArrayList<D>();

        // Percorre cada objeto da lista de origem.
        for (Object o : origin) {

            // Converte o objeto atual (o) para o tipo de destino
            // e adiciona na nova lista.
            destinationObjects.add(mapper.map(o, destination));
        }

        // Retorna a lista já convertida.
        return destinationObjects;
    }
}

