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

    public static void execute(String line) {}

    public static void main(String args[]) {
        String l[] = {"push  %rbp", "mov %rsp, %rdi", "pop   %rbp"};

        for (String arg : l) {
            execute(arg);
        }

    }
}

