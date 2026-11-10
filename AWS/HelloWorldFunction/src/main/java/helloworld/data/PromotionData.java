package helloworld.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PromotionData {
    private double reducePrice;
    private boolean freeShipping;
    private String promoMsg;
}
