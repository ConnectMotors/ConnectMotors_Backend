package br.com.ConnectMotors.Entidade.Model.Car;

import lombok.Data;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Data
@Schema(description = "DTO para requisições de criação de um carro")
public class CarRequestDTO {

    @Schema(description = "Marca do carro", example = "Toyota")
    private String brand;

    @Schema(description = "Modelo do carro", example = "Corolla")
    private String model;

    @Schema(description = "Ano do carro (enviado como String no form-data)", example = "2022")
    private String year;

    @Schema(description = "Quilometragem do carro em km (enviado como String no form-data)", example = "45000")
    private String mileage;

    @Schema(description = "Preço do carro em reais (enviado como String no form-data)", example = "85000.50")
    private String price;

    @Schema(description = "Lista de imagens do carro enviadas como arquivos")
    private List<MultipartFile> images;

    public Integer getYear() {
        return year != null ? Integer.parseInt(year) : null;
    }

    public Integer getMileage() {
        return mileage != null ? Integer.parseInt(mileage) : null;
    }

    public BigDecimal getPrice() {
        return price != null ? new BigDecimal(price) : null;
    }
}
