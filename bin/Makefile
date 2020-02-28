
CC=riscv64-unknown-linux-gnu-gcc

EXE=test

all: test


test: main.o
	$(CC) -o $(EXE) $^

main.o: main.s
	$(CC) -o $@ $^

