package chess;

import boardgame.Board;

public class ChessMatch {
	
	// quem sabe a dimens�o do tabuleiro de xadrez � a classe ChessMatch
	// nessa classe que ser� dito que a dimens�o do tabuleiro ser� 8x8

	private Board board; // uma partida de xadrez tem que ter um tabuleiro
	
	public ChessMatch() {
		board = new Board(8, 8);
	}
	
	public ChessPiece[][] getPieces() {
		// retorna uma matriz de pe�as de xadrez correspondente a essa partida
		
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
}
