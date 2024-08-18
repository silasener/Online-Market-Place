package tr.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tr.com.enums.Brand;
import tr.com.enums.Category;
import tr.com.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findProductById(UUID id);

    @Query("SELECT p FROM Product p WHERE p.name = :name AND p.brand = :brand AND p.category = :category")
    Optional<Product> findProductByNameAndBrandAndCategory(@Param("name") String name,
                                                           @Param("brand") Brand brand,
                                                           @Param("category") Category category);

    @Query("SELECT p FROM Product p WHERE p.name = :name AND p.brand = :brand AND p.category = :category AND p.id <> :id")  // another record check
    Optional<Product> findProductByNameAndBrandAndCategoryAndIdNot(@Param("name") String name, @Param("brand") Brand brand,
                                                                   @Param("category") Category category, @Param("id") UUID id);

    @Query("SELECT p FROM Product p JOIN p.sellers s WHERE s.id NOT IN (:blockedSellerIds)")
    Page<Product> findProductsBySellerIdsNotIn(@Param("blockedSellerIds") List<UUID> blockedSellerIds, Pageable pageable);


    @Query("SELECT p FROM Product p JOIN p.sellers s WHERE s.id NOT IN (:blockedSellerIds)")
    List<Product> findProductsBySellerIdsNotIn(@Param("blockedSellerIds") List<UUID> blockedSellerIds);

    Page<Product> findAll(Pageable pageable);



}
