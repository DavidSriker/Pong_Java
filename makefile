JFLAGS = -g
JC = javac
JVM = java

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		  Pong/PongLogic.java \
		  Pong/GameRenderer.java \

MAIN = Pong/PongLogic

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) Pong/*.class

run:
	$(JVM) $(MAIN)
