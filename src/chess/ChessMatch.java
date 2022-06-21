package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	// quem sabe a dimensão do tabuleiro de xadrez é a classe ChessMatch
	// nessa classe que será dito que a dimensão do tabuleiro será 8x8

	private int turn;
	private Color currentPlayer;	
	private Board board; // uma partida de xadrez tem que ter um tabuleiro

	public ChessMatch() {
		board = new Board(8, 8); // no momento de inicio da da partida, é criado um tabuleiro 8x8
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup(); // e é chamada a função initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
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

	public boolean [][] possibleMoves(ChessPosition sourcePosition) { // a operação possibleMoves imprime as posições possíveis a partir de uma posição de origem
		Position position = sourcePosition.toPosition();  // converte essa posição de xadrez para posição de matriz normal
		validateSourcePosition(position); // valida posição de origem
		return board.piece(position).possibleMoves(); // retorna os movimentos posíveis dessa peça na posição
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source); // operação responsável por validar a posição de origem, se ela não existir, lança uma exceção
		validateTargetPosition(source, target); // operação responsável por validar a posição de destino, se ela não existir, lança uma exceção
		Piece capturedPiece = makeMove(source, target);
		nextTurn(); // troca o turno
		return (ChessPiece)capturedPiece; 
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source); // retira a peça que estava na posição de origem
		Piece capturedPiece = board.removePiece(target); // remove a possível peça que esteja na posição de destino
		board.placePiece(p, target); // coloca a peça p na posição de destino target
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) { // se não existe uma peça nessa posição, lança a exceção
			throw new ChessException("There is no piece on source position.");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { // lança uma exceção caso o jogador esteja tentando mover uma peça adversária
			throw new ChessException("The chosen piece is not yours.");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) { // se não tiver nenhum movimento possível, lança uma exceção
			throw new ChessException("There is no possible moves for the chosen piece.");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) { // se pra peça de origem a posição de destino não é um movimento possível, significa que não pode mexer a peça pra lá
			throw new ChessException("The chosen piece can't move to target position.");
		}
	}
	
	private void nextTurn() {
		turn++; // incrementa o turno, passa para o turno 1, turno 2...
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; 
		// se o jogador atual for igual a Color.WHITE, então agora vai ser igual a Color.BLACK. Caso contrário, ele vai ser Color.White
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { // método responsável por receber as coordenadas do xadrez
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	// método responsável por iniciar a partida de xadrez, colocando as peças no tabuleiro
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
