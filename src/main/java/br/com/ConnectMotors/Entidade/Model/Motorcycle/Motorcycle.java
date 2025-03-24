package br.com.ConnectMotors.Entidade.Model.Motorcycle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

    @Entity
    @Table(name = "motorcycles")
    @Data
    @Schema(description = "Representa uma moto cadastrada no sistema")
    public class Motorcycle {
        
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "ID único da moto", example = "1")
        private Long id;
    
        @Column(nullable = false)
        @Schema(description = "Marca da moto", example = "Honda")
        private String brand;
    
        @Column(nullable = false)
        @Schema(description = "Modelo da moto", example = "CBR 1000RR")
        private String model;
    
        @Column(nullable = false)
        @Schema(description = "Ano da moto", example = "2022")
        private Integer year;
    
        @Column(nullable = false)
        @Schema(description = "Quilometragem da moto em km", example = "45000")
        private Integer mileage;
    
        @Column(nullable = false)
        @Schema(description = "Preço da moto em reais", example = "85000.50")
        private BigDecimal price;
    
        @ElementCollection
        @CollectionTable(name = "motorcycle_images", joinColumns = @JoinColumn(name = "motorcycle_id"))
        @Column(name = "image_file_name")
        @Schema(description = "Lista de nomes de arquivos das imagens da moto")
        private List<String> imageFileNames = new ArrayList<>();

    }