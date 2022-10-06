package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////

import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {
    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen


    public DobbeltLenketListe() {
        //lager tom liste
        hode = null;
        hale = null;
        antall = 0;
    }

    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a, "Tabellen a er null!");

        for (int i = 0; i < a.length; i++) {
            if (!Objects.isNull(a[i])) {
                //plasserer det første elementet hvis listen er tom
                if (tom()) {
                    Node<T> n = new Node<>(a[i], hode, hale);
                    hode = hale = n;
                    antall++;
                } else {
                    //plasserer de resterende Nodene baksert
                    Node<T> n = new Node<>(a[i]);
                    hale.neste = n;
                    n.forrige = hale;
                    hale = n;
                    antall++;
                }
            }
        }
    }

    public Liste<T> subliste(int fra, int til) {
        DobbeltLenketListe<T> utListe = new DobbeltLenketListe<>();
        //godkjenning av input, og returnerer tom liste, hvis listen er tom
        if (antall() == 0) {
            return utListe;
        }
        fraTilKontroll(fra,til);


        Node<T> n = finnNode(fra);
        int indeks = 0;
        for (int i = fra; i < til; i++) {
            //genererer utlisten
            utListe.leggInn(n.verdi);
            n = n.neste;
            indeks++;
        }
        //setter endringer og indeks
        utListe.antall = indeks;
        utListe.endringer = 0;
        return utListe;
    }

    private void fraTilKontroll(int fra, int til){
        //godkjenning av inputten fra og til, om de er innenfor scope og ikke omvendt.
        if (fra < 0 || fra > antall()){
            throw new IndexOutOfBoundsException(fra + " fra er utenfor sublistens scope: " + antall());}

        if (til < 0 || til > antall()) {
            throw new IndexOutOfBoundsException(til + " til er utenfor sublistens scope" + antall());}

        if (til < fra) {
            throw new IllegalArgumentException(til + " til er mindre enn fra" + fra);}
    }

    @Override
    public int antall() {return this.antall;}

    @Override
    public boolean tom() {return antall() == 0;}

    @Override
    public boolean leggInn(T verdi) {
        //sjekker innVerdi
        Objects.requireNonNull(verdi, "kan ikke legge inn en null verdi");

        int gmlEndring = endringer;

        if (tom()) {   //plasserer den første hvis listen er tom
            Node<T> n = new Node<>(verdi, hode, hale);
            hode = hale = n;

        } else {       //plasserer noden bakerst i listen
            Node<T> n = new Node<>(verdi, hale, null);
            hale.neste = n;
            n.forrige = hale;
            hale = n;

        }
        antall++;
        endringer++;

        return endringer > gmlEndring;
        //hvorfor returnere true?
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        //kontrollerer input
        Objects.requireNonNull(verdi);

        if (indeks < 0 || indeks > antall()){
            throw new IndexOutOfBoundsException(indeks + " indeks er utenfor listens lengde " + (antall()));
        }

        //setter inn node i tom liste
        if (antall() == 0 && indeks == 0){
            Node<T> n = new Node<>(verdi, hode, hale);
            hode = hale = n;
        }
        //setter inn Node bakerst
        else if (indeks == antall()){
            Node<T> n = new Node<T>(verdi, hale,null);
            hale.neste = n;
            hale = n;
        }
        //setter Node forann
        else if (indeks == 0){
            Node<T> n = new Node<T>(verdi, null, hode);
            hode.forrige = n;
            hode = n;
        }
        //setter Node imellom to eksisterende
        else {
            Node<T> flytter = finnNode(indeks);
            Node<T> forann = flytter.forrige;

          //  Node<T> forann = finnNode(indeks-1);
            Node<T> n = new Node<T>(verdi, forann, flytter);
            flytter.forrige = n;
            forann.neste = n;
        }
        endringer++;
        antall++;
    }

    @Override
    public boolean inneholder(T verdi) {
        //ser om listen inneholder verdien
        if (indeksTil(verdi)>=0){
            return true;
        }
        return false;
    }

    private Node<T> finnNode(int indeks) {
        //sjekker input
        indeksKontroll(indeks, false);

        int teller = 1;

        Node<T> tmp = hode;

        //hvis indeks er på første halvdel av listen starter søket der
        if (indeks < (antall() / 2)) {
            while (teller <= indeks) {
                tmp = tmp.neste;
                teller++;
            }       //søket starter bakerst hvis indeks er i siste halvdel
        } else {
            tmp = hale;
            teller = antall() - 1;
            while (teller > indeks) {
                tmp = tmp.forrige;
                teller--;
            }
        }
        return tmp;
    }

    @Override
    public T hent(int indeks) {return finnNode(indeks).verdi;}

    @Override
    public int indeksTil(T verdi) {
        //initialiserer variabler, sjekker listen og input
        int idx = -1;
        if (antall() == 0 || verdi == null) {
            return idx;
        }
        Node<T> n = finnNode(0);

        //søker igjennom listen etter verdien
        for (int i = 0; i < antall(); i++) {
            if (n.verdi.equals(verdi)){
                return i;
            }
            n = n.neste;
        }
        return idx;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {

        Objects.requireNonNull(nyverdi);    //sjekker input
        indeksKontroll(indeks, false);

        Node<T> gmlNode = finnNode(indeks);  //setter inn noden
        T gmlVerdi = gmlNode.verdi;
        gmlNode.verdi = nyverdi;
        endringer++;                //oppdaterer listen
        return gmlVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        //sjekker input
        if (verdi == null){return false;}
        int idx = indeksTil(verdi);
        //returnerer tom liste
        if (antall()==0){
            return false;
        }

        //fjerner bakerst
        else if (idx == antall()-1){
            hale.forrige = hale.forrige.forrige;
            hale.forrige.forrige.neste = hale.forrige;

        } else if (idx == 0) {
            hode.neste = hode.neste.neste;
            hode.neste.forrige = hode.neste;
        } else {
            Node<T> forann = finnNode(idx-1);
            Node<T> bak = forann.neste.neste;
            forann.neste = bak;
            bak.forrige = forann;
        }
        endringer++;
        antall--;
        return true;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks,false);

        //returnerer tom Node hvis listen er tom
        if (antall() == 0) {
            return null;
        }
        //jobb her



        /*indeksKontroll(indeks,false);
        if (antall() ==0){
            return null;
        }
        if (indeks > antall()-1 || indeks < 0){
            return null;
        }
        //Node<T> n = finnNode(indeks);

        Node<T> n = new Node<T>(hode.verdi);
        if (indeks == 0) {
            n = new Node<T>(hode.neste.verdi, null,hode.neste.neste);
            hode.neste = n;
        }
        else if (indeks == antall()-1){
            n = new Node<T>(hale.forrige.verdi, hale.forrige,null);
            hale.forrige = n;
        }
        else {

        }
        endringer++;
        antall--;
        return n.verdi;
*/      throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {

        StringBuilder ut = new StringBuilder();
        ut.append("[");
        Node<T> tmp = hode;

        while (tmp != null) {       //setter inn verdier i listen og legger til komma
            ut.append(tmp.verdi).append(", ");
            tmp = tmp.neste;
        }

        if (ut.length() > 2) {      //fjerner enden av stringen hvis den ikke er tom
            ut.delete(ut.length() - 2, ut.length());
        }

        ut.append("]");
        return ut.toString();
    }

    public String omvendtString() {
        //samme som toString bare bakvendt
        StringBuilder ut = new StringBuilder();
        ut.append("[");
        Node<T> tmp = hale;

        while (tmp != null) {
            ut.append(tmp.verdi).append(", ");
            tmp = tmp.forrige;
        }
        if (ut.length() > 2) {
            ut.delete(ut.length() - 2, ut.length());
        }
        ut.append("]");
        return ut.toString();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }


    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        /*
        String[] s = {"Ole", null, "Per", "Kari", null};
        Liste<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste.antall() + " " + liste.tom());
        System.out.println(liste);
        System.out.println(liste.omvendtString());
        liste.leggInn("Baltus");
        liste.leggInn("Bror");
        System.out.println(liste);
        System.out.println(liste.omvendtString());
        */

        Character[] c = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
      // System.out.println(liste.subliste(3, 8)); // [D, E, F, G, H]
      // System.out.println(liste.subliste(5, 5)); // []
      // System.out.println(liste.subliste(8, liste.antall())); // [I, J]


        for (int i = 0; i < liste.antall(); i++) {
            Node<Character> n = liste.finnNode(i);
        }

        Integer[] nn = {1};
        DobbeltLenketListe<Integer> enliste = new DobbeltLenketListe<Integer>(nn);

        String ut = enliste.toString();
        Integer[] tomList = new Integer[0];
        DobbeltLenketListe<Integer> annenListe = new DobbeltLenketListe<>(tomList);

        annenListe.leggInn(0,1);
        String ut1 = annenListe.toString();


        System.out.println(liste);
        liste.leggInn(3,'Z');
        System.out.println(liste);
        liste.leggInn(0,'H');
        System.out.println(liste);
        liste.leggInn(liste.antall(),'O');
        System.out.println(liste);
        System.out.println(liste.antall());

        //System.out.println(liste.subliste(0,11)); // skal kaste unntak


        /*DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        System.out.println(liste.toString() + " " + liste.omvendtString());
        for (int i = 0; i <= 14; i++) {
            liste.leggInn(i);
            System.out.println(liste.toString() + " " + liste.omvendtString());*/


       /* String[] s1 = {}, s2 = {"A"}, s3 = {null,"A",null,"B",null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);
        System.out.println(l1.toString() + " " + l2.toString()
                + " " + l3.toString() + " " + l1.omvendtString() + " "
                + l2.omvendtString() + " " + l3.omvendtString());*/


    }

} // class DobbeltLenketListe
