package fr.doranco.ecom.controllers;

import fr.doranco.ecom.dto.AddressDto;
import fr.doranco.ecom.services.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // Créer une nouvelle adresse
    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto) {
        AddressDto createdAddress = addressService.createAddress(addressDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    // Mettre à jour une adresse existante
    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Long id, @RequestBody AddressDto addressDto) {
        AddressDto updatedAddress = addressService.updateAddress(id, addressDto);
        return ResponseEntity.ok(updatedAddress);
    }

    // Supprimer une adresse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Obtenir une adresse par son ID
    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id) {
        AddressDto address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    // Obtenir toutes les adresses d'un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressDto>> getAddressesByUserId(@PathVariable Long userId) {
        List<AddressDto> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }

    // Obtenir toutes les adresses
    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        List<AddressDto> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }
}
