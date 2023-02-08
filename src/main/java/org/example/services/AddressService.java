package org.example.services;

import org.example.entity.Address;

import java.util.List;
import java.util.Optional;

/**
 * @author kurstan
 * @created at 08.02.2023 16:58
 */
public interface AddressService {
    String saveAddress(Address address);
    String saveMoreAddress(List<Address> addresses);
    List<Address> getAllAddresses();
    Address findAddressById(Long id);
    String deleteAddressById(Long id);
    String  deleteAllAddress();
    String  updateAddress(Long oldAddressId, Address newAddress);
}
