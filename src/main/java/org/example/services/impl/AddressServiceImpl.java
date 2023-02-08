package org.example.services.impl;

import org.example.entity.Address;
import org.example.myExceptions.NotFoundException;
import org.example.repository.AddressRepository;
import org.example.repository.impl.AddressRepositoryImpl;
import org.example.services.AddressService;

import java.util.List;

/**
 * @author kurstan
 * @created at 08.02.2023 16:59
 */
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository = new AddressRepositoryImpl();
    @Override
    public String saveAddress(Address address) {
        addressRepository.saveAddress(address);
        return "Address " + address + " is saved!";
    }

    @Override
    public String saveMoreAddress(List<Address> addresses) {
        addressRepository.saveMoreAddress(addresses);
        return "Addresses saved!";
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.getAllAddresses();
    }

    @Override
    public Address findAddressById(Long id) {
        return addressRepository
                .findAddressById(id)
                .orElseThrow(()-> new NotFoundException("AddressBy id " + id + " is not found!"));
    }

    @Override
    public String deleteAddressById(Long id) {
        addressRepository.deleteAddressById(id);
        return "Address by id " + id + " is deleted!";
    }

    @Override
    public String deleteAllAddress() {
        addressRepository.deleteAllAddress();
        return "All addresses deleted!";
    }

    @Override
    public String updateAddress(Long oldAddressId, Address newAddress) {
        addressRepository.updateAddress(oldAddressId, newAddress);
        return "Address " + newAddress + " is updated to id " + oldAddressId;
    }
}
