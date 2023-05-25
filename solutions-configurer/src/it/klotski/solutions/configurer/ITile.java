package it.klotski.solutions.configurer;

/**
 * Questa interfaccia rappresenta una tessera del gioco.
 */
public interface ITile extends Comparable<ITile> {
    /**
     * Restituisce l'ID della tessera.
     *
     * @return l'ID della tessera
     */
    int getId();

}