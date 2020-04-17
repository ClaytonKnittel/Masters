
#include <stdio.h>

int main(int argc, char *argv[]) {
    int buf[1];

    if (argc == 1) {
        printf("Usage: %s <binary file>\n", argv[0]);
        return -1;
    }
    char *file = argv[1];

    FILE *f = fopen(file, "r");
    if (!f) {
        printf("Unable to open %s\n", file);
        return -1;
    }

    while (fread(buf, sizeof(buf), 1, f)) {
        printf("0x%08x\t", buf[0]);
    }
    printf("\n");

    fclose(f);
    return 0;
}

