## SOFIXIT ZADANIE REKRUTACYJNE
* [Treść](#Treść)
* [ENDPOINTY](#ENDPOINTY)
* [Technologie](#Technologie)
* [Instalacja](#Instalacja)

## Treść

Trzy serwisy.
1. Serwis który zwraca pod adresem ‘/generate/json/{size}’ listę jsonow o wskazanym rozmiarze
   oraz o strukturze poniżej z losowymi wartościami.
   np.
   { _type: "Position", _id: 65483214, key: null, name: "Oksywska", fullName: "Oksywska,
   Poland", iata_airport_code: null, type: "location", country: "Poland", geo_position:
   { latitude: 51.0855422, longitude: 16.9987442 }, location_id: 756423, inEurope: true,
   countryCode: "PL", coreCountry: true, distance: null }
2. Serwis który pobiera dane z pierwszego i konwertuje go do csv. Pierwszy endpoint który
   zawsze zwraca pobrane dane w formacie ‘type, _id, name, type, latitude, longitude’. Drugi
   endpoint który zwraca pobrane dane w danej strukturze csv czyli podajemy w zapytaniu ‘ id,
   latitude, longitude’ i oczekujemy że zwróci taki wynik ‘65483214, 51.0855422, 16.9987442’.
   Trzeci endpoint który na wejściu oczekuje definicji prostych operacji matematycznych w formie
   listy np ‘latitude*longitude,sqrt(location_id)’ i w wyniku zwróci ‘3.0052538,869.7258188’
3. Serwis który wykonuje zapytania na drugim i wyświetla proste raporty dotyczące wydajności.
   Raport powinien zawierać informacje takie jak użycie procesora,pamięci w czasie dla każdego z
   poprzednich serwisów oraz czas zapytań http pomiędzy serwisami 3->2->1.
   Raport na 1k,10k,100k wygenerowanych jsonow.
   Technologie dowolne.
   Projekt na githubie.

## ENDPOINTY 

localhost:8080/generate/json/{size} - lista jsonów o wskazanym rozmiarze

localhost:8081/csv/constant/{size} - dane w formacie ‘type, _id, name, type, latitude, longitude’

localhost:8081/csv/structure/{size}?params= ...  - pobrane dane w danej strukturze csv

localhost:8081/csv/calculate/{size}?params= ... - operacje matematyczne na danych 

localhost:8082/raport/{size}K?csvColumns= ...&mathOperations= ... - wykonuje zapytania pod dwa EP wyżej(tj. structure i calculate) oraz generuje raport dla podanej ilości json 

## Technologie

* Spring boot 3.1
* Java 17+
* REST API
* AOP/OOP
* Asynchroniczność
* GIT
* Docker

## Instalacja 

```
$ git clone https://github.com/DDolot/SOFIXIT-3-serwisy-.git
```
w folderze z projektem komenda 
```
$ docker-compose up --build
```



