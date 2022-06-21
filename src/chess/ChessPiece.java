package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	protected boolean isThereOpponentPiece(Position position) { // acess�vel somente pelo mesmo pacote e pelas subclasses que s�o as pe�as
		ChessPiece p = (ChessPiece)getBoard().piece(position); // downcasting para ChessPiece
		return p != null && p.getColor() != color; // se a cor da pe�a dessa posi��o � diferente da cor da minha pe�a, ou seja, se � uma pe�a advers�ria
 	}
}
