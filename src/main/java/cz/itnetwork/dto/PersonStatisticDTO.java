package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonStatisticDTO {
    private Long personId;

    private String personName;

    private BigDecimal revenue;

    private Long count;

    private BigDecimal lastYearRevenue;

    private Long lastYearCount;
}
