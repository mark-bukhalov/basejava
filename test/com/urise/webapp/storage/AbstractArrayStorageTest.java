package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        STORAGE.clear();
        try {
            for (int i = 0; i < 10000; i++) {
                Resume r = new Resume("Name");
                STORAGE.save(r);
            }
        } catch (StorageException e) {
            Assert.fail("Ошибка переполнения вызвана раньше чем планировалось");
        }
        Resume r = new Resume("Name");
        STORAGE.save(r);
    }
}