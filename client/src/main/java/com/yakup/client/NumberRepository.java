package com.yakup.client;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NumberRepository extends MongoRepository<MongoNumber, String> {
    MongoNumber findByNumber(Long number);
}
