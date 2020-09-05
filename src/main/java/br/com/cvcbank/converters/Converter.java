package br.com.cvcbank.converters;

public interface Converter<T extends AbstractEntity, Z> {
    T dtoToEntity(Z z);

    Z entityToDTO(T t);
}
