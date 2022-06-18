package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Piece piece(int row, int column) { // retorna a matriz pieces na linha row e na coluna column
		return pieces[row][column];
	}
	
	public Piece piece(Position position) { // sobrecarga do m�todo piece acima, mas recebendo um position, retornando a pe�a por posi��o
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; // position da classe Piece � acess�vel porque esse atributo � protected
	}
}
