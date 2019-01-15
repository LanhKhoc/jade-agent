/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Admin
 */
public class Disk {
    private String letter;
    private String typeDescription;
    private long capacityInGB;

    public Disk(String letter, String typeDescription, long capacityInGB) {
        this.letter = letter;
        this.typeDescription = typeDescription;
        this.capacityInGB = capacityInGB;
    }

    public static List<Disk> getAll() {
        List<Disk> list = new ArrayList<>();
        File[] paths = File.listRoots();
        FileSystemView fsv = FileSystemView.getFileSystemView();

        for (File path : paths) {
            String letter = fsv.getSystemDisplayName(path);
            String typeDescription = fsv.getSystemTypeDescription(path);
            long capacityInGB = getCapacity(path);
            Disk driveInformation = new Disk(letter, typeDescription, capacityInGB);
            list.add(driveInformation);
        }

        return list;
    }

    private static long getCapacity(File file) {
        long totalSpace = file.getTotalSpace();
        return totalSpace / 1024 / 1024 / 1024;
    }

    public String getLetter() {
        return letter;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public long getCapacityInGB() {
        return capacityInGB;
    }

    @Override
    public String toString() {
        return letter + ": " + capacityInGB + "BG - " + typeDescription;
    }
}
