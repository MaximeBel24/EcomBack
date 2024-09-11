package fr.doranco.ecom.services.address;

import fr.doranco.ecom.dto.AddressDto;
import fr.doranco.ecom.entities.Address;
import fr.doranco.ecom.entities.User;
import fr.doranco.ecom.exceptions.AddressException;
import fr.doranco.ecom.exceptions.UserException;
import fr.doranco.ecom.repositories.AddressRepository;
import fr.doranco.ecom.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AddressDto createAddress(AddressDto addressDto) {
        // Récupérer l'utilisateur
        User user = userRepository.findById(addressDto.getUserId())
                .orElseThrow(() -> new UserException("Utilisateur introuvable"));

        // Créer une nouvelle adresse
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setZipCode(addressDto.getZipCode());
        address.setUser(user);

        // Sauvegarder l'adresse
        Address savedAddress = addressRepository.save(address);
        return convertToDto(savedAddress);
    }

    @Override
    @Transactional
    public AddressDto updateAddress(Long id, AddressDto addressDto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressException("Adresse introuvable"));

        // Mettre à jour les champs
        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setZipCode(addressDto.getZipCode());

        // Sauvegarder les modifications
        Address updatedAddress = addressRepository.save(address);
        return convertToDto(updatedAddress);
    }

    @Override
    @Transactional
    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new AddressException("Adresse introuvable");
        }
        addressRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressDto getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressException("Adresse introuvable"));
        return convertToDto(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressDto> getAddressesByUserId(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressDto> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Méthode de conversion de Address en AddressDto
    private AddressDto convertToDto(Address address) {
        AddressDto dto = new AddressDto();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setCountry(address.getCountry());
        dto.setZipCode(address.getZipCode());
        dto.setUserId(address.getUser().getId());
        return dto;
    }
}
