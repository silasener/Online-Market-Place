package tr.com.obss.jip.finalproject.response;

import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.finalproject.common.Constants;

@Getter
@Setter
public class BaseResponse <T>{
    private Long code;
    private String message;
    private T payload;

    public BaseResponse(T payload) {
        this.payload = payload;
        this.code=Constants.ResponseCodes.SUCCESS;
    }

    public BaseResponse(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}
