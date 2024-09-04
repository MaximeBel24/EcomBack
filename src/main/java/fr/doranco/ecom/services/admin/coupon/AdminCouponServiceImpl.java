package fr.doranco.ecom.services.admin.coupon;

import fr.doranco.ecom.entities.Coupon;
import fr.doranco.ecom.entities.Product;
import fr.doranco.ecom.exceptions.ValidationException;
import fr.doranco.ecom.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService{

    private final CouponRepository couponRepository;

    @Override
    public Coupon createCoupon(Coupon coupon){
        if(couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon code already exists.");
        }
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }

    @Override
    public boolean deleteCoupon(Long id){

        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
            if(optionalCoupon.isPresent()){
                couponRepository.deleteById(id);
                return true;
            }
            return false;


    }

}
