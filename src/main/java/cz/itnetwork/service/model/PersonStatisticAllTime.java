package cz.itnetwork.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonStatisticAllTime {
    private Long personId;

    private String personName;

    private BigDecimal revenue;

    private Long count;
}
