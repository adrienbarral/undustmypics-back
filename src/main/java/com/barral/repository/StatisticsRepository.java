package com.barral.repository;

import com.barral.undustmypics.FireBaseConfigureBean;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StatisticsRepository {
    @Autowired
    FireBaseConfigureBean fireBaseConfigureBean;

    public void incrementUsageForToday() {
        addOneUsageForDate(LocalDate.now());
    }

    public void addOneUsageForDate(LocalDate date){
        String dateStr = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        DatabaseReference ref = fireBaseConfigureBean.getDatabaseReference().child("usages/"+dateStr);
        ref.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                Integer currentValue = currentData.getValue(Integer.class);
                if(currentValue==null){
                    currentData.setValue(1);
                }else{
                    currentData.setValue(currentValue+1);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(DatabaseError error, boolean committed, DataSnapshot currentData) {
                if(error != null) {
                    System.out.println(error.getMessage());
                }
            }
        });
    }
}
