package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private final ArrayList<Resume> STORAGE = new ArrayList<>();

    @Override
    protected Object findSearchKey(String uuid) {
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
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        STORAGE.add(r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return STORAGE.get((Integer) searchKey);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        STORAGE.set((Integer) searchKey, r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        STORAGE.remove((int) searchKey);
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
