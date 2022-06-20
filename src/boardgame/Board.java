package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		// quando for criar um tabuleiro, a quantidade de linhas e colunas tem que ser pelo menos 1
		// n�o faz sentido criar um tabuleiro com 0 ou menos colunas
		if (rows < 1 || columns < 1) { // se a quantidade de linhas ou de colunas for menor do que 1, significa que os dados s�o inv�lidos e lan�a a exce��o
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column.");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	// PROGRAMA��O DEFENSIVA:
	// os m�todos setRows e setColumns foram exclu�dos, pois uma vez o tabuleiro criado, 
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
		// antes de colocar uma pe�a na posi��o, precisa testar se j� existe uma pe�a nessa posi��o
		// se j� existe uma pe�a naquela posi��o, n�o se pode colocar uma outra pe�a nessa mesma posi��o
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; // position da classe Piece � acess�vel porque esse atributo � protected
	}
	
	public Piece removePiece(Position position) {
		// Se essa posi��o n�o existe no tabuleiro, retorna com uma exce��o
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board.");
		}
		if (piece(position) == null) { // se a pe�a do tabuleiro nessa posi��o � igual a nulo, significa que n�o tem nenhuma pe�a nessa posi��o
			return null;
		}
		
		// se n�o acontecer as condi��es acima, a� sim retira a pe�a do tabuleiro
		Piece aux = piece(position); 
		aux.position = null; // essa pe�a foi retirada do tabuleiro, ela n�o tem mais posi��o
		pieces[position.getRow()][position.getColumn()] = null; // na matriz de pe�as, nessa position onde estou removendo a pe�a, agora vai ser nulo, indicando que n�o tem mais pe�a nessa posi��o da matriz
		return aux; // retorna a pe�a que foi retirada
	}
	
	private boolean positionExists(int row, int column) {
		// uma posi��o numa determinada linha e numa determinada coluna existe quando essa posi��o est� dentro do tabuleiro
		
		return row >= 0 && row < rows && column >= 0 && column < columns; // condi��o completa pra ver se uma posi��o existe
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
