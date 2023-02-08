package org.example.repository;

import org.example.entity.Address;

import java.util.List;
import java.util.Optional;

/**
 * @author kurstan
 * @created at 08.02.2023 3:26
 */
public interface AddressRepository {
    void saveAddress(Address address);
    void saveMoreAddress(List<Address> addresses);
    List<Address> getAllAddresses();
    Optional<Address> findAddressById(Long id);
    void deleteAddressById(Long id);
    void deleteAllAddress();
    void updateAddress(Long oldAddressId, Address newAddress);

}
