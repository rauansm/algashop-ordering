package com.algashop.ordering.infrastructure.utillity.modelmapper;

import com.algashop.ordering.application.customer.query.CustomerOutput;
import com.algashop.ordering.application.utility.Mapper;
import com.algashop.ordering.domain.commons.FullName;
import com.algashop.ordering.domain.customer.BirthDate;
import com.algashop.ordering.domain.customer.Customer;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class ModelMapperConfig {

    private static final Converter<FullName, String> fullnameToFirstNameConverter =
            mappingContext -> {
                if (mappingContext.getSource() == null) {
                    return null;
                }
                return mappingContext.getSource().firstName();
            };

    private static final Converter<FullName, String> fullnameToLastNameConverter =
            mappingContext -> {
                if (mappingContext.getSource() == null) {
                    return null;
                }
                return mappingContext.getSource().lastName();
            };

    private static final Converter<BirthDate, LocalDate> birthDateToLocalDateConverter =
            mappingContext -> {
                if (mappingContext.getSource() == null) {
                    return null;
                }
                return mappingContext.getSource().value();
            };

    @Bean
    public Mapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        configuration(modelMapper);
        return modelMapper::map;
    }

    private void configuration(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(Customer.class, CustomerOutput.class)
                .addMappings(mapping ->
                        mapping.using(fullnameToFirstNameConverter)
                                .map(Customer::fullName, CustomerOutput::setFirstName))
                .addMappings(mapping ->
                        mapping.using(fullnameToLastNameConverter)
                                .map(Customer::fullName, CustomerOutput::setLastName))
                .addMappings(mapping ->
                        mapping.using(birthDateToLocalDateConverter)
                                .map(Customer::birthDate, CustomerOutput::setBirthDate));
    }
}
