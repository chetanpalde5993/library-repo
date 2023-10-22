package com.library.libraryservice.db3.repository;

import com.library.libraryservice.db2.entity.User;
import com.library.libraryservice.db3.entity.M_User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMongoRepository extends MongoRepository<M_User, Long> {
}
