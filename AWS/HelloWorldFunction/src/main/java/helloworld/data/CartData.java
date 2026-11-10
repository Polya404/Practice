package helloworld.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartData {
    private double sum;
    private String couponCode;
}
