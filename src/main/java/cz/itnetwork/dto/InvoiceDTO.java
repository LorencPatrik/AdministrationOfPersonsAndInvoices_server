package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    @JsonProperty("_id")
    private Long id;

    @NotNull(message = "zadejte číslo faktury")
    @Positive(message = "zadejte nezáporné celé číslo")
    private Long invoiceNumber;

    @NotNull(message = "vyberte prodávajícího")
    private PersonDTO seller;

    @NotNull(message = "vyberte kupujícího")
    private PersonDTO buyer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "zadejte datum vydání faktury")
    private LocalDate issued;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "zadejte datum splatnosti faktury")
    private LocalDate dueDate;

    @NotBlank(message = "zadejte název produktu")
    @Size(min = 3, message = "zadejte alespoň 3. znaky")
    private String product;

    @NotNull(message = "zadejte cenu produktu")
    private BigDecimal price;

    @NotNull(message = "zadejte daň produktu")
    @Min(value = 0, message = "zadejte kladné číslo")
    private Integer vat;

    private String note;
}
