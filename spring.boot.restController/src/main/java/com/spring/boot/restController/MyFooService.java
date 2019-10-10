package com.spring.boot.restController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spring.boot.restController.pojo.Foo;

@Service
public class MyFooService {
	private final Map<Long, Foo> myfoos;

    public MyFooService() {
        super();
        myfoos = new HashMap<Long, Foo>();
        myfoos.put(1L, new Foo(1L, "sample foo1"));
        myfoos.put(2L, new Foo(2L, "sample foo2"));
        myfoos.put(3L, new Foo(3L, "sample foo3"));
        myfoos.put(4L, new Foo(4L, "sample foo4"));
        myfoos.put(5L, new Foo(5L, "sample foo5"));
    }
    
    
    public Collection<Foo> findAllFoos(){
    	return myfoos.values();
    }
    
    
    public Foo findFooById(final long id) {
    	return myfoos.get(id);
    }
    
    
    public Foo updateFoo(final long id, final Foo foo) {
    	myfoos.put(id, foo);
        return foo;
    }
    
    
    public void patchFoo(final long id, final Foo foo) {
    	myfoos.put(id, foo);
    }
    
    public void createFoo(final Foo foo) {
        myfoos.put(foo.getFooId(), foo);
    } 
    
    
    public void deleteById(final long id) {
        myfoos.remove(id);
    }
    
    
}
