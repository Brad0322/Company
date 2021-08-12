package uz.pdp.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.demo.entity.Address;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyDto {

    @NotNull(message = "Companiya nomi ko'rsatilishi shart")
    private String corpName;
    @NotNull(message = "Raxbar ismi kiritilishi shart")
    private String directorName;
    @NotNull(message = "Kucha nomi kiritilishi shart")
    private String street;
    @NotNull(message = "Uy raqami kiritilishi shart")
    private String homeNumber;
}
