### Come sarà strutturato il progetto?
Di solito le applicazioni di questo genere sono distribuite in vari Tier. Per maggiori informazioni visita la pagina Wiki: [Applicazioni Distribuite](https://github.com/BBT-SaS/Clothamatic/wiki/Applicazioni-Distribuite).

Per ridurre al minimo i costi hardware e per rispettare le richieste del cliente, si è preso in considerazione la suddivisione **Two Tier**, ovvero suddividere in due macchine i tre livelli applicativi. Nelle macchine e nei terminali dei negozi verrà installato un **thin client** progettato preferibilmente nel OOPL(Object Oriented Programming Language) Java. A lato server, invece, verranno supportati i livelli di logica applicativa e di accesso ai dati. Questo permetterà al front-end di incidere al minimo sulle prestazioni sul singolo host.

### Quindi in parole povere?

Verrà costruita un'architettura di tipo client/server. Questa scelta rispetto alla più semplice pagina web dinamica permetterà di evitare attacchi da parte di malintenzionati presenti su Internet. La GUI verrà distribuita fra i manager di ogni filiale, a cui sarà affidata la responsabilità di distribuire fra i subordinati il software client.

**Tra le due macchine bisognerà stabilire un protocollo di comunicazione affidabile e che previene ogni perdita di dati**. L'intero team BBT SaS sarà responsabile della stesura del LLD Document per i protocolli di comunicazione
## Client Side
Questo è il famoso **front-end**. Dovrà essere:
* Il più user-friendly possibile;
* Suddiviso in due categorie: _lato dipendenti_ e _lato clienti_;
* Sviluppato attraverso un linguaggio platform-independent;
* Supportato anche da OS datati (es. Windows XP);
* Corredato da manuali e formazione;
* Testato in ogni sua funzionalità;

La leadership dello sviluppo dell'applicazione per i dipendenti sarà affidata a **Trocchio Lorenzo**, mentre **Bufalo Fabio** si occuperà del lato clienti.

## Server Side
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

## Conclusioni finali
Perciò possiamo concludere che il linguaggio di programmazione da preferire dovrà:

* implementare facilmente i protocolli di comunicazione TCP. 
* essere platform-independent.
* ad alto livello e ottimizzato in tutti i sistemi operativi.