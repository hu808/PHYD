package com.example.app01;

public class History1 {

    private String 開始時間;
    private String 結束時間;
    private int 本次危險次數;

    public History1() {
    }

    public History1(String 開始時間, String 結束時間, int 本次危險次數) {
        this.開始時間 = 開始時間;
        this.結束時間 = 結束時間;
        this.本次危險次數 = 本次危險次數;
    }

    public String get開始時間() {
        return 開始時間;
    }
    public void set開始時間(String 開始時間) {
        this.開始時間 = 開始時間;
    }

    public String get結束時間() {
        return 結束時間;
    }
    public void set結束時間(String 結束時間) {
        this.結束時間 = 結束時間;
    }

    public int get本次危險次數() {
        return 本次危險次數;
    }
    public void set本次危險次數(int 本次危險次數) {
        this.本次危險次數 = 本次危險次數;
    }
}
