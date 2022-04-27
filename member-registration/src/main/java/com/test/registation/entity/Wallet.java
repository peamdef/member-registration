package com.test.registation.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Wallet {

    @Id
    private String referenceCode;

    @NotBlank
    private Integer amount;
    @NotBlank
    private Integer point;
    public Wallet() {
    }

    public Wallet(String referenceCode, Integer amount, Integer point) {
        this.referenceCode = referenceCode;
        this.amount = amount;
        this.point = point;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer salary) {
        this.amount = salary;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
