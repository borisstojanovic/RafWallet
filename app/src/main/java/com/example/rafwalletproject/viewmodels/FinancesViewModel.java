package com.example.rafwalletproject.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rafwalletproject.models.Finances;

import java.io.File;

public class FinancesViewModel extends ViewModel {

    private MutableLiveData<String> title = new MutableLiveData<>();
    private MutableLiveData<String> description = new MutableLiveData<>();
    private MutableLiveData<String> path = new MutableLiveData<>();
    private MutableLiveData<Integer> quantity = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAudio = new MutableLiveData<>();

    private MutableLiveData<Finances> finances = new MutableLiveData<>();

    public FinancesViewModel(){

    }

    public LiveData<String> getDescription() {
        return description;
    }

    public LiveData<String> getPath() {
        return path;
    }

    public LiveData<Integer> getQuantity() {
        return quantity;
    }

    public LiveData<String> getTitle() {
        return title;
    }

    public LiveData<Finances> getFinances() {
        return finances;
    }

    public LiveData<Boolean> getIsAudio() {
        return isAudio;
    }

    public void initFinances(Finances finance){
        this.description.setValue(finance.getDescription());
        this.quantity.setValue(finance.getQuantity());
        this.title.setValue(finance.getTitle());
        this.isAudio.setValue(finance.isAudio());

        finances.setValue(finance);

    }

    public void setTitle(String title){
        this.title.setValue(title);
        this.finances.getValue().setTitle(title);
    }

    public void setDescription(String description){
        this.description.setValue(description);
        this.finances.getValue().setDescription(description);
    }

    public void setQuantity(Integer quantity) {
        this.quantity.setValue(quantity);
        this.finances.getValue().setQuantity(quantity);
    }

    public void setIsAudio(Boolean isAudio) {
        this.isAudio.setValue(isAudio);
        this.finances.getValue().setAudio(isAudio);
    }

    public void setPath(String path) {
        this.path.setValue(path);
        ///////////////////////////
        //////////////////////////
        //////////////////////////////
        ///////////////////////////////
        //////////////////////////////
        this.description.setValue(path);
    }
}
