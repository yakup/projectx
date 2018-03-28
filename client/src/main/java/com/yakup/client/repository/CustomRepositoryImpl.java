package com.yakup.client.repository;

import com.yakup.client.MongoNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomRepositoryImpl implements CustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public MongoNumber getMaximumNumber() {

        final Query query = new Query()
                .limit(1).
                        with(new Sort(Sort.Direction.DESC, "number"));

        return mongoTemplate.findOne(query, MongoNumber.class);
    }

    @Override
    public MongoNumber getMinimumNumber() {
        final Query query = new Query()
                .limit(1).
                        with(new Sort(Sort.Direction.ASC, "number"));

        return mongoTemplate.findOne(query, MongoNumber.class);
    }

    @Override
    public List<MongoNumber> findAllByOrder(String sorting) {
        Sort.Direction sortingDirection = Sort.Direction.ASC;
        if ("descending".equalsIgnoreCase(sorting)) {
            sortingDirection = Sort.Direction.DESC;
        }

        final Query query = new Query()
                .with(new Sort(sortingDirection, "number"));

        return mongoTemplate.find(query, MongoNumber.class);

    }
}
