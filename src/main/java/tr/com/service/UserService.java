package tr.com.obss.jip.finalproject.service;

import tr.com.obss.jip.finalproject.dto.ProductDto;
import tr.com.obss.jip.finalproject.dto.SellerDto;
import tr.com.obss.jip.finalproject.dto.UserDto;
import tr.com.obss.jip.finalproject.model.User;
import tr.com.obss.jip.finalproject.request.ProductFilterRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto findUserById(String userId);

    Optional<User> findUserByUsername(String username);

    void checkAndCreateAdminUser();

    List<UserDto> generateSampleUsers(int userSize);

    UserDto createNewUser(UserDto userDto);

    SellerDto createNewSeller(SellerDto sellerDto);

    String generateUsername(UserDto userDto);

    void blockSellerForUser(String userId, String sellerId);

    void unblockSeller(String userId,String sellerId);

    List<UserDto> findAllUsers();

    UserDto deleteUserById(String userId);

    UserDto updateExistingUser(String userId, UserDto updateUserDto);

    List<SellerDto> findAllSellers();

    SellerDto deleteSellerById(String sellerId);

    void addProductToFavoriteList(String productId, String userId);

    void removeProductFromFavoriteList(String productId, String userId);

    List<SellerDto> findBlockedSellersByUserId(String userId);

    void removeProductFromSeller(String sellerId, String productId);

    SellerDto addProductToSeller(String productId, String sellerId);

    List<ProductDto> findFavoriteProductsByUser(String userId);

    List<UserDto> findUsersByIds(List<String> userIds);

    List<SellerDto> findSellersByIds(List<String> userIds);

    List<ProductDto> filterFavoriteProducts(ProductFilterRequest productFilterRequest);

}
