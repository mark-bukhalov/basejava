package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    private final ArrayList<Resume> STORAGE = new ArrayList<>();

    @Override
    protected int findIndex(String uuid) {
        int index = 0;
        for (Resume r : STORAGE) {
            if (r.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    protected boolean isExist(Resume r) {
        return STORAGE.contains(r);
    }

    @Override
    protected void storageSave(Resume r) {
        STORAGE.add(r);
    }

    @Override
    protected Resume storageGet(int index) {
        return STORAGE.get(index);
    }

    @Override
    protected void storageUpdate(int index, Resume r) {
        STORAGE.set(index, r);
    }

    @Override
    protected void storageDelete(int index) {
        STORAGE.remove(index);
    }

    @Override
    public void clear() {
        STORAGE.clear();
    }

    @Override
    public Resume[] getAll() {
        return STORAGE.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return STORAGE.size();
    }
}
