package com.example.spring.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SimpleBookRepository implements BookRepository {
    /*
     * When the cache needs to be updated without interfering with the method
     * execution, you can use the @CachePut annotation. That is, the method is
     * always executed and its result is placed into the cache (according to
     * the @CachePut options). It supports the same options as @Cacheable and should
     * be used for cache population rather than method flow optimization.
     */    
    @CachePut(cacheNames="books", key="#isbn")
    public Book updateBook(String isbn, String descriptor) {
        System.out.println("Updating Book for " + isbn + " descriptor " + descriptor);
        return new Book(isbn, descriptor);
    }
    
    // Using @CachePut and @Cacheable annotations on the same method is generally strongly discouraged because they have different behaviors.
    
    
    /*
     * You can use @Cacheable to demarcate methods that are cacheable — that is,
     * methods for which the result is stored in the cache (cache name is 'books') so that, on subsequent
     * invocations (with the same arguments), the value in the cache is returned
     * without having to actually execute the method.
     */    
    @Override
    @Cacheable("books")
    /*@Cacheable({"books", "isbns"}) // multiple caches can be specified.
    In this case, each of the caches is checked before executing the method
    if at least one cache is hit, the associated value is returned.*/
    public Book getByIsbn(String isbn) {
        simulateSlowService();
        return new Book(isbn, "Some book");
    }
    
    
    @Cacheable(cacheNames="books", condition="#isbn.length() == 2") 
    public Book getSmallIsbn(String isbn) {
        simulateSlowService();
        return new Book(isbn, "Small book");
    }
    
    // Unless parameter - we only want to cache paperback books
    // Using the unless attribute to block hardbacks.

/*    @Cacheable(cacheNames="book", condition="#name.length() < 32", unless="#result.hardback") 
    public Book findBook(String name) {...}
*/
    
    //Simply use a naive implementation that simulates some latency (network service, DB service, slow delay, etc)
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
    
    
    /*
     * @CacheEvict annotation allows cache eviction. This process is useful for
     * removing stale or unused data from the cache. @CacheEvict demarcates methods
     * that perform cache eviction (that is, methods that act as triggers for
     * removing data from the cache). Similarly to @Cacheable, @CacheEvict requires
     * specifying one or more caches that are affected by the action, allows a
     * custom cache and key resolution or a condition to be specified, and features
     * an extra parameter (allEntries) that indicates whether a cache-wide eviction
     * needs to be performed rather than just an entry eviction (based on the key).
     */    
    
    @CacheEvict(cacheNames="books", allEntries=true) 
    public void clearCache() {
        System.out.println("Clearing all cache entries..");
    }
    
    
    @CacheEvict(cacheNames="books", key="#isbn") 
    public void clearCacheByKey(String isbn) {
        System.out.println("Clearing " +  isbn + " cache entries..");
    }
    
    
}

/*Specifying the name of the cache to use for every cache operation of the class can be replaced by a 
single class-level definition. This is where @CacheConfig comes into play. 
The following examples uses @CacheConfig to set the name of the cache:

@CacheConfig is a class-level annotation that allows sharing the cache names, the custom KeyGenerator, the custom 
CacheManager, and the custom CacheResolver. 
Placing this annotation on the class does not turn on any caching operation.	
	
@CacheConfig("books") 
public class BookRepositoryImpl implements BookRepository {

    @Cacheable
    public Book findBook(ISBN isbn) {...}
}*/


/*Sometimes, multiple annotations of the same type (such as @CacheEvict or @CachePut) need to be specified 
for example, because the condition or the key expression is different between different caches. @Caching lets 
multiple nested @Cacheable, @CachePut, and @CacheEvict annotations be used on the same method. The following 
example uses two @CacheEvict annotations:

@Caching(evict = { @CacheEvict("primary"), @CacheEvict(cacheNames="secondary", key="#p0") })
public Book importBooks(String deposit, Date date)
*/
