package fr.doranco.ecom.services.address;

import fr.doranco.ecom.dto.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto createAddress(AddressDto addressDto);
    AddressDto updateAddress(Long id, AddressDto addressDto);
    void deleteAddress(Long id);
    AddressDto getAddressById(Long id);
    List<AddressDto> getAddressesByUserId(Long userId);
    List<AddressDto> getAllAddresses();
}
