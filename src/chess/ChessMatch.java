package chess;

import boardgame.Board;

public class ChessMatch {
	
	// quem sabe a dimensão do tabuleiro de xadrez é a classe ChessMatch
	// nessa classe que será dito que a dimensão do tabuleiro será 8x8

	private Board board; // uma partida de xadrez tem que ter um tabuleiro
	
	public ChessMatch() {
		board = new Board(8, 8);
	}
	
	public ChessPiece[][] getPieces() {
		// retorna uma matriz de peças de xadrez correspondente a essa partida
		
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
}
