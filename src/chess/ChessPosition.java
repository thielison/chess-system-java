package chess;

import boardgame.Position;

public class ChessPosition {

	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		// PROGRAMA��O DEFENSIVA:
		// O tabuleiro vai de 'a' at� 'h' e de 1 at� 8, somente
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error instantiating ChessPosition: Valid values are from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}

	// m�todos setColumn e setRow exclu�dos
	// a coluna e a linha n�o podem ser livremente alteradas
	
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row; 
		// imprime letra e n�mero da posi��o da pe�a: "a1", "b2", etc...
		// o string vazio "" � um macete pra concatenar autom�tico. Se tirar esse string vazio, o compilador n�o vai aceitar
		// se coloca o string vazio pro compilador entender que isso � uma concatena��o de strings
	}
}
