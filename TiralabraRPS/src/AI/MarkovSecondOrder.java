package AI;

/**
 * Muodostaa pelaajan ja tekoälyn historiasta matriisin
 * @author joopeti
 */
public class MarkovSecondOrder extends Strategy{
    

    @Override
    public int predictPlayerMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int predictAiMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getFirstRoundToUpdateModels() {
        return 4;
    }

    @Override
    public void updateModels(double decay) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
