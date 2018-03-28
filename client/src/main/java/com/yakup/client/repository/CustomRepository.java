package com.yakup.client.repository;

import com.yakup.client.MongoNumber;

import java.util.List;

public interface CustomRepository {
    public MongoNumber getMaximumNumber();
    public MongoNumber getMinimumNumber();

    List<MongoNumber> findAllByOrder(String sorting);
}
