package com.ku.quizzical.common.util.wrapper;

import com.ku.quizzical.common.helper.number.DoubleHelper;

public final class DoubleWrapper extends Wrapper<Double> {
    // New Instance Methods
    public static DoubleWrapper newInstance() {
        return DoubleWrapper.newInstance(0.0);
    }

    public static DoubleWrapper newInstance(Double value) {
        return new DoubleWrapper(value);
    }

    // Constructor Method
    private DoubleWrapper(Double value) {
        super(value);
    }

    // Operant Return Method
    public double getAbsoluteValue() {
        return DoubleHelper.absoluteValue(this.getValue());
    }

    public double getOppositeValue() {
        return -this.getValue();
    }

    public double getNextValue() {
        return this.getValue() + 1;
    }

    public double getPreviousValue() {
        return this.getValue() - 1;
    }

    public double getValueThenIncrement() {
        double value = this.getValue();
        this.increment();
        return value;
    }

    public double incrementThenGetValue() {
        this.increment();
        return this.getValue();
    }

    public double getValueThenDecrement() {
        double value = this.getValue();
        this.decrement();
        return value;
    }

    public double decrementThenGetValue() {
        this.decrement();
        return this.getValue();
    }

    // Operant Action Methods
    public void increment() {
        this.increment(1);
    }

    public void increment(double value) {
        this.setValue(this.getValue() + value);
    }

    public void decrement() {
        this.decrement(1);
    }

    public void decrement(double value) {
        this.increment(-value);
    }

    public void multiply(double value) {
        this.setValue(this.getValue() * value);
    }

    public void divide(double value) {
        this.setValue(this.getValue() / value);
    }

    public void mod(double value) {
        this.setValue(this.getValue() % value);
    }

    // Operant Relational Methods
    public boolean isEqualTo(double value) {
        return this.getValue() == value;
    }

    public boolean isLessThan(double value) {
        return this.getValue() < value;
    }

    public boolean isGreaterThan(double value) {
        return this.getValue() > value;
    }

    public boolean isLessThanOrEqualTo(double value) {
        return this.getValue() <= value;
    }

    public boolean isGreaterThanOrEqualTo(double value) {
        return this.getValue() >= value;
    }

    public boolean isNotEqualTo(double value) {
        return this.getValue() != value;
    }
}
