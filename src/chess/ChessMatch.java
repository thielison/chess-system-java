package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	// quem sabe a dimensão do tabuleiro de xadrez é a classe ChessMatch
	// nessa classe que será dito que a dimensão do tabuleiro será 8x8

	private Board board; // uma partida de xadrez tem que ter um tabuleiro
	
	public ChessMatch() {
		board = new Board(8, 8); // na hora que iniciar a partida, é criado um tabuleiro 8x8
		initialSetup();			// e é chamada a função initialSetup();
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
	
	// método responsável por iniciar a partida de xadrez, colocando as peças no tabuleiro
	private void initialSetup() {
		 board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));
		 board.placePiece(new King(board, Color.BLACK), new Position(0,4));
		 board.placePiece(new King(board, Color.WHITE), new Position(7,4));
	}
}
