package Task5;

import java.util.ArrayList;
import java.util.List;

public class MockBackupWriter implements BackupWriter {
    public final List<String> logs = new ArrayList<>();

    @Override
    public void writeBackupLog(String data) {
        logs.add(data);
    }
}
