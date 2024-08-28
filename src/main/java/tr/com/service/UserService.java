package tr.com.service;

import tr.com.dto.ProductDto;
import tr.com.dto.SellerDto;
import tr.com.dto.UserDto;
import tr.com.model.User;
import tr.com.request.CreateNewSellerRequest;
import tr.com.request.CreateNewUserRequest;
import tr.com.request.ProductFilterRequest;
import tr.com.request.UpdateExistingUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto findUserById(String userId);

    Optional<User> findUserByUsername(String username);

    void checkAndCreateAdminUser();

    UserDto createNewUser(CreateNewUserRequest createNewUserRequest);

    SellerDto createNewSeller(CreateNewSellerRequest createNewSellerRequest);

    String blockSellerForUser(String userId, String sellerId);

    String unblockSeller(String userId,String sellerId);

    List<UserDto> findAllUsers();

    UserDto deleteUserById(String userId);

    UserDto updateExistingUser(String userId, UpdateExistingUserRequest updateExistingUserRequest);

    List<SellerDto> findAllSellers();

    SellerDto deleteSellerById(String sellerId);

    String addProductToFavoriteList(String productId, String userId);

    String removeProductFromFavoriteList(String productId, String userId);

    List<SellerDto> findBlockedSellersByUserId(String userId);

    SellerDto removeProductFromSeller(String sellerId, String productId);

    SellerDto addProductToSeller(String productId, String sellerId);

    List<ProductDto> findFavoriteProductsByUser(String userId);

    List<UserDto> findUsersByIds(List<String> userIds);

    List<SellerDto> findSellersByIds(List<String> userIds);

    List<ProductDto> filterFavoriteProducts(ProductFilterRequest productFilterRequest);

}
