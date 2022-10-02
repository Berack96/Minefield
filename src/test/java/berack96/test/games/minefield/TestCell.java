package berack96.test.games.minefield;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import berack96.games.minefield.object.Cell;
import berack96.games.minefield.object.CellStatus;

public class TestCell {
	
	public Cell cell;
	
	@BeforeAll
	public void initialize() {
		cell = new Cell();
		assertEquals(cell.getStatus(), CellStatus.COVERED);
	}
	
	@Test
	public void cellUncover() {
		assertFalse(cell.isUncovered());
		cell.uncover();
		assertTrue(cell.isUncovered());
		assertEquals(cell.getStatus(), CellStatus.UNCOVERED);
	}
	
	@Test
	public void cellMine() {
		assertFalse(cell.isMine());
		cell.setMine();
		assertFalse(cell.isUncovered());
		assertTrue(cell.isMine());
	}
	
	@Test
	public void cellDangerous() {
		cell.setDangerous();
		assertFalse(cell.isUncovered());
		assertEquals(cell.getStatus(), CellStatus.DANGEROUS);
	}
	
	@Test
	public void cellUncoveredMine() {
		cell.setMine();
		cell.uncover();
		assertEquals(cell.getStatus(), CellStatus.EXPLODED);
	}
	
	@Test
	public void cellUncoveredMineDangerous() {
		cell.setMine();
		cell.setDangerous();
		cell.uncover();
		
		assertEquals(cell.getStatus(), CellStatus.GOTRIGHT);
	}
}
