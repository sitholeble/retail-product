package rt.service.product.retailproduct.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import rt.service.product.retailproduct.entity.ProductEntity;

import java.util.UUID;

@Repository
public interface ProductRepository extends CassandraRepository<ProductEntity, UUID> {
}
