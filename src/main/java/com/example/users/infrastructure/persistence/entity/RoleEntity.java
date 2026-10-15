package com.example.users.infrastructure.persistence.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    @NotNull(message = "No puede existir un rol con nombre nulo")
    @Size(min = 2, max = 15, message = "El tamanio del nombre debe estar entre 2 y 15 caracteres")
    private String name;
    @NotNull(message = "No puede existir un rol con descripcion nula")
    @Size(min = 2, max = 200, message = "El tamanio dela descripcion debe ser entre 2 y 200 caracteres")
    private String description;
}
