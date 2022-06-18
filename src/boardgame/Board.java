package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		// quando for criar um tabuleiro, a quantidade de linhas e coluna tem que ser pelo menos 1
		// n�o faz sentido criar um tabuleiro com 0 ou menos colunas
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column.");
		}
		
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	// PROGRAMA��O DEFENSIVA:
	// os m�todos setRows e setColumns foram exclu�dos, porque uma vez o tabuleiro criado, 
	// n�o se pode mais mudar a quantidade de linhas e de colunas desse tabuleiro
	
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int row, int column) { // retorna a matriz pieces na linha row e na coluna column
		// PROGRAMA��O DEFENSIVA
		// Se essa posi��o n�o existe, lan�a uma BoardException
		if (!positionExists(row, column)) {
			throw new BoardException("Position not on the board.");
		}
		
		return pieces[row][column];
	}
	
	public Piece piece(Position position) { // sobrecarga do m�todo piece acima, mas recebendo um position, retornando a pe�a por posi��o
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board.");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) { // coloca a pe�a numa determinada posi��o
		// PROGRAMA��O DEFENSIVA:
		// Antes de colocar uma pe�a na posi��o, precisa testar se j� existe uma pe�a nessa posi��o
		// Se j� existe uma pe�a naquela posi��o, n�o se pode colocar outra pe�a
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; // position da classe Piece � acess�vel porque esse atributo � protected
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		// antes de testar o thereIsAPiece, ele j� testa se a posi��o existe
		// se n�o existe, j� lan�a uma exce��o
		if (!positionExists(position)) { 
			throw new BoardException("Position not on the board.");
		}
		return piece(position) != null; // se essa pe�a na position for diferente de nulo, significa que tem uma pe�a nessa posi��o
	}
}
