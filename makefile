# Makefile for GatorLibrary Java program

# Java compiler
JCC = javac

# Target entry for compiling and then running the java program
default: compile

# Rule for creating .class file from .java file
compile:
	$(JCC) GatorLibrary.java

# Target entry for cleaning up
clean:
	$(RM) *.class