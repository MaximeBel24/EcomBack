package fr.doranco.ecom.controllers;

import fr.doranco.ecom.dto.CouponDto;
import fr.doranco.ecom.services.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/{code}")
    public ResponseEntity<CouponDto> getCouponByCode(@PathVariable String code) {
        CouponDto couponDto = couponService.getCouponByCode(code)
                .orElseThrow(() -> new RuntimeException("Coupon non trouvé"));
        return ResponseEntity.ok(couponDto);
    }

    @PostMapping
    public ResponseEntity<CouponDto> createCoupon(@RequestBody CouponDto couponDto) {
        CouponDto createdCoupon = couponService.createCoupon(couponDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CouponDto> updateCoupon(@PathVariable Long id, @RequestBody CouponDto couponDto) {
        CouponDto updatedCoupon = couponService.updateCoupon(id, couponDto);
        return ResponseEntity.ok(updatedCoupon);
    }

    @GetMapping
    public ResponseEntity<List<CouponDto>> getAllCoupons() {
        List<CouponDto> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}