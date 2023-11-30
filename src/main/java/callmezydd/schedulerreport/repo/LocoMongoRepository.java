package callmezydd.schedulerreport.repo;

import callmezydd.schedulerreport.model.LocoDataMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocoMongoRepository extends MongoRepository<LocoDataMongo, String> {
}