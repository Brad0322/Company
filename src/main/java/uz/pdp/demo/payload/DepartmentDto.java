package uz.pdp.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.demo.entity.Company;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentDto {
    @NotNull(message = "Department nomsiz bulmasligi shart")
    private String name;
    @NotNull(message = "Companya Id si ko'rsatilmagan")
    private Integer companyId;
}
