# Ebiznes 



### Zadanie 1: Docker
✅ 3.0 obraz ubuntu z Pythonem w wersji 3.10

✅ 3.5 obraz ubuntu:24.02 z Javą w wersji 8 oraz Kotlinem

✅ 4.0 do powyższego należy dodać najnowszego Gradle’a oraz paczkę JDBC
SQLite w ramach projektu na Gradle (build.gradle)

✅ 4.5 stworzyć przykład typu HelloWorld oraz uruchomienie aplikacji
przez CMD oraz gradle

✅ 5.0 dodać konfigurację docker-compose

Link do obrazu: https://hub.docker.com/r/danielbros/zadanie1

Kod: https://github.com/DanielBros1/Ebiznes/tree/master/zadanie_1
# 

### Zadanie 2: Scala
✅ 3.0 Należy stworzyć kontroler do Produktów

✅ 3.5 Do kontrolera należy stworzyć endpointy zgodnie z CRUD - dane pobierane z listy

✅ 4.0 Należy stworzyć kontrolery do Kategorii oraz Koszyka + endpointy zgodnie z CRUD

✅ 4.5 Należy aplikację uruchomić na dockerze (stworzyć obraz) oraz dodać skrypt uruchamiający aplikację via ngrok

✅ 5.0 Należy dodać konfigurację CORS dla dwóch hostów dla metod CRUD

Kod: https://github.com/DanielBros1/Ebiznes/tree/master/zadanie_2p
#

### Zadanie 3: Kotlin
✅ 3.0 Należy stworzyć aplikację kliencką w Kotlinie we frameworku Ktor, która pozwala na przesyłanie wiadomości na platformę Discord

✅ 3.5 Aplikacja jest w stanie odbierać wiadomości użytkowników z platformy Discord skierowane do aplikacji (bota)

✅ 4.0 Zwróci listę kategorii na określone żądanie użytkownika

✅ 4.5 Zwróci listę produktów wg żądanej kategorii

✅ 5.0 Aplikacja obsłuży dodatkowo jedną z platform: Slack, Messenger, Webex

Kod: https://github.com/DanielBros1/Ebiznes/tree/master/zadanie_3
#


### Zadanie 4: GO
✅ 3.0 Należy stworzyć aplikację we frameworki echo w j. Go, która będzie miała kontroler Produktów zgodny z CRUD

✅ 3.5 Należy stworzyć model Produktów wykorzystując gorm oraz
wykorzystać model do obsługi produktów (CRUD) w kontrolerze (zamiast
listy)

✅ 4.0 Należy dodać model Koszyka oraz dodać odpowiedni endpoint

✅ 4.5 Należy stworzyć model kategorii i dodać relację między kategorią,
a produktem

✅ 5.0 pogrupować zapytania w gorm’owe scope'y

KOD: https://github.com/DanielBros1/Ebiznes/tree/master/zadanie_4
#

### Zadanie 5: Frontend
✅ 3.0 W ramach projektu należy stworzyć dwa komponenty: Produkty oraz Płatności; Płatności powinny wysyłać do aplikacji serwerowej dane, a w Produktach powinniśmy pobierać dane o produktach z aplikacji serwerowej;

✅ 3.5 Należy dodać Koszyk wraz z widokiem; należy wykorzystać routing

✅ 4.0 Dane pomiędzy wszystkimi komponentami powinny być przesyłane za pomocą React hooks

✅ 4.5 Należy dodać skrypt uruchamiający aplikację serwerową oraz kliencką na dockerze via docker-compose

✅ 5.0 Należy wykorzystać axios’a oraz dodać nagłówki pod CORS

KOD: https://github.com/DanielBros1/Ebiznes/tree/master/zadanie_5
#

### Zadanie 6: Testy

✅ 3.0 Należy stworzyć 20 przypadków testowych w CypressJS lub Selenium
(Kotlin, Python, Java, JS, Go, Scala)

✅ 3.5 Należy rozszerzyć testy funkcjonalne, aby zawierały minimum 50 asercji

✅ 4.0 Należy stworzyć testy jednostkowe do wybranego wcześniejszego projektu z minimum 50 asercjami

✅ 4.5 Należy dodać testy API, należy pokryć wszystkie endpointy z minimum jednym scenariuszem negatywnym per endpoint

❌ 5.0 Należy uruchomić testy funkcjonalne na Browserstacku

KOD: https://github.com/DanielBros1/Ebiznes/tree/master/zadanie_5/server/src/test/kotlin/org/example/server <br>
KOD: https://github.com/DanielBros1/Ebiznes/tree/master/zadanie_5/client/cypress <br>
RECORDING (do zadania 5 i 6): https://github.com/DanielBros1/Ebiznes/blob/master/recordings/ebiznes_6.wmv


#

### Zadanie 7: Sonar

✅ 3.0 Należy dodać litera do odpowiedniego kodu aplikacji serwerowej w  hookach gita

✅ 3.5 Należy wyeliminować wszystkie bugi w kodzie w Sonarze (kod aplikacji serwerowej)

✅ 4.0 Należy wyeliminować wszystkie zapaszki w kodzie w Sonarze (kod aplikacji serwerowej)

✅ 4.5 Należy wyeliminować wszystkie podatności oraz błędy bezpieczeństwa w kodzie w Sonarze (kod aplikacji serwerowej)

✅ 5.0 Należy wyeliminować wszystkie błędy oraz zapaszki w kodzie aplikacji klienckiej


[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=DanielBros1_Ebiznes&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=DanielBros1_Ebiznes)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=DanielBros1_Ebiznes&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=DanielBros1_Ebiznes)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=DanielBros1_Ebiznes&metric=bugs)](https://sonarcloud.io/summary/new_code?id=DanielBros1_Ebiznes)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=DanielBros1_Ebiznes&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=DanielBros1_Ebiznes)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=DanielBros1_Ebiznes&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=DanielBros1_Ebiznes)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=DanielBros1_Ebiznes&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=DanielBros1_Ebiznes)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=DanielBros1_Ebiznes&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=DanielBros1_Ebiznes)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=DanielBros1_Ebiznes&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=DanielBros1_Ebiznes)
#

### Zadanie 8: Oauth2

❌ 3.0 logowanie przez aplikację serwerową (bez Oauth2)

❌ 3.5 rejestracja przez aplikację serwerową (bez Oauth2)

❌ 4.0 logowanie via Google OAuth2

❌ 4.5 logowanie via Facebook lub Github OAuth2

❌ 5.0 zapisywanie danych logowania OAuth2 po stronie serwera