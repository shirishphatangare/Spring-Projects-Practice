package com.example.spring.cache;

public interface BookRepository {

	Book getByIsbn(String isbn);
	Book updateBook(String isbn, String descriptor);
	void clearCache();
	void clearCacheByKey(String isbn);
	Book getSmallIsbn(String isbn);

}
