package components;

public class Light extends Track
{
    private boolean isRed = true;
    private int lightId = 0;

    public Light(int id)
    {
        lightId = id;
    }

    public boolean isRed()
    {
        return isRed;
    }

    public void setRed(boolean red)
    {
        if (red)
            isRed = true;
        else
            isRed = false;
    }

    public int getLightId()
    {
        return lightId;
    }
}
