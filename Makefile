
MAIN=test
SDIR=src

# directory where compiled .class files will go
CLASSPATH=.class

# where downloaded extensions go
DOWNLOAD_DIR=/usr/local/Cellar

# downloaded jars
JARS := $(DOWNLOAD_DIR)/aspectj/1.9.5/libexec/aspectj/lib/aspectjrt.jar
JARS :=$(JARS):$(DOWNLOAD_DIR)/aspectj/1.9.5/libexec/aspectj/lib/aspectjweaver.jar
JARS :=$(JARS):$(DOWNLOAD_DIR)/aspectj/1.9.5/libexec/aspectj/lib/aspectjtools.jar

SRC=$(shell find $(SDIR) -type f -name '*.java')
CLASS=$(patsubst %.java,$(CLASSPATH)/%.class,$(SRC))

CC=ajc

CFLAGS=-1.5

$(shell mkdir -p $(CLASSPATH))

SRC_PWD = $(shell pwd)


.PHONY: all
all: source

source: $(SRC)
	$(CC) $(CFLAGS) -d $(CLASSPATH) -cp .:$(CLASSPATH):$(JARS) $^

.PHONY: clean
clean:
	find $(CLASSPATH) -type f -name '*.class' -delete
	find $(CLASSPATH) -type d -name '$(CLASSPATH)/*' -delete

.PHONY: run
run:
	@java -cp $(CLASSPATH):$(JARS) $(SDIR)/$(MAIN)
