package com.example.technology.infrastructure.adapters.persistenceadapter.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("technologies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyEntity {
    @Id
    private Long id;
    @Column("nombre")
    private String name;
    @Column("descripcion")
    private String description;
}
