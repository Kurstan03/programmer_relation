package org.example.repository;

import org.example.entity.Country;

import java.util.List;
import java.util.Optional;

/**
  @author kurstan
  @created at 08.02.2023 3:27
*/public interface CountryRepository {
    void saveCountry(Country country);
    void saveCollectionCountry(List<Country> countries);
    List<Country> getAllCountry();
    Optional<Country> findCountryById(Long id);
    void deleteCountryById(Long id);
    void deleteAllCountry();
    void updateCountry(Long oldCountryId, Country newCountry);
    Country findCountryWithTheLongestDescription();
    Long countCountry(String countryName);

}
