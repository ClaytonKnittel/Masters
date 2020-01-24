
MAIN=test
SDIR=src
CLASSDIR=.class

SRC=$(shell find $(SDIR) -type f -name '*.java')
CLASS=$(patsubst %.java,$(CLASSDIR)/%.class,$(SRC))

CC=javac

$(shell mkdir -p $(CLASSDIR))

SRC_PWD = $(shell pwd)


.PHONY: all
all: $(CLASS)

$(CLASSDIR)/$(SDIR)/%.class: $(SDIR)/%.java
	$(CC) -d $(CLASSDIR) $<

.PHONY: clean
clean:
	find $(CLASSDIR) -type f -name '*.class' -delete

.PHONY: run
run:
	@java -cp $(CLASSDIR) $(SDIR)/$(MAIN)
