package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	// quem sabe a dimensão do tabuleiro de xadrez é a classe ChessMatch
	// nessa classe que será dito que a dimensão do tabuleiro será 8x8

	private Board board; // uma partida de xadrez tem que ter um tabuleiro
	
	public ChessMatch() {
		board = new Board(8, 8); // no momento de inicio da da partida, é criado um tabuleiro 8x8
		initialSetup(); // e é chamada a função initialSetup();
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
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { // método responsável por receber as coordenadas do xadrez
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	// método responsável por iniciar a partida de xadrez, colocando as peças no tabuleiro
	private void initialSetup() {
		 placeNewPiece('b', 6, new Rook(board, Color.WHITE));
		 placeNewPiece('e', 8, new King(board, Color.BLACK));
		 placeNewPiece('e', 1, new King(board, Color.WHITE));
	}
}
