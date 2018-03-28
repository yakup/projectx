package com.yakup.client.controller;

import com.yakup.client.AssignmentErrors;
import com.yakup.client.MongoNumber;
import com.yakup.client.NumberRepository;
import com.yakup.client.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/number")
@EnableMongoRepositories
public class DocumentController {
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private NumberRepository numberRepository;
    @Autowired
    private CustomRepository customRepository;

    @RequestMapping(path = "/{number}", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity<?> save(@PathVariable("number") Long number) {
        MongoNumber existingNumber = numberRepository.findByNumber(number);
        if (existingNumber != null) {
            return ResponseEntity.badRequest().body(AssignmentErrors.NUMBER_ALREADY_EXISTS.toString());
        }

        try {
            MongoNumber mongoNumber = new MongoNumber();
            mongoNumber.setNumber(number);
            mongoNumber.setInsertDate(DATE_FORMAT.format(new Date()));
            numberRepository.insert(mongoNumber);

            return ResponseEntity.ok(mongoNumber);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AssignmentErrors.UNEXPECTED_EXCEPTION.toString());
        }

        /*
        return this.accountRepository
                .findByUsername(userId)
                .map(account -> {
                    Bookmark result = bookmarkRepository.save(new Bookmark(account,
                            input.getUri(), input.getDescription()));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();

                    return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());
        */
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<?> findAll(@RequestParam(value = "sorting", required = false) String sorting) {
        List<MongoNumber> allNumbers = customRepository.findAllByOrder(sorting);
        if (allNumbers.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AssignmentErrors.NO_NUMBER_EXISTS.toString());
        }

        return ResponseEntity.ok(allNumbers);
    }

    @RequestMapping(path = "/{number}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> find(@PathVariable("number") Long number) {
        MongoNumber mongoNumber = numberRepository.findByNumber(number);
        if (mongoNumber != null) {
            return ResponseEntity.ok(number);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AssignmentErrors.NUMBER_NOT_FOUND.toString());
    }

    //6. Write a resource that can get the persisted object as JSON having the maximum number in db.

    @RequestMapping(path = "/max", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> max() {
        MongoNumber maximumNumber = customRepository.getMaximumNumber();
        if (maximumNumber != null) {
            return ResponseEntity.ok(maximumNumber.getNumber());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AssignmentErrors.MAXIMUM_NUMBER_COULD_NOT_FOUND.toString());
    }

    @RequestMapping(path = "/min", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> min() {

        MongoNumber minimumNumber = customRepository.getMinimumNumber();
        if (minimumNumber != null) {
            return ResponseEntity.ok(minimumNumber.getNumber());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AssignmentErrors.MAXIMUM_NUMBER_COULD_NOT_FOUND.toString());
    }


    @RequestMapping(path = "/delete/{number}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("number") Long number) {
        MongoNumber mongoNumber = numberRepository.findByNumber(number);
        if (mongoNumber == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AssignmentErrors.NUMBER_NOT_FOUND.toString());
        }

        numberRepository.delete(mongoNumber);
        return ResponseEntity.ok().build();
    }

}
