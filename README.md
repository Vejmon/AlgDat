# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Vemund Hellekleiv, s362068, s362068@oslomet.no

# Arbeidsfordeling

I oppgaven har vi hatt følgende arbeidsfordeling:
* jeg har gjort oppgavene aleine, syns obligene virker som god øving i dette faget.

# Oppgavebeskrivelse

I oppgave 1 jeg jobbet litt med å komme i gang og skjønne hvordan listen skulle fungere,
slet litt med at hode og hale skulle holde null som verdi, har senere gått vekk fra dette da jeg leste oppgaveteksten litt grundigere...

I oppgave 2 så løste jeg med å lage en loop forfra og en bakfra, tenkte jeg skulle samle stringbyggingen i en hjelpemetode, men hadde løst oppgaven allerede da. hadde en katastrofe feil i legginn(T) og senere i legginn(T,i) metodene mine, hvor en tom liste fikk en hale og en hode som pekte på hver sin like verdi. Skjønner ikke helt hvorfor leggInn(T) skal returnere noe heller,

I oppgave 3, ble forvirret av 3å3 og 3å4 i test klassen, fant ikke noe i oppgaveteksten om å kaste "IllegalArgumentException" for at fra > til. men har det på plass i oppgaven min nå. 

opg4, Disse metodene gikk ganske greit å implementere, hadde krokodilletegnet feil vei i inneholder såklart, ellers ikke noe drama, løste oppgaven litt smartere senere og, da jeg byttet:
Node<T> n = finnNode(0) med n = hode...

opg5, her krølla jeg litt med antall()-1 og ikke, her hadde jeg og den katastrofale feilen at hvis man la inn en Node i en tom liste, pekte hale og hode på to individuelle verdier, og ikke samme objekt.

opg6, her løste jeg tilslutt fjern(T) uten å eksplisit finne indeksen til T, har også prøvd å være flink og kommentere ganske så nøye hva som foregår.

for op7 lagde jeg en motode så jeg enklere senere kan kjøre testen min hvis jeg vil, jeg så og at nullstill() metoden er nesten dobbelt så effektiv som fjern0 metoden for alle, men måtte bruke veldig mange noder for å få noe meningsfullt resultat.