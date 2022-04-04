package com.quintrix.java.training.iwterry;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

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

        Double doubleWrapper = Double.valueOf("3.14");
        double doublePrimitive = 3.14;

        if (doublePrimitive == doubleWrapper) {
            System.out.println("this is an example of concept autoboxing/unboxing");
        }

        // ----------- writing to and reading from a file -----------------
        System.out.println();
        Path path = Paths.get("hello-world.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE_NEW
        )) {
            System.out.println("The file " + path.toAbsolutePath() + " was created.");
            System.out.println("-------Attempting to write to file-------");
            writer.write("Hello World!\n");
            writer.write("This is another line.");
            System.out.println("-------Finished writing to file.-----");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try(BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String textReadFromFile;

            System.out.println("-------Reading from file.-------");
            while (true) {
                textReadFromFile = reader.readLine();

                if (textReadFromFile == null) break;

                System.out.println(textReadFromFile);
            }
            System.out.println("------Finished reading from file.------");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Files.delete(path);
                System.out.println("The file " + path.toAbsolutePath() + " was deleted.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
