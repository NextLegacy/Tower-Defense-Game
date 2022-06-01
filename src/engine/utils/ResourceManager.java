package engine.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ResourceManager
{
    
    private static final String RESOURCE_LOCATION = "./resources/";

    public static File getFile(String type, String name)
    {
        return new File(RESOURCE_LOCATION + type + "/" + name);
    }

    public static BufferedReader getReader(String type, String name) throws FileNotFoundException
    {
        return new BufferedReader(new FileReader(getFile(type, name)));
    }

}
