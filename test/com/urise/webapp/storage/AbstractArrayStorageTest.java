package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private final Storage STORAGE;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);


    public AbstractArrayStorageTest(Storage storage) {
        this.STORAGE = storage;
    }

    @Before
    public void setUp() {
        STORAGE.clear();
        STORAGE.save(new Resume(UUID_1));
        STORAGE.save(new Resume(UUID_2));
        STORAGE.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        STORAGE.clear();
        assertSize(0);
        Resume[] allResume = STORAGE.getAll();
        Assert.assertEquals(0, allResume.length);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void save() {
        STORAGE.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        STORAGE.save(new Resume(UUID_1));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        STORAGE.get(UUID_NOT_EXIST);
    }

    @Test
    public void update() {
        Resume resumeUpdate = new Resume(UUID_1);
        STORAGE.update(resumeUpdate);
        Assert.assertSame(resumeUpdate, STORAGE.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resumeUpdate = new Resume(UUID_NOT_EXIST);
        STORAGE.update(resumeUpdate);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        STORAGE.delete(UUID_1);
        assertSize(2);
        STORAGE.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        STORAGE.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAll() {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(expected, STORAGE.getAll());
        assertSize(3);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        STORAGE.clear();
        try {
            for (int i = 0; i < 10000; i++) {
                Resume r = new Resume();
                STORAGE.save(r);
            }
        } catch (StorageException e) {
            Assert.fail("Ошибка переполнения вызвана раньше чем планировалось");
        }
        Resume r = new Resume();
        STORAGE.save(r);
    }

    private void assertSize(int size) {
        Assert.assertEquals(STORAGE.size(), size);
    }

    private void assertGet(Resume r) {
        Assert.assertEquals(STORAGE.get(r.getUuid()), r);
    }
}