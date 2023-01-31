package dev.roder.MoviesAPI.mappers;

import dev.roder.MoviesAPI.entities.Franchise;
import dev.roder.MoviesAPI.entities.dto.franchise.FranchiseDTO;
import dev.roder.MoviesAPI.entities.dto.franchise.FranchisePostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

    FranchiseDTO franchiseToFranchiseDTO(Franchise franchise);
    Franchise franchisePostDTOToFranchise(FranchisePostDTO franchisePostDTO);
    Franchise franchiseDTOToFranchise(FranchiseDTO franchiseDTO);
    Collection<FranchiseDTO> franchiseToFranchiseDTO(Collection<Franchise> franchise);
}
