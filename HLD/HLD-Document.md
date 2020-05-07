### Come sarà strutturato il progetto?
Di solito le applicazioni di questo genere sono distribuite in vari Tier. Per maggiori informazioni visita la pagina Wiki: [Applicazioni Distribuite](https://github.com/BBT-SaS/Clothamatic/wiki/Applicazioni-Distribuite).

Per ridurre al minimo i costi hardware e per rispettare le richieste del cliente, si è preso in considerazione la suddivisione **Two Tier**, ovvero suddividere in due macchine i tre livelli applicativi. Nelle macchine e nei terminali dei negozi verrà installato un **thin client** progettato preferibilmente nel OOPL(Object Oriented Programming Language) Java. A lato server, invece, verranno supportati i livelli di logica applicativa e di accesso ai dati. Questo permetterà al front-end di incidere al minimo sulle prestazioni sul singolo host.

## Struttura a blocchi
![HLD-clothamatic](https://user-images.githubusercontent.com/43414688/81339374-5ac83180-90ae-11ea-9872-9f992fb98bde.png)



### Quindi in parole povere?

Verrà costruita un'architettura di tipo client/server. Questa scelta rispetto alla più semplice pagina web dinamica permetterà di evitare attacchi da parte di malintenzionati presenti su Internet. La GUI verrà distribuita fra i manager di ogni filiale, a cui sarà affidata la responsabilità di distribuire fra i subordinati il software client.

**Tra le due macchine bisognerà stabilire un protocollo di comunicazione affidabile e che previene ogni perdita di dati**. L'intero team BBT SaS sarà responsabile della stesura del LLD Document per i protocolli di comunicazione
## Front-end
Dovrà essere:
* Il più user-friendly possibile;
* Suddiviso in due categorie: _lato dipendenti_ e _lato clienti_;
* Sviluppato attraverso un linguaggio platform-independent;
* Supportato anche da OS datati (es. Windows XP);
* Corredato da manuali e formazione;
* Testato in ogni sua funzionalità;

### Clienti

L'interfaccia sarà ospitata su dei terminali non molto performanti, quindi dovrà essere il più leggero possibile. 

**Gli scambi di informazioni con il database dovranno essere limitate alla sola pressione del tasto di prenotazione**.
È necessario comunque confermare l'ordine prima che l'utente proceda con la prenotazione, perciò prima della schermata di avvenuto ordine bisognerà progettare una **pagina di conferma**, dove l'utente può vedere l'ordine che sta effettuando (tutti i dettagli dei prodotti devono essere presenti) e possa ricontrollare le informazioni inserite. **Dovranno essere posti dei vincoli ai valori dell'email e del numero di telefono**.

All'avvio dell'applicazione, che sarà avviata ogni mattina, bisognerà **verificare l'avvenuta connessione del terminale**, e se si è offline segnalare la mancanza di connessione.

**Bufalo Fabio** sarà leader dello sviluppo, e si occuperà della stesura del documento LLD.

### Dipendenti

Questa sezione dovrà avere privilegi in più, perciò dovrà essere progettata in maniera minuziosa. Le credenziali di accesso al server dovranno essere criptate, ma si procede a parlarne nella sezione _Protocolli di comunicazione_.
Le credenziale potranno essere recuperate attraverso un apposito link che ridirezione l'utente a una pagina dove saranno chieste delle informazioni riservate. **Questa parte del front-end deve essere utilizzata solo connessi a una rete internet**. È necessario ricordare all'utente, qualora sia offline, di connettersi a una rete. **Questo controllo è da fare all'avvio dell'applicazione**.

**Bufalo Fabio** sarà leader dello sviluppo, e si occuperà della stesura del documento LLD.

## Back-end


Comprenderà il middleware e il back-end, con accesso a un DataBase integrato.
Il software che accederà ai dati e risponderà alle risposte del client dovrà essere:
* Sviluppato attraverso un linguaggio platform-independent, preferibilmente Java;
* Ottimizzato nei tempi di risposta;
* Il più leggero possibile, per permettere al DBMS di sfruttare al massimo le prestazioni della macchina;
* Prevedere delle interruzioni di servizio e essere in grado di ripristinare i dati;
* Supportato anche da OS datati (es. Windows XP);
* Corredato da manuali e formazione;
* Testato in ogni sua funzionalità;

La leadership dello sviluppo del back-end verrà affidato a **Bafunno Lorenzo**.

## Protocolli di comunicazione
Per poter permettere al client di comunicare con il server e viceversa, è necessario che si instauri una connessione sicura fra i due host. Si escludono con questi criteri i protocolli UDP, HTTP. La soluzione migliore sarebbe utilizzare dei **socket TCP** per la sua _affidabilità, sicurezza e scalabilità_.

