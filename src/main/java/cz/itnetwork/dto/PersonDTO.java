package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.itnetwork.constant.Countries;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    @JsonProperty("_id")
    private Long id;

    @NotBlank(message = "zadejte jméno a příjmení nebo název organizace")
    @Size(min = 3, message = "zadejte alespoň 3. znaky")
    private String name;

    @NotBlank(message = "zadejte IČO")
    @Size(min = 3, message = "zadejte alespoň 3. znaky")
    private String identificationNumber;

    @NotBlank(message = "zadejte DIČ")
    @Size(min = 3, message = "zadejte alespoň 3. znaky")
    private String taxNumber;

    @NotBlank(message = "zadejte číslo bankovnního účtu")
    @Size(min = 3, message = "zadejte alespoň 3. znaky")
    private String accountNumber;

    @NotBlank(message = "zadejte kód banky")
    @Size(min = 3, message = "zadejte alespoň 3. znaky")
    private String bankCode;

    @NotBlank(message = "zadejte IBAN")
    @Size(min = 3, message = "zadejte alespoň 3. znaky")
    private String iban;

    @NotBlank(message = "zadejte telefonní číslo")
    @Size(min = 9, message = "zadejte alespoň 9. znaků")
    private String telephone;

    @NotBlank(message = "zadejte email")
    @Email(message = "zadali jste nevhodný formát emailu")
    private String mail;

    @NotBlank(message = "zadejze název ulice")
    @Size(min = 3, message = "zadejte alespoň 3. znaky")
    private String street;

    @NotBlank(message = "zadejte poštovní směrovací číslo")
    @Size(min = 5, message = "zadejte alespoň 5. znaků")
    private String zip;

    @NotBlank(message = "zadejte město vašeho trvalého bydliště")
    @Size(min = 3, message = "zadejte alespoň 3. znaky")
    private String city;

    private Countries country;

    private String note;
}
