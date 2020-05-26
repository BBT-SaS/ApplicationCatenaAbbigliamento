## Low Level Design Document - Database

![ERDiagram](https://user-images.githubusercontent.com/43414688/82757375-5c993100-9de0-11ea-99c0-e2c9061446d2.png)

Il diagramma E/R raffigurato mostra la struttura generale del database. Questo può essere tradotto in tabelle nel seguente modo:

* Prodotti(<ins>IDProdotto</ins>, Sesso _(può essere null)_ Tipo, Taglia _(può essere null)_, Prezzo, Colore, Costo, Materiale, ImageURL, Negozio (FK));
* Clienti (<ins>IDCliente</ins>, E-mail, Nome, Cognome, Indirizzo, Città, CAP);
* ProdottiPrenotati(<ins>IDProdotto, IDCliente</ins>, DataPrenotazione);
* Negozi (<ins> Indirizzo, Città</ins>, CAP);
* Dipendenti(<ins>Matricola</ins>, Nome, Cognome, E-mail, Username, Password);

L'associazione fra Prodotti e Clienti è stata gestita in questo modo in quanto nessuno dei due aveva obbligatorietà nell'associazione e questo metodo risulta il più affidabile.

Ipotesi aggiuntive considerate nel processo di realizzazione:

* **Ogni dipendendente avrà accesso al DataBase**
* **Ogni prodotto avrà sempre associato un'immagine**
* **Username e password dei dipendenti saranno stringhe cifrate**

