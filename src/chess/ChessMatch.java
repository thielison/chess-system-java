package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	// quem sabe a dimens�o do tabuleiro de xadrez � a classe ChessMatch
	// nessa classe que ser� dito que a dimens�o do tabuleiro ser� 8x8

	private Board board; // uma partida de xadrez tem que ter um tabuleiro

	public ChessMatch() {
		board = new Board(8, 8); // no momento de inicio da da partida, � criado um tabuleiro 8x8
		initialSetup(); // e � chamada a fun��o initialSetup();
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

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source); // opera��o respons�vel por validar a posi��o de origem, se ela n�o existir, lan�a uma exce��o
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece)capturedPiece; 
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source); // retira a pe�a que estava na posi��o de origem
		Piece capturedPiece = board.removePiece(target); // remove a poss�vel pe�a que esteja na posi��o de destino
		board.placePiece(p, target); // coloca a pe�a p na posi��o de destino target
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) { // se n�o existe uma pe�a nessa posi��o, lan�a a exce��o
			throw new ChessException("There is no piece on source position.");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) { // se n�o tiver nenhum movimento poss�vel, lan�a uma exce��o
			throw new ChessException("There is no possible moves for the chosen piece.");
		}
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { // m�todo respons�vel por receber as coordenadas do xadrez
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	// m�todo respons�vel por iniciar a partida de xadrez, colocando as pe�as no tabuleiro
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));

		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
