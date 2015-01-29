package AI;

/**
 *
 * @author joopeti
 */
public abstract class Strategy {
    public abstract int predictPlayerMove();
    public abstract int predictAiMove();
    public abstract void updateModels(double decay);
    public abstract int getFirstRoundToUpdateModels();
}
