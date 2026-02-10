package com._clientes.demo.dto;

import com._clientes.demo.entities.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ClienteDTO {

    private long id;

    @NotBlank(message = "Campo inválido, nome nao pode ser vazio")
    private String name;

    private String cpf;

    private Double income;

    @PastOrPresent(message = "Data de nascimento nao pode estar no futuro")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "pt-BR")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private Integer children;

    public ClienteDTO() {
    }

    public ClienteDTO(long id, String name, String cpf, Double income, LocalDate birthDate, Integer children) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.income = income;
        this.birthDate = birthDate;
        this.children = children;
    }

    public ClienteDTO(Cliente entity) {
        id = entity.getId();
        name = entity.getName();
        cpf = entity.getCpf();
        income = entity.getIncome();
        birthDate = entity.getBirthDate();
        children = entity.getChildren();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public Double getIncome() {
        return income;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getChildren() {
        return children;
    }
}
