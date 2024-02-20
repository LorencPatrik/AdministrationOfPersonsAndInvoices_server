package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceStatisticDTO {
    private BigDecimal lastYearRevenue;
    private Long lastYearCount;
    private BigDecimal allTimeRevenue;
    private Long allTimeCount;
}
