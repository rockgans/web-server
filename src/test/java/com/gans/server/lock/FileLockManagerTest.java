package com.gans.server.lock;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by gmohan on 26/02/19.
 */

public class FileLockManagerTest {

    static String filename = null;

    @BeforeClass
    public static void init() {

        filename = "File1";
    }

    @Test
    public void shouldReturnTrueForFirstUse() throws InterruptedException {

        boolean isLockAquired = FileLockManager.tryLock(filename);
        Assert.assertTrue(isLockAquired);

    }

    @Test
    public void shouldReturnFalseForSecondThread() throws InterruptedException {

        boolean isLockAquired = FileLockManager.tryLock(filename);

        new Thread() {
            public void run() {
                try {
                    boolean isLockAquiredAgain = FileLockManager.tryLock(filename);
                    Assert.assertFalse(isLockAquiredAgain);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Test
    public void shouldReturnTrueWhenUnlockedByFirst() throws InterruptedException{
        boolean isLockAquired = FileLockManager.tryLock(filename);
        FileLockManager.releaseLock(filename);
        new Thread() {
            public void run() {
                try {
                    boolean isLockAquiredAgain = FileLockManager.tryLock(filename);
                    Assert.assertTrue(isLockAquiredAgain);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
