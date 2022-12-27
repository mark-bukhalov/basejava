package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume r, int index) {
        int indexIns = (index + 1) * -1;
        Resume[] storageTemp = new Resume[size - indexIns + 1];
        storageTemp[0] = r;
        System.arraycopy(STORAGE, indexIns, storageTemp, 1, storageTemp.length - 1);
        System.arraycopy(storageTemp, 0, STORAGE, indexIns, storageTemp.length);
    }

    @Override
    protected void deleteResume(int index) {
        Resume[] storageTemp = new Resume[size - index];
        System.arraycopy(STORAGE, index + 1, storageTemp, 0, size - index - 1);
        Arrays.fill(STORAGE, index, size, null);
        System.arraycopy(storageTemp, 0, STORAGE, index, size - index);
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(STORAGE, 0, size, searchKey);
    }

}
