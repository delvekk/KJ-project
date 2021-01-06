package com.dawid.repositories;

import com.dawid.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
   User findByEmail(String email);
   User findByConfirmationToken(String confimrationToken);

}

