package com.cheong.agenticai.repository;

import com.cheong.agenticai.model.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<User, String> {
}
