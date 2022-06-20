package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		// quando for criar um tabuleiro, a quantidade de linhas e colunas tem que ser pelo menos 1
		// não faz sentido criar um tabuleiro com 0 ou menos colunas
		if (rows < 1 || columns < 1) { // se a quantidade de linhas ou de colunas for menor do que 1, significa que os dados são inválidos e lança a exceção
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column.");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	// PROGRAMAÇÃO DEFENSIVA:
	// os métodos setRows e setColumns foram excluídos, pois uma vez o tabuleiro criado, 
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
		// antes de colocar uma peça na posição, precisa testar se já existe uma peça nessa posição
		// se já existe uma peça naquela posição, não se pode colocar uma outra peça nessa mesma posição
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; // position da classe Piece é acessível porque esse atributo é protected
	}
	
	public Piece removePiece(Position position) {
		// Se essa posição não existe no tabuleiro, retorna com uma exceção
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board.");
		}
		if (piece(position) == null) { // se a peça do tabuleiro nessa posição é igual a nulo, significa que não tem nenhuma peça nessa posição
			return null;
		}
		
		// se não acontecer as condições acima, aí sim retira a peça do tabuleiro
		Piece aux = piece(position); 
		aux.position = null; // essa peça foi retirada do tabuleiro, ela não tem mais posição
		pieces[position.getRow()][position.getColumn()] = null; // na matriz de peças, nessa position onde estou removendo a peça, agora vai ser nulo, indicando que não tem mais peça nessa posição da matriz
		return aux; // retorna a peça que foi retirada
	}
	
	private boolean positionExists(int row, int column) {
		// uma posição numa determinada linha e numa determinada coluna existe quando essa posição está dentro do tabuleiro
		
		return row >= 0 && row < rows && column >= 0 && column < columns; // condição completa pra ver se uma posição existe
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
