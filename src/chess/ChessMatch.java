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

	// quem sabe a dimens�o do tabuleiro de xadrez � a classe ChessMatch
	// nessa classe que ser� dito que a dimens�o do tabuleiro ser� 8x8

	private int turn;
	private Color currentPlayer;	
	private Board board; // uma partida de xadrez tem que ter um tabuleiro
	private boolean check;
	private boolean checkMate;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8); // no momento de inicio da da partida, � criado um tabuleiro 8x8
		turn = 1;
		currentPlayer = Color.WHITE;
		// check = false; -> serve para enfatizar o valor false do boolean de check, mas n�o � necess�rio, pois uma propriedade boolean por padr�o come�a com falso
		initialSetup(); // e � chamada a fun��o initialSetup();
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
		
		if (testCheck(currentPlayer)) { // testa se o jogador colocou o pr�prio jogador em Check, isso n�o pode acontecer 
			undoMove(source, target, capturedPiece);	// se esse if falhar, significa que o jogador n�o se colocou em check
			throw new ChessException("You can't put yourself in check.");
		}
		
		// a propriedade check recebe -> se testCheck do oponente for verdade, retorna true, a partida est� em check, sen�o retorna false, que n�o est� em check
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if (testCheck(opponent(currentPlayer))) { // se a jogada que eu fiz deixou o oponente e checkMate, o jogo vai ter que acabar
			checkMate = true; // checkMate � true e acaba a partida
		}
		else { // caso n�o esteja em checkMate
			nextTurn(); // chama o pr�ximo turno
		}
		return (ChessPiece)capturedPiece; 
	}
	
	private Piece makeMove(Position source, Position target) { 
		Piece p = board.removePiece(source); // retira a pe�a que estava na posi��o de origem
		Piece capturedPiece = board.removePiece(target); // remove a poss�vel pe�a capturada que esteja na posi��o de destino
		board.placePiece(p, target); // coloca a pe�a p na posi��o de destino target
		
		if (capturedPiece != null) { // se uma pe�a foi capturada, ou seja, se for diferente de null, uma pe�a foi capturada
			piecesOnTheBoard.remove(capturedPiece); // ent�o remove a pe�a capturada da lista de pe�as do tabuleiro
			capturedPieces.add(capturedPiece); // e adiciona ela na lista de pe�as capturadas
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target); // retira a pe�a que moveu l� no destino
		board.placePiece(p, source); // devolve a pe�a para a posi��o de origem
		
		if (capturedPiece != null) { // se verdadeiro...
			board.placePiece(capturedPiece, target); // volta a pe�a para o tabuleiro na posi��o de destino
			capturedPieces.remove(capturedPiece); // tira a pe�a da lista de pe�as capturadaS
			piecesOnTheBoard.add(capturedPiece); // coloca novamente na lista de pe�as do tabuleiro
		}
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
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
		// se essa cor passada como argumento for igual a Color.WHITE, ent�o retorna Color.BLACK. Caso contr�rio, retorna Color.WHITE.
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) { // para cada pe�a p na lista list
			if (p instanceof King) { // se essa pe�a p for uma inst�ncia de rei, significa que retornou um rei
				return (ChessPiece)p; // retorna essa pe�a p (downcasting para ChessPiece)
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board.");
	} 
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) { // se nessa matriz na linha e coluna, se esse elemento for verdadeiro, signifca que o rei est� em cheque
				return true; // retorna true se o teste de cheque for verdadeiro
			}
		}
		return false; // se esgotar todas pe�as advers�rias e nenhuma dessas pe�as tiver na matriz de movimentos poss�veis a posi��o do rei marcada como true, significa que o rei n�o est� em cheque e retorna false 
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) { // teste para eliminar a possibilidade dessa pe�a/cor n�o estar em Check
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); // pega todas pe�as de uma determinada Color
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
						if (!testCheck) { // se n�o estava em check, significa que esse movimento tirou o rei do check e retorna false, que n�o est� em checkMate
							return false;
						}
					}
				}
			}
		}
		return true;
	}	
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { // m�todo respons�vel por receber as coordenadas do xadrez
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // coloca a pe�a no tabuleiro
		piecesOnTheBoard.add(piece); // coloca essa pe�a na lista de pe�as no tabuleiro
	}

	// m�todo respons�vel por iniciar a partida de xadrez, colocando as pe�as no tabuleiro
	private void initialSetup() {
		placeNewPiece('h', 7, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));

        placeNewPiece('b', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new King(board, Color.BLACK));
	}
}
