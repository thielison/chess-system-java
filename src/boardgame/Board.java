package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		// quando for criar um tabuleiro, a quantidade de linhas e coluna tem que ser pelo menos 1
		// não faz sentido criar um tabuleiro com 0 ou menos colunas
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column.");
		}
		
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	// PROGRAMAÇÃO DEFENSIVA:
	// os métodos setRows e setColumns foram excluídos, porque uma vez o tabuleiro criado, 
	// não se pode mais mudar a quantidade de linhas e de colunas desse tabuleiro
	
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int row, int column) { // retorna a matriz pieces na linha row e na coluna column
		// PROGRAMAÇÃO DEFENSIVA
		// Se essa posição não existe, lança uma BoardException
		if (!positionExists(row, column)) {
			throw new BoardException("Position not on the board.");
		}
		
		return pieces[row][column];
	}
	
	public Piece piece(Position position) { // sobrecarga do método piece acima, mas recebendo um position, retornando a peça por posição
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board.");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) { // coloca a peça numa determinada posição
		// PROGRAMAÇÃO DEFENSIVA:
		// Antes de colocar uma peça na posição, precisa testar se já existe uma peça nessa posição
		// Se já existe uma peça naquela posição, não se pode colocar outra peça
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; // position da classe Piece é acessível porque esse atributo é protected
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		// antes de testar o thereIsAPiece, ele já testa se a posição existe
		// se não existe, já lança uma exceção
		if (!positionExists(position)) { 
			throw new BoardException("Position not on the board.");
		}
		return piece(position) != null; // se essa peça na position for diferente de nulo, significa que tem uma peça nessa posição
	}
}
