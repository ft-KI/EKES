# EKES



## Quick Start
 - Command `git clone https://github.com/ft-KI/EKES` ausführen
 - Gehe in den Ordner webfrontend
 - Führe den Befehl npm install aus
 - Führe den Befehl npm start aus
 - Öffne den Ordner EvolutionSimulator in Intellij Idea
 - Starte WebMain.main mit der JDK 11-15
 - Navigiere zu localhost:3000 und sieh dir die Simulation an

Wir nutzen logischerweise für unser Projekt das neuronale mutierende Lernen, weil es bei einer Evolution nicht unbedingt ein richtig oder falsch geben muss. Dabei setzen wir bei der Evolutionssimulation auf einzelne Kreaturen.
Allgemeine Fakten zu einer Kreatur:
Eine Kreatur hat: 
- Ein Alter
- Eine Blickrichtung (Die sie frei rotieren kann) 
- Ein Energielevel
- 3 Fühler (s. unten)
- Ein neuronales Netz
 
Was nehmen die Kreaturen war? (Stand 10.11.2020) 
- Eingabe Werte in das Netz einer jeden Kreatur:
  - Ist die Kreatur gerade auf Land oder Wasser?
  - Wieviel Futter/Gras ist unter der Kreatur
  - Wie hoch ist das Energie-Level
  - Eingabe der Fühler: 
    - 3 unterschiedlich lange Fühler, die in die Blickrichtung der Kreatur zeigen. 
    - Diese Empfangen ob unter dem Fühler-Ende sich Land oder Wasser befindet 
 - Ausgabe Werte des Netzes 
    - Rotation um die eigene Achse nach rechts 
    - Rotation um die eigene Achse nach links
    - Will die Kreatur etwas essen… Wenn ja. Wie viel?
    - Und ob die Kreatur ein Nachfahre erstellen will
    
Wie lebt eine Kreatur?
- Eine Kreatur hat mit der Erstellung ein bestimmtes Energie Level
- Jede Aktion kostet Energie (Manche mehr, andere weniger) 
- Durch Fressen kann Energie zurückgewonnen werden (Selbst das eigentliche Fressen kostet Energie, dadurch wird die Kreatur angeregt, nur zu fressen, wenn Futter da ist)
- Mit steigendem Alter steigt der Energieverbrauch pro Aktion 
- Sinkt der Energievorrat unter ein gewisses Level, stirbt die Kreatur
- Wenn sich die Kreatur auf Wasser bewegt, verliert sie mehr Energie, als wenn sie sich auf Land bewegen würde 

Wie wird die Umgebung erstellt?

- Wir analysieren ein Bild, mit einer Weltkarte, dann erstellen wir je nach Farbe ein Land- Element oder ein Wasser-Element. Dadurch können wir eine abwechslungsreiche Umgebung erschaffen. Alle grünen Flächen sind Gras alles andere ist Wasser. Wenn die Kreaturen nun das Futter fressen verfärbt sich das grüne Land gelblich. Je näher das Land am Wasser ist, desto schneller wächst das Gras nach. 

 
Hintergrundwissen
- Die Simulation ist vollständig von der Visualisierung getrennt, d.h. die Simulation läuft auch weiter, wenn die Visualisierung geschlossen wird
- Das Grundgerüst des neuronalen Netzes (www.github.com/ft-ki/KI) ist komplett unabhängig, von uns eigen entwickelt und kann und wird auch in anderen Projekten verwendet
- Die Simulation im Hintergrund ist in JAVA geschrieben
- Die Visulisierung läuft im Browser auf JavaScript Basis
- In der Simulation ist die kleinste Zeit-Einheit ein sog. Step, aus diesem Grund ist es irrelevant wie viele Steps pro Sekunde (kurz SPS) berechnet werden, das hat keinen Einfluss auf den Ablauf bzw. die Genauigkeit der Evolution. Die SPS sind nur durch die Rechenleistung des Computers limitiert

UI – User Interface

- In der Mitte kann man die aktuelle Simulation verfolgen und beobachten, dabei sind Kreaturen in der ersten Generation noch grün und verfärben sich langsam weißlich. Links sieht man Informationen, die aus der Simulation extrahiert werden. Anschließend werden diese unten in einem dynamischen Diagramm visualisiert. Rechts kann man die Simulation bzw. Darstellung beeinflussen. Bisher ist es möglich die Auflösung und Aktualisierungsrate der Diagramme zu verändern. Des Weiteren kann man die Geschwindigkeit der Simulation verändern, d.h. wie hoch die SPS ist. Geplant ist auch die Selektion einer Kreatur, um Informationen speziell über diese in Erfahrung zu bringen. 
