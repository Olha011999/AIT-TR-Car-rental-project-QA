package car_rent.rest_assured.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ErrorResponseDto {
    private String message;
}
