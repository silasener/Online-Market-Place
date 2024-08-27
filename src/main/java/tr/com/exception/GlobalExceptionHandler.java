package tr.com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tr.com.common.Constants;
import tr.com.response.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(Exception e) {
        return new ResponseEntity<>(new BaseResponse(Constants.ResponseCodes.UNKNOWN_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(new BaseResponse(Constants.ResponseCodes.ACCESS_DENIED, e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponse> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(new BaseResponse(Constants.ResponseCodes.USER_NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundForFilterException.class)
    public ResponseEntity<BaseResponse<String>> handleNoProductFoundException(ProductNotFoundForFilterException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(ProductAlreadyExistingException.class)
    public ResponseEntity<BaseResponse<String>> handleProductAlreadyExistingException(ProductAlreadyExistingException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse<>(ex.getMessage()));
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(SellerAlreadyAssociatedWithProductException.class)
    public ResponseEntity<BaseResponse<String>> handleSellerAlreadyLinkedException(SellerAlreadyAssociatedWithProductException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(SellerVendorCodeAlreadyInUseException.class)
    public ResponseEntity<BaseResponse<String>> handleSellerVendorCodeAlreadyInUseException(SellerVendorCodeAlreadyInUseException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<BaseResponse<String>> handleUsernameAlreadyInUseException(UsernameAlreadyInUseException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse<>(ex.getMessage()));
    }

}
