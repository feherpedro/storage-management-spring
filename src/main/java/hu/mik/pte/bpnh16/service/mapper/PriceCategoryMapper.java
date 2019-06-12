package hu.mik.pte.bpnh16.service.mapper;

import hu.mik.pte.bpnh16.domain.*;
import hu.mik.pte.bpnh16.service.dto.PriceCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PriceCategory} and its DTO {@link PriceCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PriceCategoryMapper extends EntityMapper<PriceCategoryDTO, PriceCategory> {



    default PriceCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        PriceCategory priceCategory = new PriceCategory();
        priceCategory.setId(id);
        return priceCategory;
    }
}
