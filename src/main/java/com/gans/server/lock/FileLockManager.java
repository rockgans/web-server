package com.gans.server.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Manages the lock over files for read and write. Holds the path of the file and a lock over it when accessed by a thread.
 * Has a thread safe static final mapping of the path of the file and lock object.
 * Created by gmohan on 25/02/19.
 */
public class FileLockManager {

    private final static Map<String, ReentrantLock> fileLockManager = new ConcurrentHashMap<>();
    private static final int GIVEUP_TIME = 2;

    /**
     * Called to try and acquire lock on a file. It waits for a time frame before giving up on the request to access the file.
     *
     * @param file on which the thread is trying to acquire lock
     * @return
     */
    public static boolean tryLock(String file) throws InterruptedException {

        if (!fileLockManager.containsKey(file)) {
            ReentrantLock lock = new ReentrantLock();
            fileLockManager.put(file, lock);
        }
        ReentrantLock lock = fileLockManager.get(file);
        return lock.tryLock(GIVEUP_TIME, TimeUnit.SECONDS);
    }

    /**
     * Releases the lock on the file once done with the usage.
     *
     * @param file file on which lock is held
     */
    public static void releaseLock(String file) {
        ReentrantLock lock = fileLockManager.get(file);
        if (lock != null)
            lock.unlock();
    }
}
