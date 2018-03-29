package com.yakup.client.repository;

import com.yakup.client.model.MongoNumber;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NumberRepository extends MongoRepository<MongoNumber, String> {
    MongoNumber findByNumber(Long number);
}
