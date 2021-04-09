package com.example.rafwalletproject.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rafwalletproject.models.Finances;

import java.util.ArrayList;
import java.util.List;

public class SharedFinancesViewModel extends ViewModel {

    private static int counter = 1;

    private MutableLiveData<Integer> incomeSum = new MutableLiveData<>();
    private MutableLiveData<Integer> expensesSum = new MutableLiveData<>();
    private MutableLiveData<Integer> totalSum = new MutableLiveData<>();

    private MutableLiveData<List<Finances>> incomes = new MutableLiveData<>();
    private MutableLiveData<List<Finances>> expenses = new MutableLiveData<>();
    private ArrayList<Finances> incomeList = new ArrayList<>();
    private ArrayList<Finances> expensesList = new ArrayList<>();

    public SharedFinancesViewModel(){
        int _incomeSum = 0;
        int _expensesSum = 0;
        for(int i=0; i<=5; i++) {
            Finances finance = new Finances(counter++, "Income", true, "Income " + i, 100 + i);
            incomeList.add(finance);
            _incomeSum += finance.getQuantity();
        }
        for(int i=0; i<=5; i++) {
            Finances finance = new Finances(counter++, "Expense", false, "Expense " + i, 100 + i);
            expensesList.add(finance);
            _expensesSum += finance.getQuantity();
        }
        // ovo radimo zato sto cars.setValue u pozadini prvo proverava da li je pokazivac na objekat isti i ako jeste nece uraditi notifyAll
        // kreiranjem nove liste dobijamo novi pokazivac svaki put
        ArrayList<Finances> listToSubmit = new ArrayList<>(incomeList);
        incomes.setValue(listToSubmit);
        incomeSum.setValue(_incomeSum);
        listToSubmit = new ArrayList<>(expensesList);
        expenses.setValue(listToSubmit);
        expensesSum.setValue(_expensesSum);
        totalSum.setValue(_incomeSum - _expensesSum);
    }

    public LiveData<List<Finances>> getIncomes() {
        return incomes;
    }

    public LiveData<List<Finances>> getExpenses() {
        return expenses;
    }

    public LiveData<Integer> getTotalSum() {
        return totalSum;
    }

    public LiveData<Integer> getExpensesSum() {
        return expensesSum;
    }

    public LiveData<Integer> getIncomeSum() {
        return incomeSum;
    }

    public void addFinance(String title, String description, Integer quantity, boolean isIncome) {
        Finances finance = new Finances(counter++, description, isIncome, title, quantity);
        int _incomeSum = incomeSum.getValue();
        int _expensesSum = expensesSum.getValue();
        if(isIncome) {
            _incomeSum += quantity;
            incomeSum.setValue(_incomeSum);
            incomeList.add(finance);
            ArrayList<Finances> listToSubmit = new ArrayList<>(incomeList);
            incomes.setValue(listToSubmit);
        }else{
            _expensesSum += quantity;
            expensesSum.setValue(_expensesSum);
            expensesList.add(finance);
            ArrayList<Finances> listToSubmit = new ArrayList<>(expensesList);
            expenses.setValue(listToSubmit);
        }
        totalSum.setValue(_incomeSum - _expensesSum);
    }

    public void removeFinance(Finances finance){
        int _incomeSum = incomeSum.getValue();
        int _expensesSum = expensesSum.getValue();
        if(finance.isIncome()) {
            _incomeSum -= finance.getQuantity();
            incomeSum.setValue(_incomeSum);
            incomeList.remove(finance);
            ArrayList<Finances> listToSubmit = new ArrayList<>(incomeList);
            incomes.setValue(listToSubmit);
        }else{
            _expensesSum -= finance.getQuantity();
            expensesSum.setValue(_expensesSum);
            expensesList.remove(finance);
            ArrayList<Finances> listToSubmit = new ArrayList<>(expensesList);
            expenses.setValue(listToSubmit);
        }
        totalSum.setValue(_incomeSum - _expensesSum);
    }

}
