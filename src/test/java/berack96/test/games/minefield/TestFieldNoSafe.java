package berack96.test.games.minefield;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import berack96.games.minefield.object.FieldNoSafe;

public class TestFieldNoSafe {
	
	public FieldNoSafe field;
	public final int height = 10;
	public final int length = 20;
	public final int mines = 10;
	
	@BeforeAll
	public void initialize() {
		field = new FieldNoSafe(this.length, this.height, this.mines);
	}
	
	@Test
	public void fieldStart() {
		assertEquals(field.lines, this.height);
		assertEquals(field.lines, this.length);
		assertEquals(field.getNumCoveredCell(), this.height*this.length);
		assertEquals(field.getNumDagerousCell(), 0);
		assertEquals(field.getNumUncoveredCell(), 0);
		assertEquals(field.getNumMines(), this.mines);
	}
}
