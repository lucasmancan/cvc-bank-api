package br.com.cvcbank.converters;

public interface Converter<T, Z> {
    T dtoToEntity(Z z);

    Z entityToDTO(T t);
}
