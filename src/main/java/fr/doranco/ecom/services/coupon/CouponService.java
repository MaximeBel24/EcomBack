package fr.doranco.ecom.services.coupon;

import fr.doranco.ecom.dto.CouponDto;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    Optional<CouponDto> getCouponByCode(String code);
    boolean isCouponValid(String code);
    CouponDto createCoupon(CouponDto couponDto);
    CouponDto updateCoupon(Long id, CouponDto couponDto);
    void deleteCoupon(Long id);
    List<CouponDto> getAllCoupons();
}
