package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполненно");
        }
        if (index > 0) {
            System.out.printf("Резюме с uuid %s уже существует%n", r.getUuid());
        } else {
            int indexIns = (index + 1) * -1;
            Resume[] storageTemp = new Resume[size - indexIns + 1];
            storageTemp[0] = r;
            System.arraycopy(STORAGE, indexIns, storageTemp, 1, storageTemp.length - 1);
            System.arraycopy(storageTemp, 0, STORAGE, indexIns, storageTemp.length);
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            Resume[] storageTemp = new Resume[size - index];
            System.arraycopy(STORAGE, index + 1, storageTemp, 0, size - index - 1);
            Arrays.fill(STORAGE, index, size, null);
            System.arraycopy(storageTemp, 0, STORAGE, index, size - index);
            size--;
        } else {
            printErorResumeNotFound(uuid);
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(STORAGE, 0, size, searchKey);
    }

}
