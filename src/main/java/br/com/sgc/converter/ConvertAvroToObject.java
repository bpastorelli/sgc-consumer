package br.com.sgc.converter;

public interface ConvertAvroToObject<T, Z> {

	T convert(Z avro);
	
}
