package br.com.rpg.sistema;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public final class SaveService {
    private SaveService(){}

    public static final Path SAVE_PATH = Path.of(System.getProperty("user.home"), ".rpg_ravina_nevoa.save");

    public static void save(SaveState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(SAVE_PATH))) {
            oos.writeObject(state);
        } catch (IOException e) {
            System.err.println("Falha ao salvar jogo: " + e.getMessage());
        }
    }

    public static SaveState load() {
        if (!Files.exists(SAVE_PATH)) return null;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(SAVE_PATH))) {
            Object obj = ois.readObject();
            return (SaveState) obj;
        } catch (Exception e) {
            System.err.println("Falha ao carregar jogo: " + e.getMessage());
            return null;
        }
    }

    public static void delete() {
        try { Files.deleteIfExists(SAVE_PATH); } catch (IOException ignored) {}
    }
}
