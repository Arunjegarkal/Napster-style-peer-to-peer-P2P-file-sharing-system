JFLAGS = -g
JC = javac
JVM= java
FILE=
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
    Client.java \
        Files.java \
        fileTransfer.java \
	Server.java \
	Server_Details.java \
        main.java 

MAIN = main

default: classes run

classes: $(CLASSES:.java=.class)

run: classes 
	$(JVM) $(MAIN)

clean:
	$(RM) *.class
