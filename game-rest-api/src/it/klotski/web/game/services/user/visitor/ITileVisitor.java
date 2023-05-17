package it.klotski.web.game.services.user.visitor;

import it.klotski.web.game.domain.tile.IRectangularTile;
import it.klotski.web.game.domain.tile.IWinningTile;

/**
 * Interfaccia che utilizza il design pattern "Visitor" per distinguere i pezzi dal loro comportamento, in modo da
 * associare a una determinata forma un determinato comportamento.
 *
 * L'interfaccia ITileVisitor definisce due metodi di default, `visitRectangularTile` e `visitWinningTile`, che possono
 * essere implementati dalle classi che implementano questa interfaccia. Questi metodi vengono utilizzati per visitare
 * i diversi tipi di tessere e definire il comportamento associato a ciascuna tessera.
 */
public interface ITileVisitor {
    /**
     * Metodo di default per visitare una tessera rettangolare.
     *
     * @param tileConfig la tessera rettangolare da visitare
     */
    default void visitRectangularTile(IRectangularTile tileConfig) {

    }

    /**
     * Metodo di default per visitare una tessera vincente.
     *
     * @param winningTile la tessera vincente da visitare
     */
    default void visitWinningTile(IWinningTile winningTile) {

    }
}
