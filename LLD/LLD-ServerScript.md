## Low Level Design Document - Server Script

Questo documento descriverà il middle-tier dell'applicazione presa in considerazione. L'interfaccia installata sui client dovrà interagire soltanto con questo script.

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


### Linguaggio di programmazione
In questo progetto è importante la velocità di risposta e di semplicità di progettazione, a discapito di una gestione della memoria utilizzata. Java perciò è la risposta a tutto questo, anche perchè gestisce la memoria indipendentemente. Oltretutto, esistono librerie di connessioni a molti dei database relazionali, primo fra tutti MySQL, già implementato dal team BBT-SaS.