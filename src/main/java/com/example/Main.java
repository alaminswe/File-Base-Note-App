package com.example;

import java.io.*;
import java.util.Scanner;

public class Main {
    static final String NOTES_FOLDER = "notes";
    
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("Wecome to the File note Application!");

        File folder = new File(NOTES_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }

        while (true) {
            System.out.println("\n===== Note Taking Application =====");
            System.out.println("1. Create New Note");
            System.out.println("2. View All Notes");
            System.out.println("3. Update Note");
            System.out.println("4. Delete Note");
            System.out.println("5. Reset (Delete All Notes)");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> createNote();
                case 2 -> viewNotes();
                case 3 -> updateNote();
                case 4 -> deleteNote();
                case 5 -> resetNotes();
                case 6 -> {
                    System.out.println("Thank you! Goodbye 👋");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }
    static void resetNotes() {
        File folder = new File(NOTES_FOLDER);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        System.out.println("All notes deleted successfully!");
    }
    static void deleteNote() {
        System.out.println("Your notes are : ");
        File folder = new File(NOTES_FOLDER);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No notes found!");
            return;
        }
        for(File file : files) {
            System.out.println(file.getName());
        }
        System.out.print("Enter the file id to delete: ");
        String fileId = sc.nextLine();

        File fileToDelete = new File(NOTES_FOLDER + "/note-" + fileId+".txt");
        if(fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                System.out.println("Note deleted successfully!");
            } else {
                System.out.println("Note not found!");
            }
        }
    }
    static void updateNote() {
        System.out.println("Your all notes (with the unique identifier): ");
        File folder = new File(NOTES_FOLDER);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No notes found!");
            return;
        }
        for(File file : files) {
            System.out.println(file.getName());
        }

        System.out.print("\nEnter note ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        File file = new File(NOTES_FOLDER + "/note-" + id + ".txt");

        if (!file.exists()) {
            System.out.println("Note not found!");
            return;
        }
        System.out.println("1. Replace Note");
        System.out.println("2. Append Note");
        System.out.print("Choose option: ");
        int option = sc.nextInt();
        sc.nextLine();

        try {
            if (option == 1) {
                System.out.print("Enter new content: ");
                String newContent = sc.nextLine();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(newContent);
                fileWriter.close();
            } else if (option == 2) {
                System.out.print("Enter content to append: ");
                String appendContent = sc.nextLine();
                FileWriter fileWriter = new FileWriter(file, true);
                fileWriter.write("\n" + appendContent);
                fileWriter.close();
            } else {
                System.out.println("Invalid option!");
                return;
            }
            System.out.println("Note updated successfully!");

        } catch (IOException e) {
            System.out.println("Error updating note!");
        }
    }
    static void viewNotes() {
        System.out.println("Your notes are : ");
        File folder = new File(NOTES_FOLDER);
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("No notes found!");
            return;
        }

        for (File file : files) {
            System.out.println("\n--- " + file.getName() + " ---");
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading file!");
            }
        }
    }
    static void createNote() {
        try {
            System.out.print("Enter your note: ");
            String noteContent = sc.nextLine();

            int id = getNextNoteId();
            File noteFile = new File(NOTES_FOLDER + "/note-" + id + ".txt");

            FileWriter writer = new FileWriter(noteFile);
            writer.write(noteContent);
            writer.close();
            System.out.println("Note created with ID: " + id);
        } catch (IOException e) {
            throw new RuntimeException("Error creating note!" + e);
        }
    }

    static int getNextNoteId() {
        File folder = new File(NOTES_FOLDER);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            return 1;
        }
        return files.length + 1;
    }
}