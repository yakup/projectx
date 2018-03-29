package com.yakup.client.repository;

import com.yakup.client.model.MongoNumber;

import java.util.List;

public interface CustomRepository {
    MongoNumber getMaximumNumber();
    MongoNumber getMinimumNumber();

    List<MongoNumber> findAllByOrder(String sorting);
}
