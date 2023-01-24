package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] STORAGE = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    protected Resume storageGet(int index) {
        return STORAGE[index];
    }

    @Override
    protected void storageSave(Resume r) {
        int index = findIndex(r.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище переполнено", r.getUuid());
        } else {
            insertResume(r, index);
            size++;
        }
    }

    @Override
    protected void storageUpdate(int index, Resume r) {
        STORAGE[index] = r;
    }

    @Override
    protected void storageDelete(int index) {
        deleteResume(index);
        size--;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, size);
    }

    public void clear() {
        Arrays.fill(STORAGE, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    protected abstract void insertResume(Resume r, int index);

    protected abstract void deleteResume(int index);

    protected abstract int findIndex(String uuid);
}
