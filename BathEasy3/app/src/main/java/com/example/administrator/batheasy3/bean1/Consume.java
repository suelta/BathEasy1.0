package com.example.administrator.batheasy3.bean1;

import java.io.Serializable;

public class Consume implements Serializable{
    private String consume_place;
    private String consume_money;
    private String consume_time;
    private String consume_result;
    public Consume(String place, String money, String time, String result)
    {
        this.consume_place=place;
        this.consume_money=money;
        this.consume_time=time;
        this.consume_result=result;
    }

    public String getConsume_place() {
        return consume_place;
    }

    public void setConsume_place(String consume_place) {
        this.consume_place = consume_place;
    }

    public String getConsume_money() {
        return consume_money;
    }

    public void setConsume_money(String consume_money) {
        this.consume_money = consume_money;
    }

    public String getConsume_time() {
        return consume_time;
    }

    public void setConsume_time(String consume_time) {
        this.consume_time = consume_time;
    }

    public String getConsume_result() {
        return consume_result;
    }

    public void setConsume_result(String consume_result) {
        this.consume_result = consume_result;
    }
}
