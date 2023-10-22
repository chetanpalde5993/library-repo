package com.library.libraryservice.db3.repository;

import com.library.libraryservice.db3.entity.M_User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMongoRepository extends MongoRepository<M_User, Long> {
}
