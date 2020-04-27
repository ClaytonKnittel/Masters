
CC=riscv64-unknown-elf-gcc
OBJD=riscv64-unknown-elf-objdump

EXE=test

CFLAGS=-march=rv64imafd

all: test test.s dump test2 test2.s

test.s: test
	$(OBJD) -d $^ > $@

test: main.s
	$(CC) $(CFLAGS) -c $^ -o $(EXE)

dump: dump.c
	gcc $^ -o $@

test2.s: test2
	$(OBJD) -d $^ > $@

test2: main.c
	$(CC) $(CFLAGS) -c $^ -o $@


.PHONY: clean
clean:
	rm test test.s test2 test2.s

