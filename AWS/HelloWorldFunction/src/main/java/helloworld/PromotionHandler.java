package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import helloworld.data.CartData;
import helloworld.data.PromotionData;

public class PromotionHandler implements RequestHandler<CartData, PromotionData> {
    @Override
    public PromotionData handleRequest(CartData cartData, Context context) {
        PromotionData promotionData = new PromotionData();
        String promoMsg = "";
        if (cartData.getSum() > 1000) {
            promotionData.setReducePrice(cartData.getSum() - cartData.getSum() / 10);
            promoMsg += "10% discount applied. ";
        }
        if (cartData.getCouponCode().equalsIgnoreCase("freeShipping")) {
            promotionData.setFreeShipping(true);
            promoMsg += "Free shipping applied";
        }
        promotionData.setPromoMsg(promoMsg);
        return promotionData;
    }
}
