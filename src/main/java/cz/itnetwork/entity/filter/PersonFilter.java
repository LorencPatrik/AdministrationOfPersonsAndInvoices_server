package cz.itnetwork.entity.filter;

import lombok.Data;

@Data
public class PersonFilter {
    private String name;
    private String identificationNumber;
    private String telephone;
    private String mail;
    private String city;
    private Integer limit = 10;
    private Integer pageNumber = 0;
    private boolean hidden = false;
    private String nameOfProperty = "name";
    private boolean isAscending = true;
}
