package dev.roder.MoviesAPI.mappers;

import dev.roder.MoviesAPI.entities.Franchise;
import dev.roder.MoviesAPI.entities.dto.franchise.FranchiseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

    FranchiseDTO franchiseToFranchiseDTO(Franchise franchise);
}
