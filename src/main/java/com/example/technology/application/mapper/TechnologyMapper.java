package com.example.technology.application.mapper;

import com.example.technology.application.dto.TechnologyResponseDTO;
import com.example.technology.domain.model.Technology;
import com.example.technology.application.dto.TechnologyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TechnologyMapper {
    @Mapping(target = "id", ignore = true)
    Technology toModel(TechnologyDTO technologyDTO);

    TechnologyDTO toDTO(Technology technology);

    TechnologyResponseDTO toResponseDTO(Technology technology);
}
