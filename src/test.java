package src;

public class test {
    public int a;

    public test() {
        a = 1;
    }

    public void print() {
        System.out.printf("test has val %d\n", a);
    }

    public static void main(String args[]) {
        test t = new test();
        t.print();
    }
}

