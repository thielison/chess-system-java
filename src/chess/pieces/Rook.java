package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);	
	}
	
	
	// converte uma torre (Rook) para String
	// A letra "R" de Rook entra na hora de imprimir o tabuleiro, onde estiver a peça no tabuleiro vai aparecer a letra "R"
	@Override
	public String toString() {
		return "R";
	}
}
