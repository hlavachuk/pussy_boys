package Task5;

public class LocalDiskEmergencyWriter implements BackupWriter {
    @Override
    public void writeBackupLog(String data) {
        System.out.println("Локальний дублюючий запис телеметрії: " + data);
    }
}
