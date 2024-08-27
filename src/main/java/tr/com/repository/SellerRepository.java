package tr.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.model.Seller;

import java.util.Optional;
import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {

    Optional<Seller> findSellerByVendorCode(String vendorCode);

    Optional<Seller> findSellerById(UUID id);
}
