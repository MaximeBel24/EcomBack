package fr.doranco.ecom.services.coupon;

import fr.doranco.ecom.dto.CouponDto;
import fr.doranco.ecom.entities.Coupon;
import fr.doranco.ecom.exceptions.CouponException;
import fr.doranco.ecom.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public Optional<CouponDto> getCouponByCode(String code) {
        return couponRepository.findByCode(code).map(this::convertToDto);
    }

    @Override
    public boolean isCouponValid(String code) {
        return couponRepository.findByCode(code)
                .filter(coupon -> coupon.getExpirationDate().after(new java.util.Date()))
                .isPresent();
    }

    @Override
    public CouponDto createCoupon(CouponDto couponDto) {
        // Conversion du DTO vers l'entité
        Coupon coupon = new Coupon();
        coupon.setName(couponDto.getName());
        coupon.setCode(couponDto.getCode());
        coupon.setDiscount(couponDto.getDiscount());
        coupon.setExpirationDate(couponDto.getExpirationDate());

        // Sauvegarde du coupon dans la base de données
        Coupon savedCoupon = couponRepository.save(coupon);
        return convertToDto(savedCoupon);
    }

    @Override
    public CouponDto updateCoupon(Long id, CouponDto couponDto) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponException("Coupon non trouvé"));

        // Mise à jour des champs
        coupon.setName(couponDto.getName());
        coupon.setCode(couponDto.getCode());
        coupon.setDiscount(couponDto.getDiscount());
        coupon.setExpirationDate(couponDto.getExpirationDate());

        // Sauvegarder les modifications
        Coupon updatedCoupon = couponRepository.save(coupon);

        return convertToDto(updatedCoupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);  // Supprimer le coupon
    }

    @Override
    public List<CouponDto> getAllCoupons() {
        return couponRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Méthode de conversion
    private CouponDto convertToDto(Coupon coupon) {
        return new CouponDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getCode(),
                coupon.getDiscount(),
                coupon.getExpirationDate()
        );
    }
}
