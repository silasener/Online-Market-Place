package tr.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import tr.com.model.Seller;

import java.util.Optional;
import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {

    Optional<Seller> findSellerByVenderCode(String venderCode);

    Optional<Seller> findSellerById(UUID id);

    @Modifying
    @Transactional
    @Query("delete from Seller s where s.id = :sellerId")
    void deleteSellerById(UUID sellerId);
}
