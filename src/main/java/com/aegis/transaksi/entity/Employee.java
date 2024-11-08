package com.aegis.transaksi.entity;

import jakarta.persistence.*;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "employee")
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number")
    private UUID registrationNumber;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "position")
    private String position;

    @Column(name = "salary")
    private BigDecimal salary;

    @CreatedDate
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Job> pekerjaan;

    public Employee(){
        this.registrationNumber = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(UUID registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<Job> getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(List<Job> pekerjaan) {
        this.pekerjaan = pekerjaan;
    }
}
