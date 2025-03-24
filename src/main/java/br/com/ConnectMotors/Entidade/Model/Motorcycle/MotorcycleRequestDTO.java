package br.com.ConnectMotors.Entidade.Model.Motorcycle;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para requisições de criação de uma moto")
public class MotorcycleRequestDTO {
    
    @Schema(description = "Marca da moto", example = "Honda")
    private String brand;

    @Schema(description = "Modelo da moto", example = "CBR 1000RR")
    private String model;

    @Schema(description = "Ano da moto", example = "2022")
    private Integer year;

    @Schema(description = "Quilometragem da moto em km", example = "45000")
    private Integer mileage;

    @Schema(description = "Preço da moto em reais", example = "85000.50")
    private BigDecimal price;

    @Schema(description = "Lista de imagens da moto enviadas como arquivos")
    private List<MultipartFile> images;

    
}
