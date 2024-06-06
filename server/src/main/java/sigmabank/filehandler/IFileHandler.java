package sigmabank.filehandler;

import java.util.List;

public interface IFileHandler {
    public List<Object> readObjects(String path);
    public void writeObjects(String path, List<Object> objects);
}
