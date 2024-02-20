package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoicesPageDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    InvoiceEntity toEntity(InvoiceDTO source);

    InvoiceDTO toDTO(InvoiceEntity source);

    List<InvoiceDTO> toDTO(List<InvoiceEntity> source);

    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "seller", ignore = true)
    void updateInvoiceEntity(InvoiceDTO source, @MappingTarget InvoiceEntity target);

    /**
     * <p>Transfers the invoices List to another DTO and adds data about the available number of invoices in the
     * database and the limit of the number of invoices per page.</p>
     *
     * @param pageEntities One sorted page of Invoices
     * @param limit        Limit of the number of invoices per page
     * @return One sorted page of invoices and information about the available number of invoices and the limit
     * of the number of invoices per page
     */
    default InvoicesPageDTO addPaginationInfoToDTO(Page<InvoiceEntity> pageEntities, int limit) {
        InvoicesPageDTO invoicesPageDTO = new InvoicesPageDTO();
        invoicesPageDTO.setInvoices(toDTO(pageEntities.getContent()));
        invoicesPageDTO.setCount(pageEntities.getTotalElements());
        invoicesPageDTO.setLimit(limit);
        return invoicesPageDTO;
    }
}
