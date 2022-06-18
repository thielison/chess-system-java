package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}

	// converte um rei (King) para String
	// A letra "K" de King entra na hora de imprimir o tabuleiro, onde estiver a pe�a no tabuleiro vai aparecer a letra "K"
	@Override
	public String toString() {
		return "K";
	}
}
