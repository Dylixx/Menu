### VARIABLES ###
JC = javac
SRC = src/fr/iutfbleau/SAE/
BUILD = build/fr/iutfbleau/SAE/
MARIADB = res/mariadb-client.jar
JVM = java
JARDEV = -cef fr.iutfbleau.SAE.Main testeur.jar -C build fr -C res org
JARTEST = -cef fr.iutfbleau.SAE.MainDev developpeur.jar -C build fr -C res org
### REGLES ESSENTIELLES ###

${BUILD}MainDev.class : ${SRC}MainDev.java ${BUILD}VueProtocoleDev.class
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}MainDev.java

${BUILD}VueProtocoleDev.class : ${SRC}VueProtocoleDev.java ${BUILD}ControlerProtocoleDev.class
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}VueProtocoleDev.java 

${BUILD}ControlerProtocoleDev.class : ${SRC}ControlerProtocoleDev.java ${BUILD}ConnectionBDDev.class ${BUILD}ModelProtocoleBDDev.class ${BUILD}VueCamembert.class
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}ControlerProtocoleDev.java

${BUILD}ConnectionBDDev.class : ${SRC}ConnectionBDDev.java
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}ConnectionBDDev.java

${BUILD}ModelProtocoleBDDev.class : ${SRC}ModelProtocoleBDDev.java
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}ModelProtocoleBDDev.java

${BUILD}PieChartPanel.class : ${SRC}PieChartPanel.java
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}PieChartPanel.java

${BUILD}Remplissage.class : ${SRC}Remplissage.java
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}Remplissage.java

${BUILD}VueCamembert.class : ${SRC}VueCamembert.java ${BUILD}PieChartPanel.class ${BUILD}Remplissage.class 
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}VueCamembert.java

${BUILD}Main.class : ${SRC}Main.java ${BUILD}VueProtocole.class
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}Main.java

${BUILD}VueProtocole.class : ${SRC}VueProtocole.java ${BUILD}ControlerProtocole.class
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}VueProtocole.java

${BUILD}ControlerProtocole.class : ${SRC}ControlerProtocole.java ${BUILD}ArbreMouseListener.class ${BUILD}ModelProtocoleBD.class ${BUILD}ConnectionBD.class ${BUILD}VueJTree.class ${BUILD}Arbre.class
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}ControlerProtocole.java

${BUILD}ArbreMouseListener.class : ${SRC}ArbreMouseListener.java ${BUILD}OptionJTree.class ${BUILD}EnregistrementBD.class
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}ArbreMouseListener.java

${BUILD}ModelProtocoleBD.class : ${SRC}ModelProtocoleBD.java
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}ModelProtocoleBD.java

${BUILD}ConnectionBD.class : ${SRC}ConnectionBD.java
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}ConnectionBD.java

${BUILD}VueJTree.class : ${SRC}VueJTree.java
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}VueJTree.java

${BUILD}Arbre.class : ${SRC}Arbre.java ${BUILD}OptionJTree.class
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}Arbre.java

${BUILD}OptionJTree.class : ${SRC}OptionJTree.java
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}OptionJTree.java

${BUILD}EnregistrementBD.class : ${SRC}EnregistrementBD.java 
	javac -encoding UTF-8 -implicit:none -d build -classpath build:res -sourcepath src ${SRC}EnregistrementBD.java

### REGLES OPTIONNELLES ###
all: ${BUILD}Main.class ${BUILD}MainDev.class
	jar ${JARTEST}
	jar ${JARDEV}

testeur :
	java -jar testeur.jar

developpeur :
	java -jar developpeur.jar

clean :
	-rm -f *.class

mrproper : clean Main.class

### BUTS FACTICES ###
.PHONY : run clean mrproper
### FIN ###
