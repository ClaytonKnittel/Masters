package src.processor;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.ByteBuffer;
import static java.nio.ByteOrder.LITTLE_ENDIAN;

public class Memory {

    private ByteBuffer m;
    private long size;

    // takes name of file to back the memory system
    public Memory(String file_name) {
        InputStream i;
        long length;

        try {
            i = new FileInputStream(file_name);
            length = new File(file_name).length();
        } catch (FileNotFoundException e) {
            System.out.printf("File \"%s\" not found\n", file_name);
            return;
        }

        if (length % 4 != 0) {
            System.out.printf("File \"%s\" has size which is not a multiple " +
                    "of 4 (%l)\n", file_name, length);
            return;
        }

        size = length;

        byte data[] = new byte[(int) length];

        try {
            // read the entire file into m
            i.read(data);
        } catch (IOException e) {
        }

        m = ByteBuffer.wrap(data);
        m.order(LITTLE_ENDIAN);
    }

    public long size() {
        return size;
    }

    public int loadB(int addr) {
        return m.get(addr) & 0xff;
    }

    public int loadH(int addr) {
        return m.getShort(addr);
    }

    public int loadW(int addr) {
        return m.getInt(addr);
    }

    public void storeB(int addr, byte val) {
        m.put(addr, val);
    }

    public void storeH(int addr, short val) {
        m.putShort(addr, val);
    }

    public void storeW(int addr, int val) {
        m.putInt(addr, val);
    }
}


