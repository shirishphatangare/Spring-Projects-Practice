package com.example.profiles;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;
import static org.assertj.core.api.Assertions.assertThat;

public class ProfilesApplicationTest2 {

	@Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void testDefaultProfile() {
    	ProfilesApplication.main(new String[0]);
        String output = this.outputCapture.toString();
        assertThat(output).contains("Today is sunny day!");
    }

    @Test
    public void testRainingProfile() {
        System.setProperty("spring.profiles.active", "rainy");
        ProfilesApplication.main(new String[0]);
        String output = this.outputCapture.toString();
        assertThat(output).contains("Today is raining day!");
    }

    @Test
    public void testRainingProfile_withDoption() {
    	ProfilesApplication.main(new String[]{"--spring.profiles.active=sunny"});
        String output = this.outputCapture.toString();
        assertThat(output).contains("Today is sunny day!");
    }

    @After
    public void after() {
        System.clearProperty("spring.profiles.active");
    }
	
}
