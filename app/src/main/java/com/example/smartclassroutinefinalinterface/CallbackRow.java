package com.example.smartclassroutinefinalinterface;

import com.example.smartclassroutinefinalinterface.weekdays.DomainRow;

import java.util.List;

public interface CallbackRow {
    void received(List<DomainRow> list);

}
