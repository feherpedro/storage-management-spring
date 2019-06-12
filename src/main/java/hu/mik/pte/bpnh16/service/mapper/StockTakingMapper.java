package hu.mik.pte.bpnh16.service.mapper;

import hu.mik.pte.bpnh16.domain.*;
import hu.mik.pte.bpnh16.service.dto.StockTakingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StockTaking} and its DTO {@link StockTakingDTO}.
 */
@Mapper(componentModel = "spring", uses = {StatusMapper.class})
public interface StockTakingMapper extends EntityMapper<StockTakingDTO, StockTaking> {

    @Mapping(source = "status.id", target = "statusId")
    StockTakingDTO toDto(StockTaking stockTaking);

    @Mapping(source = "statusId", target = "status")
    StockTaking toEntity(StockTakingDTO stockTakingDTO);

    default StockTaking fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockTaking stockTaking = new StockTaking();
        stockTaking.setId(id);
        return stockTaking;
    }
}
