package com.packtpub.reactive;

import java.util.HashMap;
import java.util.Map;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserRepositorySample implements UserRepository {

	// initiate Users
	private final Map<Integer, User> users = new HashMap<>();

	// fill dummy values for testing
	public UserRepositorySample() {
		this.users.put(1, new User(1, "David"));
		this.users.put(2, new User(2, "John"));
		this.users.put(3, new User(3, "Kevin"));
	}
	
	//this method will return all users
	@Override
	public Flux<User> getAllUsers() {
		return Flux.fromIterable(this.users.values());
	}
	
	@Override
	public Mono<User> getUser(Integer id){
		return Mono.justOrEmpty(this.users.get(id)); // justOrEmpty - Create a new Mono that emits the specified item if non null otherwise only emits onComplete
	}
	
	@Override
	public Mono<Void> saveUser(Mono<User> userMono) {
		return userMono.doOnNext(user -> {	// doOnNext - Add behavior triggered when the Mono emits a data successfully		
			users.put(user.getUserid(), user);
			System.out.format("Saved %s with id %d%n", user, user.getUserid());
		}).thenEmpty(Mono.empty()); // thenEmpty - Return a Mono<Void> that waits for this Mono to complete then for a supplied Publisher<Void> to also complete.
	}
	
	@Override
	public Mono<Void> updateUser(Mono<User> userMono) {
		return userMono.doOnNext(user -> {			
			users.put(user.getUserid(), user);
			System.out.format("Saved %s with id %d%n", user, user.getUserid());
		}).thenEmpty(Mono.empty());
	}
	
	@Override
	public Mono<Void> deleteUser(Integer id) {
		
		users.remove(id);		
		System.out.println("user : "+users);
		
		return Mono.empty(); // empty - Create a Mono that completes without emitting any item.
	}
}