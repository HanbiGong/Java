package com.apd545.hotel;

public class ActivityLogService {

    private static final ActivityLogService instance = new ActivityLogService();
    public static ActivityLogService getInstance() { return instance; }

    private final ActivityLogRepository repo = ActivityLogRepository.getInstance();

    private ActivityLogService() {}

    /**
     * Simple 1-line logging
     */
    public void log(String username, String action) {
        ActivityLog log = new ActivityLog(username, action);
        repo.save(log);
        System.out.println("LOG SAVED: " + action);
    }

    public java.util.List<ActivityLog> getAll() {
        return repo.findAll();
    }
}
