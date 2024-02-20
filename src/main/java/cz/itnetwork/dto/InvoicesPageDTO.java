package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoicesPageDTO {
    private List<InvoiceDTO> invoices;
    private Long count;
    private Integer limit;
}
