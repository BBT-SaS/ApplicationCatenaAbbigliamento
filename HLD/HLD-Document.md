### Come sarà strutturato il progetto?
Di solito le applicazioni di questo genere sono distribuite in vari Tier. Per maggiori informazioni visita la pagina Wiki: [Applicazioni Distribuite](https://github.com/BBT-SaS/Clothamatic/wiki/Applicazioni-Distribuite).

Per ridurre al minimo i costi hardware e per rispettare le richieste del cliente, si è preso in considerazione la suddivisione **Two Tier**, ovvero suddividere in due macchine i tre livelli applicativi. Nelle macchine e nei terminali dei negozi verrà installato un **thin client** progettato preferibilmente nel OOPL(Object Oriented Programming Language) Java. A lato server, invece, verranno supportati i livelli di logica applicativa e di accesso ai dati. Questo permetterà al front-end di incidere al minimo sulle prestazioni sul singolo host.

## Struttura a blocchi
![HLD-clothamatic](https://user-images.githubusercontent.com/43414688/81342986-7a625880-90b4-11ea-976c-b39ce38ca7e1.png)



### Quindi in parole povere?

Verrà costruita un'architettura di tipo client/server. Questa scelta rispetto alla più semplice pagina web dinamica permetterà di evitare attacchi da parte di malintenzionati presenti su Internet. La GUI verrà distribuita fra i manager di ogni filiale, a cui sarà affidata la responsabilità di distribuire fra i subordinati il software client.

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

**Trocchio Lorenzo** sarà leader dello sviluppo, e si occuperà della stesura del documento LLD.

## Back-end


Comprenderà il middleware e il back-end, con accesso a un DataBase integrato.

### Middle-tier

Il software che accederà ai dati e risponderà alle risposte del client dovrà essere:
* Sviluppato attraverso un linguaggio platform-independent, preferibilmente Java;
* Ottimizzato nei tempi di risposta;
* Il più leggero possibile, per permettere al DBMS di sfruttare al massimo le prestazioni della macchina;
* Prevedere delle interruzioni di servizio e essere in grado di ripristinare i dati;
* Supportato anche da OS datati (es. Windows XP);
* Corredato da manuali e formazione;
* Testato in ogni sua funzionalità;

Il server dovrà essere disponibile negli orari lavorativi, e lo stesso dovrà ricevere molte richieste, quindi dovrà gestire molte richieste nello stesso momento. Lo sviluppo dovrà implementare perciò un **sistema di multi-threading** per compiere il lavoro simultaneamente. Allo stesso tempo, dovrà impedire alle molte richieste di generare inconsistenza dei dati, perciò dovrà implementare un **sistema di lock delle risorse**: la soluzione migliore sarebbe usare i monitor.

### Database

Si preferisce un database relazionale, dato che il DBMS agevola il compito dello script, monitorando egli stesso l'integrità e la consistenza dei dati. Si presume che i dati non siano di grande dimensione. Soltanto la creazione del database e delle tabelle saranno scritte a mano, per il resto l'inserimento, la cancellazione e la modifica dei dati sarà gestita dal server-side script.

**È necessario che le funzioni siano testati più volte per avere la sicurezza del loro funzionamento ottimale**.

**Bafunno Lorenzo** sarà leader dello sviluppo back-end, e si occuperà della stesura del documento LLD.

## Protocolli di comunicazione e sicurezza

### Sicurezza

Per poter permettere al client di comunicare con il server e viceversa, è necessario che si instauri una connessione sicura fra i due host. Oltre al protocollo affidabile TCP, è necessario cifrare i dati come credenziali e dati personali dei clienti. Data la semplicità dell'architettura, verrà usata un algoritmo di hash (**sha-256**), che otterrà l'effetto di avvertire i due capi della connessione se il messaggio è stato aperto da qualcun altro, e anche se il mittente è quello giusto.

### Protocolli di comunicazione

Il TCP è la soluzione affidabile necessaria a questa architettura. Dato che si possono aprire più socket contemporaneamente, ed essi identificano univocamente il client con cui si parla, il server può registrare gli IP da cui provengono i pacchetti, e **se il pacchetto non possiede l'hash giusto (quindi il mittente è falso), può ignorarlo e mettere l'host in una blacklist**.

Per poter comunicare e potersi accertare che il mittente sia valido, client e server devono stabilire delle specifiche stringhe note soltanto ai due. Queste stringhe saranno scritte nei rispettivi documenti LLD, ma in generale dovranno consistere in:

**Client Side**

* **Stringa ID della richiesta**: permette al server di riconoscere per cosa il client sta contattando il server (inserimento, prenotazione, modifica, etc.);
* **Stringa ID univoca del pacchetto (generata casualmente)**: permette al server di controllare questa stringa con altre di pacchetti già ricevuta, in modo da evitare un eventuale attacco di camuffamento dei pacchetti.

* **Stringhe username e password dipendenti (solo lato dipendenti)**: ovviamente questa string adovrà essere crittografata così che malintenzionati esterni non possano carpirli.

* **Stringhe dati personali cliente**: ovviamente questa string adovrà essere crittografata così che malintenzionati esterni non possano carpirli.

**Server Side**

* **Stringa di ACK**: per far sapere al client che la richiesta è stata verificata e sta venendo processata.
* **Stringa di OK**: per far sapere al client che la richiesta è andata a buon fine.
* **Stringa di errore**: per far sapere al client che la richiesta è errata oppure che ci sono stati errori durante il processo. Se è un problema di dati, bisognerebbe far sapere al client che tipo di errore è.

Il middle-tier negozierà le trasmissione fra DBMS e client, facendo accedere al DB soltanto ai dipendenti autorizzati.

