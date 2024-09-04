package fr.doranco.ecom.services.admin.coupon;

import fr.doranco.ecom.entities.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AdminCouponService{

    Coupon createCoupon(Coupon coupon);

    List<Coupon> getAllCoupons();

    boolean deleteCoupon(Long id);
}
