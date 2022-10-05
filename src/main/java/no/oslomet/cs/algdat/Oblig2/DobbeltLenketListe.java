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
        hode = null;
        hale = null;
        antall = 0;

    }

    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a, "Tabellen a er null!");

        for (int i = 0; i < a.length; i++) {
            if (!Objects.isNull(a[i])) {
                if (tom()) {
                    Node<T> n = new Node<>(a[i], hode, hale);
                    hode = hale = n;
                    antall++;
                } else {
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
        if (antall() == 0) {
            return utListe;
        }
        fraTilKontroll(fra,til);

        Node<T> n = finnNode(fra);
        int indeks = 0;

        for (int i = fra; i < til; i++) {
            utListe.leggInn(n.verdi);
            n = n.neste;
            indeks++;
        }
        utListe.antall = indeks;
        utListe.endringer = 0;
        return utListe;
    }

    private void fraTilKontroll(int fra, int til){
        if (fra < 0 || fra > antall()){throw new IndexOutOfBoundsException(fra + " fra er utenfor sublistens scope: " + antall());
    }
        if (til < 0 || til > antall()) {throw new IndexOutOfBoundsException(til + " til er utenfor sublistens scope" + antall());
        }
        if (til < fra) {
            throw new IllegalArgumentException(til + " til er mindre enn fra" + fra);
        }
    }


    @Override
    public int antall() {
        return this.antall;
    }

    @Override
    public boolean tom() {
        return antall() == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "kan ikke legge inn en null verdi");
        if (tom()) {
            Node<T> n = new Node<>(verdi, hode, hale);
            hode = hale = n;
            antall++;
            endringer++;
        } else {
            Node<T> n = new Node<>(verdi, hale, null);
            hale.neste = n;
            n.forrige = hale;
            hale = n;
            antall++;
            endringer++;
        }
        return true;
        //hvorfor returnere true?
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    private Node<T> finnNode(int indeks) {
        indeksKontroll(indeks, false);
        int teller = 1;
        Node<T> tmp = hode;

        if (indeks < (this.antall() / 2)) {
            while (teller <= indeks) {
                tmp = tmp.neste;
                teller++;
            }
        } else {
            tmp = hale;
            teller = this.antall() - 1;
            while (teller > indeks) {
                tmp = tmp.forrige;
                teller--;

            }
        }
        return tmp;
    }

    @Override
    public T hent(int indeks) {
        Node<T> enNode = finnNode(indeks);
        return enNode.verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        int idx = -1;
        Node<T> n = finnNode(0);
        if (verdi.equals(n.verdi)){
            return 0;
        }
        if (antall() >0){
        for (int i = 1; i < antall(); i++) {
             //jobb her  if ()
            }
        }
        //throw new UnsupportedOperationException();
        return idx;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi);
        indeksKontroll(indeks, false);
        Node<T> gmlNode = finnNode(indeks);
        T gmlVerdi = gmlNode.verdi;
        gmlNode.verdi = nyverdi;
        endringer++;
        return gmlVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
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

        while (tmp != null) {
            ut.append(tmp.verdi).append(", ");
            tmp = tmp.neste;
        }
        if (ut.length() > 2) {
            ut.delete(ut.length() - 2, ut.length());
        }
        ut.append("]");
        return ut.toString();
    }

    public String omvendtString() {
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
        System.out.println(liste.subliste(8, 3)); // [D, E, F, G, H]
        System.out.println(liste.subliste(5, 5)); // []
        System.out.println(liste.subliste(8, liste.antall())); // [I, J]
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
