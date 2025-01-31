package io.github.marbys.microservices.order.services;

import com.mongodb.DuplicateKeyException;
import io.github.marbys.microservices.order.persistence.DatabaseSequence;
import io.github.marbys.util.exceptions.InvalidInputException;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    private final ReactiveMongoTemplate mongoOperations;
    private DatabaseSequence sequence;

    public SequenceGeneratorService(ReactiveMongoTemplate mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {
        mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class).subscribe(databaseSequence -> {sequence= databaseSequence;});
        return !Objects.isNull(sequence) ? sequence.getSeq() : 1;
    }

}
