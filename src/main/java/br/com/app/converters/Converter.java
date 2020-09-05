package br.com.app.converters;

import br.com.app.entities.AbstractEntity;

public interface Converter<T extends AbstractEntity, Z> {
    T dtoToEntity(Z z);

    Z entityToDTO(T t);
}
