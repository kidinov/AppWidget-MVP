package kidinov.org.ecosia.data;


public class MusicFileMetaData {
    private final String name;
    private final String path;

    public MusicFileMetaData(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

}
