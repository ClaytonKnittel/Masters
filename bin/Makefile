
CC=riscv64-unknown-linux-gnu-gcc
OBJD=riscv64-unknown-linux-gnu-objdump

EXE=test

CFLAGS=-march=rv64im

all: test test.s dump

test.s: test
	$(OBJD) -d $^ > $@

test: main.s
	$(CC) $(CFLAGS) -c $^ -o $(EXE)

dump: dump.c
	gcc $^ -o $@


.PHONY: clean
clean:
	rm test test.s

