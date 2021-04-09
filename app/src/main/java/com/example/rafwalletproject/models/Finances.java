package com.example.rafwalletproject.models;

public class Finances {

    private int id;

    private String description;
    private boolean isIncome;
    private String title;
    private Integer quantity;

    public Finances(int id, String description, boolean isIncome, String title, Integer quantity) {
        this.id = id;
        this.description = description;
        this.isIncome = isIncome;
        this.title = title;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }
}
