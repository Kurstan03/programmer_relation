package org.example;

import org.example.config.HibernateConfig;
import org.example.entity.Address;
import org.example.entity.Country;
import org.example.myExceptions.NotFoundException;
import org.example.services.AddressService;
import org.example.services.CountryService;
import org.example.services.impl.AddressServiceImpl;
import org.example.services.impl.CountryServiceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        CountryService countryService = new CountryServiceImpl();
        AddressService addressService = new AddressServiceImpl();
        commandsCountry(countryService);
        commandsAddress(addressService);
    }
    public static void commandsCountry(CountryService countryService){
        while (true) {
            System.out.println("""
                    1.Save country
                    2.Save more country
                    3.view all countries
                    4.find country by id
                    5.delete country by id
                    6.delete all countries
                    7.update country
                    8.view country with the longest description
                    9.count countries""");
            String num = new Scanner(System.in).next();
            switch (num) {
                case "1" -> {
                    Country country = new Country("KGZ", "my country");
                    System.out.println(countryService.saveCountry(country));
                }
                case "2" -> {
                    List<Country> countries = new LinkedList<>();
                    countries.add(new Country("USA", "United States of America"));
                    countries.add(new Country("Canada", "country of hockey"));
                    System.out.println(countryService.saveCollectionCountry(countries));
                }
                case "3" -> countryService.getAllCountry().forEach(System.out::println);
                case "4" -> {
                    try {
                        System.out.print("Country id: ");
                        Long id = new Scanner(System.in).nextLong();
                        System.out.println(countryService.findCountryById(id));
                    } catch (NotFoundException e){
                        System.err.println(e.getMessage());
                    }
                }
                case "5" -> {
                    System.out.print("Country id: ");
                    Long id = new Scanner(System.in).nextLong();
                    System.out.println(countryService.deleteCountryById(id));
                }
                case "6" -> System.out.println(countryService.deleteAllCountry());
                case "7" -> {
                    System.out.print("Old country id: ");
                    Long id = new Scanner(System.in).nextLong();
                    System.out.print("Country: ");
                    String name = new Scanner(System.in).nextLine();
                    System.out.print("description: ");
                    String description = new Scanner(System.in).nextLine();
                    Country country = new Country(name, description);
                    System.out.println(countryService.updateCountry(id, country));
                }
                case "8" -> System.out.println(countryService.findCountryWithTheLongestDescription());
                case "9" -> {
                    System.out.print("Country: ");
                    String country = new Scanner(System.in).nextLine();
                    System.out.println(countryService.countCountry(country));
                }
            }
        }
    }
    public static void commandsAddress(AddressService addressService){
        while (true) {
            System.out.println("""
                    1.save address
                    2.save more address
                    3.view all address
                    4.find by id
                    5.delete by id
                    6.delete all address
                    7.update address
                    """);
            String num = new Scanner(System.in).nextLine();
            switch (num) {
                case "1" -> {
                    Address address = new Address("Osh", "Kurmanjan datka", 23);
                    System.out.println(addressService.saveAddress(address));
                }
                case "2" -> {
                    List<Address> addresses = new LinkedList<>();
                    addresses.add(new Address("Batken", "Kiev", 12));
                    addresses.add(new Address("Talas", "Shakirova", 1));
                    System.out.println(addressService.saveMoreAddress(addresses));
                }
                case "3" -> addressService.getAllAddresses().forEach(System.out::println);
                case "4" -> {
                    try {
                        System.out.print("Address id: ");
                        Long id = new Scanner(System.in).nextLong();
                        System.out.println(addressService.findAddressById(id));
                    } catch (NotFoundException e){
                        System.err.println(e.getMessage());
                    }
                }
                case "5" -> {
                    System.out.print("Address id: ");
                    Long id = new Scanner(System.in).nextLong();
                    System.out.println(addressService.deleteAddressById(id));
                }
                case "6" -> System.out.println(addressService.deleteAllAddress());
                case "7" -> {
                    System.out.print("Old address id: ");
                    Long id = new Scanner(System.in).nextLong();
                    System.out.print("Region name: ");
                    String regionName = new Scanner(System.in).nextLine();
                    System.out.print("street: ");
                    String street = new Scanner(System.in).nextLine();
                    System.out.print("home number: ");
                    Integer homeNumber = new Scanner(System.in).nextInt();
                    Address address = new Address(regionName, street, homeNumber);

                    System.out.println(addressService.updateAddress(id, address));
                }
            }
        }
    }
}
