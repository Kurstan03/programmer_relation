package org.example.services;

import org.example.entity.Country;

import java.util.List;
import java.util.Optional;

/**
 * @author kurstan
 * @created at 08.02.2023 15:18
 */
public interface CountryService {
    String saveCountry(Country country);
    String saveCollectionCountry(List<Country> countries);
    List<Country> getAllCountry();
    Country findCountryById(Long id);
    String deleteCountryById(Long id);
    String deleteAllCountry();
    String updateCountry(Long oldCountryId, Country newCountry);
    Country findCountryWithTheLongestDescription();
    Long countCountry(String countryName);
}
