package com.packtpub.reactive;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Create a WebFlux Handler
public class UserHandler {

	private final UserRepository userRepository;
	
	public UserHandler(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
public Mono<ServerResponse> getAllUsers(ServerRequest request) {
	Flux<User> users = this.userRepository.getAllUsers();
	return ServerResponse.ok().contentType(APPLICATION_JSON).body(users, User.class);
}
	
public Mono<ServerResponse> getUser(ServerRequest request) {
	int userId = Integer.valueOf(request.pathVariable("id"));
	System.out.println("trap "+userId);
	Mono<ServerResponse> notFound = ServerResponse.notFound().build();
	Mono<User> userMono = this.userRepository.getUser(userId);
	return userMono
			.flatMap(user -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(user)))
			.switchIfEmpty(notFound); // switchIfEmpty - Fallback to an alternative Mono if this mono is completed without data
}

public Mono<ServerResponse> createUser(ServerRequest request) {
	Mono<User> user = request.bodyToMono(User.class);
	return ServerResponse.ok().build(this.userRepository.saveUser(user)); // build operation acts as subscriber to saveUser publisher operation
}

public Mono<ServerResponse> updateUser(ServerRequest request) {
	Mono<User> user = request.bodyToMono(User.class);
	return ServerResponse.ok().build(this.userRepository.updateUser(user));
}

public Mono<ServerResponse> deleteUser(ServerRequest request) {		
	int userId = Integer.valueOf(request.pathVariable("id"));
	return ServerResponse.ok().build(this.userRepository.deleteUser(userId));
}
}