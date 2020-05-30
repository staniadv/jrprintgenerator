<h3>Program arguments</h3>
src/main/resources/dataset.csv src/main/resources/mix_boxes.jrxml destfile.jrprint

<h3>Команда для генерации jar</h3>

mvn clean compile assembly:single

<h3>Команда для запуска jar</h3>

java -jar ./target/jrprintgenerator-1.0-jar-with-dependencies.jar src/main/resources/mix_boxes.jrxml destfile.jrprint
