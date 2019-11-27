package com.example.custom.autoconfig.Testing;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository 
extends JpaRepository<MyUser, String> { }
