package org.example.services.impl;

import org.example.entity.Country;
import org.example.myExceptions.NotFoundException;
import org.example.repository.CountryRepository;
import org.example.repository.impl.CountryRepositoryImpl;
import org.example.services.CountryService;

import java.util.List;

/**
 * @author kurstan
 * @created at 08.02.2023 15:20
 */
public class CountryServiceImpl implements CountryService {
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    @Override
    public String saveCountry(Country country) {
        countryRepository.saveCountry(country);
        return "Country by name " + country.getCountry() + " is saved!";
    }

    @Override
    public String saveCollectionCountry(List<Country> countries) {
        countryRepository.saveCollectionCountry(countries);
        return "Countries saved!";
    }

    @Override
    public List<Country> getAllCountry() {
        return countryRepository.getAllCountry();
    }

    @Override
    public Country findCountryById(Long id) {
        Country country = countryRepository
                .findCountryById(id)
                .orElseThrow(() -> new NotFoundException("Country by id " + id + " is not found!"));
        return country;
    }

    @Override
    public String deleteCountryById(Long id) {
        countryRepository.deleteCountryById(id);
        return "Country by id " + id + " is deleted!";
    }

    @Override
    public String deleteAllCountry() {
        countryRepository.deleteAllCountry();
        return "All countries deleted!";
    }

    @Override
    public String updateCountry(Long oldCountryId, Country newCountry) {
        countryRepository.updateCountry(oldCountryId, newCountry);
        return "Country by name " + newCountry.getCountry() + " is updated to id " + oldCountryId;
    }

    @Override
    public Country findCountryWithTheLongestDescription() {
        return countryRepository.findCountryWithTheLongestDescription();
    }

    @Override
    public Long countCountry(String countryName) {
        return countryRepository.countCountry(countryName);
    }
}
