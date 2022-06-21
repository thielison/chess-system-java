package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	// quem sabe a dimens�o do tabuleiro de xadrez � a classe ChessMatch
	// nessa classe que ser� dito que a dimens�o do tabuleiro ser� 8x8

	private int turn;
	private Color currentPlayer;	
	private Board board; // uma partida de xadrez tem que ter um tabuleiro

	public ChessMatch() {
		board = new Board(8, 8); // no momento de inicio da da partida, � criado um tabuleiro 8x8
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup(); // e � chamada a fun��o initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
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

	public boolean [][] possibleMoves(ChessPosition sourcePosition) { // a opera��o possibleMoves imprime as posi��es poss�veis a partir de uma posi��o de origem
		Position position = sourcePosition.toPosition();  // converte essa posi��o de xadrez para posi��o de matriz normal
		validateSourcePosition(position); // valida posi��o de origem
		return board.piece(position).possibleMoves(); // retorna os movimentos pos�veis dessa pe�a na posi��o
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source); // opera��o respons�vel por validar a posi��o de origem, se ela n�o existir, lan�a uma exce��o
		validateTargetPosition(source, target); // opera��o respons�vel por validar a posi��o de destino, se ela n�o existir, lan�a uma exce��o
		Piece capturedPiece = makeMove(source, target);
		nextTurn(); // troca o turno
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
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { // lan�a uma exce��o caso o jogador esteja tentando mover uma pe�a advers�ria
			throw new ChessException("The chosen piece is not yours.");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) { // se n�o tiver nenhum movimento poss�vel, lan�a uma exce��o
			throw new ChessException("There is no possible moves for the chosen piece.");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) { // se pra pe�a de origem a posi��o de destino n�o � um movimento poss�vel, significa que n�o pode mexer a pe�a pra l�
			throw new ChessException("The chosen piece can't move to target position.");
		}
	}
	
	private void nextTurn() {
		turn++; // incrementa o turno, passa para o turno 1, turno 2...
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; 
		// se o jogador atual for igual a Color.WHITE, ent�o agora vai ser igual a Color.BLACK. Caso contr�rio, ele vai ser Color.White
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
