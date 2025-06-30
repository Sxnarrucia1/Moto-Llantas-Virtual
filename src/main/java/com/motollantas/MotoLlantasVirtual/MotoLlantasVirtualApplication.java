package com.motollantas.MotoLlantasVirtual;

import com.motollantas.MotoLlantasVirtual.DTO.ClientDateDTO;
import com.motollantas.MotoLlantasVirtual.domain.RepairOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MotoLlantasVirtualApplication {

    public static void main(String[] args) {
        SpringApplication.run(MotoLlantasVirtualApplication.class, args);
    }

    @Configuration
    public class modelMapperConfig {

        @Bean
        public ModelMapper modelMapper() {
            ModelMapper modelMapper = new ModelMapper();

            TypeMap<RepairOrder, ClientDateDTO> typeMap = modelMapper.createTypeMap(RepairOrder.class, ClientDateDTO.class);
            typeMap.addMapping(RepairOrder::getId, ClientDateDTO::setId);
            typeMap.addMappings(mapper -> {
                mapper.map(src -> src.getMotorcycle().getBrand(), ClientDateDTO::setBrand);
                mapper.map(src -> src.getMotorcycle().getModelName(), ClientDateDTO::setModelName);
                mapper.map(src -> src.getMotorcycle().getLicensePlate(), ClientDateDTO::setLicensePlate);
                mapper.map(src -> src.getMotorcycle().getYear(), ClientDateDTO::setYear);
            });

            return modelMapper;
        }
    }

}
