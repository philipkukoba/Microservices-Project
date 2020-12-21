make_jar(){
	folder=$1
	cd $folder
	
	./mvnw package -DskipTests
	
	mv target/*.jar ../
	cd ..
}

make_jar bestellingen
make_jar medicijnen
make_jar orderservice
make_jar Verzendingsdienst

