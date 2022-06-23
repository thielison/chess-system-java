package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private boolean check;
	private boolean checkMate;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8); // no momento de inicio da da partida, é criado um tabuleiro 8x8
		turn = 1;
		currentPlayer = Color.WHITE;
		// check = false; -> serve para enfatizar o valor false do boolean de check, mas não é necessário, pois uma propriedade boolean por padrão começa com falso
		initialSetup(); // e é chamada a função initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
		
		if (testCheck(currentPlayer)) { // testa se o jogador colocou o próprio jogador em Check, isso não pode acontecer 
			undoMove(source, target, capturedPiece);	// se esse if falhar, significa que o jogador não se colocou em check
			throw new ChessException("You can't put yourself in check.");
		}
		
		// a propriedade check recebe -> se testCheck do oponente for verdade, retorna true, a partida está em check, senão retorna false, que não está em check
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if (testCheck(opponent(currentPlayer))) { // se a jogada que eu fiz deixou o oponente e checkMate, o jogo vai ter que acabar
			checkMate = true; // checkMate é true e acaba a partida
		}
		else { // caso não esteja em checkMate
			nextTurn(); // chama o próximo turno
		}
		return (ChessPiece)capturedPiece; 
	}
	
	private Piece makeMove(Position source, Position target) { 
		Piece p = board.removePiece(source); // retira a peça que estava na posição de origem
		Piece capturedPiece = board.removePiece(target); // remove a possível peça capturada que esteja na posição de destino
		board.placePiece(p, target); // coloca a peça p na posição de destino target
		
		if (capturedPiece != null) { // se uma peça foi capturada, ou seja, se for diferente de null, uma peça foi capturada
			piecesOnTheBoard.remove(capturedPiece); // então remove a peça capturada da lista de peças do tabuleiro
			capturedPieces.add(capturedPiece); // e adiciona ela na lista de peças capturadas
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target); // retira a peça que moveu lá no destino
		board.placePiece(p, source); // devolve a peça para a posição de origem
		
		if (capturedPiece != null) { // se verdadeiro...
			board.placePiece(capturedPiece, target); // volta a peça para o tabuleiro na posição de destino
			capturedPieces.remove(capturedPiece); // tira a peça da lista de peças capturadaS
			piecesOnTheBoard.add(capturedPiece); // coloca novamente na lista de peças do tabuleiro
		}
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
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
		// se essa cor passada como argumento for igual a Color.WHITE, então retorna Color.BLACK. Caso contrário, retorna Color.WHITE.
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) { // para cada peça p na lista list
			if (p instanceof King) { // se essa peça p for uma instância de rei, significa que retornou um rei
				return (ChessPiece)p; // retorna essa peça p (downcasting para ChessPiece)
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board.");
	} 
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) { // se nessa matriz na linha e coluna, se esse elemento for verdadeiro, signifca que o rei está em cheque
				return true; // retorna true se o teste de cheque for verdadeiro
			}
		}
		return false; // se esgotar todas peças adversárias e nenhuma dessas peças tiver na matriz de movimentos possíveis a posição do rei marcada como true, significa que o rei não está em cheque e retorna false 
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) { // teste para eliminar a possibilidade dessa peça/cor não estar em Check
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); // pega todas peças de uma determinada Color
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);	
						undoMove(source, target, capturedPiece);
						if (!testCheck) { // se não estava em check, significa que esse movimento tirou o rei do check e retorna false, que não está em checkMate
							return false;
						}
					}
				}
			}
		}
		return true;
	}	
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { // método responsável por receber as coordenadas do xadrez
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // coloca a peça no tabuleiro
		piecesOnTheBoard.add(piece); // coloca essa peça na lista de peças no tabuleiro
	}

	// método responsável por iniciar a partida de xadrez, colocando as peças no tabuleiro
	private void initialSetup() {
		placeNewPiece('h', 7, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));

        placeNewPiece('b', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new King(board, Color.BLACK));
	}
}
