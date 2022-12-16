package crow.teomant.springboot3test.document;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDocumentMongoRepository extends MongoRepository<TestDocument, UUID> {
}
