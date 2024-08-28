package tr.com.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tr.com.dto.ProductDto;
import tr.com.dto.SellerDto;
import tr.com.dto.UserDto;
import tr.com.request.CreateNewSellerRequest;
import tr.com.request.CreateNewUserRequest;
import tr.com.request.ProductFilterRequest;
import tr.com.request.UpdateExistingUserRequest;
import tr.com.response.BaseResponse;
import tr.com.service.UserService;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users") //http://localhost:8080/api/v1/users
@RestController
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public BaseResponse<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = userService.findAllUsers();
        return new BaseResponse<>(userDtoList);
    }

    @GetMapping("/{userId}")
    public BaseResponse<UserDto> getUserDetail(@PathVariable("userId") String userId) {
        UserDto userDto = userService.findUserById(userId);
        return new BaseResponse<>(userDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public BaseResponse<UserDto> createNewUser(@RequestBody @Valid CreateNewUserRequest createNewUserRequest) {
        UserDto createdUser = userService.createNewUser(createNewUserRequest);
        return new BaseResponse<>(createdUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-seller")
    public BaseResponse<SellerDto> createNewSeller(@RequestBody @Valid CreateNewSellerRequest createNewSellerRequest) {
        SellerDto createdSeller = userService.createNewSeller(createNewSellerRequest);
        return new BaseResponse<>(createdSeller);
    }


    @PostMapping("/{userId}/block-seller")
    public ResponseEntity<String> blockSellerForUser(@PathVariable("userId") String userId, @RequestParam("sellerId") String sellerId) {
        String responseMessage = userService.blockSellerForUser(userId, sellerId);
        return ResponseEntity.ok(responseMessage);
    }


    @PostMapping("/{userId}/unblock-seller")
    public ResponseEntity<String> unblockSellerForUser(@PathVariable("userId") String userId, @RequestParam("sellerId") String sellerId) {
        String responseMessage = userService.unblockSeller(userId, sellerId);
        return ResponseEntity.ok(responseMessage);
    }


    @GetMapping("/{userId}/blocked-sellers")
    public ResponseEntity<BaseResponse<List<SellerDto>>> getBlockedSellersForUser(@PathVariable("userId") String userId) {
        List<SellerDto> blockedSellersDto = userService.findBlockedSellersByUserId(userId);
        return ResponseEntity.ok(new BaseResponse<>(blockedSellersDto));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public BaseResponse<UserDto> deleteUser(@PathVariable("userId") String userId) {
        UserDto deletedUser = userService.deleteUserById(userId);
        return new BaseResponse<>(deletedUser);
    }


    @PutMapping("/{userId}")
    public BaseResponse<UserDto> updateUser(@PathVariable("userId") String userId, @RequestBody @Valid UpdateExistingUserRequest updateExistingUserRequest) {
        UserDto updatedUser = userService.updateExistingUser(userId, updateExistingUserRequest);
        return new BaseResponse<>(updatedUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/sellers")
    public BaseResponse<List<SellerDto>> getAllSellers() {
        List<SellerDto> sellerDtoList = userService.findAllSellers();
        return new BaseResponse<>(sellerDtoList);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/sellers/{sellerId}")
    public BaseResponse<SellerDto> deleteSeller(@PathVariable("sellerId") String sellerId) {
        SellerDto deletedSeller = userService.deleteSellerById(sellerId);
        return new BaseResponse<>(deletedSeller);
    }


    @PostMapping("/{userId}/favorites/{productId}")
    public ResponseEntity<BaseResponse<String>> addProductToFavoriteList(@PathVariable("userId") String userId, @PathVariable("productId") String productId) {
        String responseMessage = userService.addProductToFavoriteList(productId, userId);
        return ResponseEntity.ok(new BaseResponse<>(responseMessage));
    }


    @DeleteMapping("/{userId}/favorites/{productId}")
    public ResponseEntity<BaseResponse<String>> removeProductFromFavoriteList(@PathVariable("userId") String userId, @PathVariable("productId") String productId) {
        String responseMessage = userService.removeProductFromFavoriteList(productId, userId);
        return ResponseEntity.ok(new BaseResponse<>(responseMessage));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{sellerId}/products/{productId}")
    public ResponseEntity<BaseResponse<SellerDto>> removeProductFromSeller(@PathVariable String sellerId, @PathVariable String productId) {
        SellerDto sellerDto = userService.removeProductFromSeller(sellerId, productId);
        return ResponseEntity.ok(new BaseResponse<>(sellerDto));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/sellers/{sellerId}/products/{productId}")
    public ResponseEntity<BaseResponse<SellerDto>> addProductToSeller(@PathVariable("sellerId") String sellerId, @PathVariable("productId") String productId) {
        SellerDto sellerDto = userService.addProductToSeller(productId, sellerId);
        return ResponseEntity.ok(new BaseResponse<>(sellerDto));
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<BaseResponse<List<ProductDto>>> getFavoriteProductsByUser(@PathVariable("userId") String userId) {
        List<ProductDto> favoriteProducts = userService.findFavoriteProductsByUser(userId);
        return ResponseEntity.ok(new BaseResponse<>(favoriteProducts));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/find-by-ids")
    public ResponseEntity<BaseResponse<List<UserDto>>> findUsersByIds(@RequestBody List<String> userIds) {
        List<UserDto> users = userService.findUsersByIds(userIds);
        return ResponseEntity.ok(new BaseResponse<>(users));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/sellers/find-by-ids")
    public ResponseEntity<BaseResponse<List<SellerDto>>> findSellersByIds(@RequestBody List<String> sellerIds) {
        List<SellerDto> sellers = userService.findSellersByIds(sellerIds);
        return ResponseEntity.ok(new BaseResponse<>(sellers));
    }

    @PostMapping("/favorites-filter")
    public ResponseEntity<BaseResponse<List<ProductDto>>> filterFavoriteProducts(@RequestBody ProductFilterRequest productFilterRequest) {
        List<ProductDto> filteredFavoriteProducts = userService.filterFavoriteProducts(productFilterRequest);
        return ResponseEntity.ok(new BaseResponse<>(filteredFavoriteProducts));
    }
}
