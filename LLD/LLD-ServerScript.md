## Low Level Design Document - Server Script

Questo documento descriverà il middle-tier dell'applicazione presa in considerazione. L'interfaccia installata sui client dovrà interagire soltanto con questo script.

### Linguaggio di programmazione
In questo progetto è importante la velocità di risposta e di semplicità di progettazione, a discapito di una gestione della memoria utilizzata. Java perciò è la risposta a tutto questo, anche perchè gestisce la memoria indipendentemente. Oltretutto, esistono librerie di connessioni a molti dei database relazionali, primo fra tutti MySQL, già implementato dal team BBT-SaS.

### Struttura

![LLD-ServerSide](https://user-images.githubusercontent.com/43414688/81901969-a81d3500-95bf-11ea-9b1b-97ce0deaa9c8.png)


La fase principale di connessione con il client sarà gestita attraverso un WelcomeSocket, del tipo TCP ServerSocket. Attraverso di esso sarà possibile capire ogni qualvolta verrà richiesta la risorsa e quindi poter suddividere in Thread le richieste.

Le funzionalità di questo server-side script sono:

* Multi-threading
* Storage delle informazioni socket per future connessioni
* Black-listing degli IP+porta non validi
* Interazione con il DB, utilizzando funzioni del linguaggio SQL.
* Approvazione dei socket in base all'algoritmo di hash, protocolli di comunicazione utilizzati e/o protocolli di crittografia.
* Comunicazione duplex con il client.

Una volta che sono stati suddivisi in thread, la richiesta verrà elaborata e verrano svolte le operazioni richieste. Prima di ogni modifica, è necessario che ci sia un controllo effettivo che il prodotto richiesto sia effettivamente disponibile: in quanto le richieste avvengono simultaneamente, è possibile che qualcuno abbia modificato i dati, impossibilitando la conclusione della richiesta di un altro thread. Perciò è necessario attuare una politica di scheduling che sia mutualmente esclusiva. Soltanto un thread può entrare in una sezione di dati alla volta. Questo non vuol dire limitare l'intero accesso al DB, ma solo proteggere una singola tabella. Per effettuare questo, esistono metodi come i monitor o semafori.

![Thread-lock](https://user-images.githubusercontent.com/43414688/81902640-c0da1a80-95c0-11ea-9652-2dcfdce78f28.png)

### Welcome Socket
Si occuperà della gestione delle richieste e perciò dovrà necessariamente avere un **ServerSocket** che si metterà in ascolto su una porta prestabilita.

![Welcome-socket](https://user-images.githubusercontent.com/43414688/82296281-42f78400-99b1-11ea-84de-37fdaac67873.png)

Appena ricevuta una richiesta, il WelcomeSocket controllerà la validità della richiesta (controllando ID univoco e funzione di hash) e se la richiesta è valida procederà a creare un Thread e a creare un SocketWorker, che scomporrà la richiesta e la processerà. Attraverso un array di SocketWorker, il WelcomeSocket saprà quali connessioni sono attive, perciò connessioni provenienti dallo stesso socket (IP+Porta) verranno rifiutate fino a quando una connessione valida sarà attiva.

Il controllo del Welcome-Socket sul socket avviene in questo modo:

* Controllo blacklist: in caso il welcome-socket trovi l'indirizzo IP in questa sezione, la richiesta sarà rifiutata senza neanche avvisare il client.

* Controllo socket richiesta: in caso ci sia già una richiesta attiva, il welcome socket manderà indietro una stringa di errore 

* Controllo fingerprint del messaggio: se la fingerprint non corrisponde, l'IP viene messo nella black-list e la richiesta scartata.

* Controllo ID univoco: Se l'ID è già stato utilizzato da connessioni precedenti, l'IP viene messo in una blacklist.

* Controllo ID della richiesta: controllo puramente formale per evitare richieste non necessarie. Se l'ID è sbagliato verrà mandato indietro una stringa di errore.

* Controllo autorizzazione per la richiesta: se la richiesta non presenta username e password con l'autorizazzione necessaria, viene mandato indietro una stringa di errore "unauthorized".



Una tecnica degna di nota per impedire che le credenziali possano essere scoperte accedendo al DB è di depositare stringhe crittografate. Quindi lo script non decifrerà le credenziali crittografate ma le confronterà con quelle depositate nel DB  e le convertirà soltanto per mandarle al client quando richiede un recupero credenziali.

### Connessione con il DBMS

Per poter ottenere informazioni, il middle-tier ha necessità di contattare il DBMS, soprattutto per conoscere le autorizzazioni di ogni username+password dei dipendenti.

Dato che il DB sarà sulla stessa macchina, non è necessario contrattare una connessione sicura, e perciò bisognerà soltanto chiedere le informazioni necessarie al DB.

Attraverso il driver JDBC per MySQL è possibile instaurare una connessione. Prima di rispondere al client con una risposta di OK, deve necessariamente modificare, inserire o rimuovere dati.

![Connessione-DB](https://user-images.githubusercontent.com/43414688/82299804-42152100-99b6-11ea-84eb-4bf7d200f4f4.png)

