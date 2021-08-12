package uz.pdp.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto {
    @NotNull(message = "Kucha nomi kiritilishi shart")
    private String street;
    @NotNull(message = "Uy raqami kiritilishi shart")
    private String homeNumber;
}
