package br.com.ConnectMotors.Entidade.Model.Car;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "cars")
@Data
@Schema(description = "Representa um carro cadastrado no sistema")
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do carro", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Marca do carro", example = "Toyota")
    private String brand;

    @Column(nullable = false)
    @Schema(description = "Modelo do carro", example = "Corolla")
    private String model;

    @Column(nullable = false)
    @Schema(description = "Ano do carro", example = "2022")
    private Integer year;

    @Column(nullable = false)
    @Schema(description = "Quilometragem do carro em km", example = "45000")
    private Integer mileage;

    @Column(nullable = false)
    @Schema(description = "Preço do carro em reais", example = "85000.50")
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(name = "car_images", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "image_file_name")
    @Schema(description = "Lista de nomes de arquivos das imagens do carro")
    private List<String> imageFileNames = new ArrayList<>();
}
