package cz.itnetwork.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonStatisticLastYear {
    private Long personId;

    private BigDecimal lastYearRevenue;

    private Long lastYearCount;
}
