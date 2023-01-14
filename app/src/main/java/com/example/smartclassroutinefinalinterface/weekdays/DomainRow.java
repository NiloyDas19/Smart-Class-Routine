package com.example.smartclassroutinefinalinterface.weekdays;

public class DomainRow {
    public String _1,_2,_3,_4,_5,_6,_7,_8,_9;

    public DomainRow(String _1, String _2, String _3, String _4, String _5, String _6, String _7, String _8, String _9) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
        this._7 = _7;
        this._8 = _8;
        this._9 = _9;
    }
   public DomainRow()
   {

   }

    @Override
    public String toString() {
        return "DomainRow{" +
                "_1='" + _1 + '\'' +
                ", _2='" + _2 + '\'' +
                ", _3='" + _3 + '\'' +
                ", _4='" + _4 + '\'' +
                ", _5='" + _5 + '\'' +
                ", _6='" + _6 + '\'' +
                ", _7='" + _7 + '\'' +
                ", _8='" + _8 + '\'' +
                ", _9='" + _9 + '\'' +
                '}';
    }
}
