import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TetrisModelTests {

    private TetrisModel tetrisModel;
    private int initialScore;
    private int initialSpeed;

    @Before
    public void setUp() {
        tetrisModel = new TetrisModel(TetrisModel.DEFAULT_WIDTH, TetrisModel.DEFAULT_HEIGHT, TetrisModel.DEFAULT_COLORS_NUMBER);
        initialScore = TetrisModel.SCORE;
        initialSpeed = TetrisModel.SPEED;
    }

    @Test
    public void testAssessScore_OneRowCleared() {
        tetrisModel.assessScore(1, 0);
        assertEquals(initialScore + 40, TetrisModel.SCORE);
    }

    @Test
    public void testAssessScore_TwoRowsCleared() {
        tetrisModel.assessScore(2, 0);
        assertEquals(initialScore + 100, TetrisModel.SCORE);
    }

    @Test
    public void testAssessScore_ThreeRowsCleared() {
        tetrisModel.assessScore(3, 0);
        assertEquals(initialScore + 300, TetrisModel.SCORE);
    }

    @Test
    public void testAssessScore_FourRowsCleared() {
        tetrisModel.assessScore(4, 0);
        assertEquals(initialScore + 1200, TetrisModel.SCORE);
    }

    @Test
    public void testAssessScore_NoRowsCleared() {
        tetrisModel.assessScore(0, 0);
        assertEquals(initialScore, TetrisModel.SCORE);
    }

    @Test
    public void testAssessScore_LevelIncrease() {
        int level = 2;
        tetrisModel.assessScore(4, level);
        assertEquals(initialScore + 1200 * (level + 1), TetrisModel.SCORE);
    }

    @Test
    public void testAdjustSpeed_NoChange() {
        TetrisModel.SCORE = 400;
        tetrisModel.adjustSpeed();
        assertEquals(initialSpeed, TetrisModel.SPEED);
    }

    @Test
    public void testAdjustSpeed_IncreaseTo500() {
        TetrisModel.SCORE = 500;
        tetrisModel.adjustSpeed();
        assertEquals(500, TetrisModel.SPEED);
    }

    @Test
    public void testAdjustSpeed_IncreaseTo300() {
        TetrisModel.SCORE = 1000;
        tetrisModel.adjustSpeed();
        assertEquals(300, TetrisModel.SPEED);
    }

    @Test
    public void testAdjustSpeed_IncreaseTo200() {
        TetrisModel.SCORE = 2000;
        tetrisModel.adjustSpeed();
        assertEquals(200, TetrisModel.SPEED);
    }

    @Test
    public void testAdjustSpeed_IncreaseTo100() {
        TetrisModel.SCORE = 5000;
        tetrisModel.adjustSpeed();
        assertEquals(100, TetrisModel.SPEED);
    }
}
