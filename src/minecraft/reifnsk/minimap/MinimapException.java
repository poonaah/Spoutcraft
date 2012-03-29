package reifnsk.minimap;

public class MinimapException extends RuntimeException
{
    public MinimapException()
    {
    }

    public MinimapException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public MinimapException(String s)
    {
        super(s);
    }

    public MinimapException(Throwable throwable)
    {
        super(throwable);
    }
}
