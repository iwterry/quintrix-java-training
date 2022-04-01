package com.quintrix.java.training.iwterry;

public class App 
{
    public static void main( String[] args )
    {
        int operand1 = Integer.parseInt("32");
        int operand2 = (int)-3.4;
        int theSum = operand1 + operand2;

        System.out.println("The sum is " + theSum);

        if (theSum > 20) {
            System.out.println("The sum is greater than 20.");
        } else {
            System.out.println("The sum is not greater than 20.");
        }
    }
}
