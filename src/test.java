package src;

import src.dir1.*;

public class test {
    public int a;

    public test() {
        a = 1;
    }

    public void print() {
        System.out.println("test has val " + a);
    }

    public static void main(String args[]) {
        test t = new test();
        t.print();

        test2 t2 = new test2();

    }
}

