package com.asia.paint.base.network.bean;

/**
 * @author by chenhong14 on 2019-11-24.
 */
public class OrderCheckout {

    public OrderCheckout(String name, String value, Color color, float price) {
        this.name = name;
        this.value = value;
        this.color = color;
        this.price = price;
    }

    public OrderCheckout(String name, String value, String color, float price) {
        this.name = name;
        this.value = value;
        this.colorStr = color;
        this.price = price;
    }


    public enum Color {
        RED, BLACK
    }

    public String name;
    public String value;
    public Color color;
    public String colorStr;
    public float price;
}
