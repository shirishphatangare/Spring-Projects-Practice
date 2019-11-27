package spring.boot.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActuatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActuatorApplication.class, args);
	}

}


/*Actuator now shares the security config with the regular App security rules. Hence, the security model is dramatically simplified.
 Therefore, to tweak Actuator security rules, we could just add an entry for /actuator/**:

@Bean
public SecurityWebFilterChain securityWebFilterChain(
  ServerHttpSecurity http) {
    return http.authorizeExchange()
      .pathMatchers("/actuator/**").permitAll()
      .anyExchange().authenticated()
      .and().build();
}

*/


/*<!--  The /info endpoint remains unchanged. As before, we can add git details using the Maven or Gradle respective dependency: -->
<dependency>
	<groupId>pl.project13.maven</groupId>
	<artifactId>git-commit-id-plugin</artifactId>
</dependency>
*/