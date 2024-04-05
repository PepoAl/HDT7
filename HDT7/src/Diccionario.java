import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

public class Diccionario {

    private BinarySearchTree<String, Association<String, String>> englishTree;
    private BinarySearchTree<String, Association<String, String>> spanishTree;
    private BinarySearchTree<String, Association<String, String>> frenchTree;

    public Diccionario() {
        Comparator<String> stringComparator = String::compareToIgnoreCase;
        englishTree = new BinarySearchTree<>(stringComparator);
        spanishTree = new BinarySearchTree<>(stringComparator);
        frenchTree = new BinarySearchTree<>(stringComparator);
    }

    public void cargarDiccionario(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("diccionario.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String englishWord = parts[0].trim();
                    String spanishWord = parts[1].trim();
                    String frenchWord = parts[2].trim();

                    Association<String, String> englishAssociation = new Association<>(englishWord, spanishWord + " - " + frenchWord);
                    Association<String, String> spanishAssociation = new Association<>(spanishWord, englishWord + " - " + frenchWord);
                    Association<String, String> frenchAssociation = new Association<>(frenchWord, englishWord + " - " + spanishWord);

                    englishTree.insert(englishWord, englishAssociation);
                    spanishTree.insert(spanishWord, spanishAssociation);
                    frenchTree.insert(frenchWord, frenchAssociation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String detectarIdioma(String texto) {
        String[] palabras = texto.split("\\s+");
        int englishCount = 0;
        int spanishCount = 0;
        int frenchCount = 0;

        for (String palabra : palabras) {
            if (englishTree.find(palabra) != null) {
                englishCount++;
            }
            if (spanishTree.find(palabra) != null) {
                spanishCount++;
            }
            if (frenchTree.find(palabra) != null) {
                frenchCount++;
            }
        }

        if (englishCount >= spanishCount && englishCount >= frenchCount) {
            return "inglés";
        } else if (spanishCount >= englishCount && spanishCount >= frenchCount) {
            return "español";
        } else {
            return "francés";
        }
    }

    public void procesarTexto(String texto, String idiomaDestino) {
        String[] palabras = texto.split("\\s+");
        for (String palabra : palabras) {
            String traduccion = traducirPalabra(palabra, idiomaDestino);
            System.out.print(traduccion + " ");
        }
        System.out.println();
    }

    private String traducirPalabra(String palabra, String idiomaDestino) {
        switch (idiomaDestino.toLowerCase()) {
            case "español":
                return traducir(palabra, "español");
            case "francés":
                return traducir(palabra, "francés");
            default:
                return traducir(palabra, "inglés");
        }
    }

    private String traducir(String palabra, String idiomaDestino) {
        String traduccion = String.valueOf(buscarTraduccion(palabra, idiomaDestino));
        if (traduccion != null) {
            return traduccion;
        } else {
            return "*" + palabra + "*";
        }
    }

    private String buscarTraduccion(String palabra, String idiomaDestino) {
        switch (idiomaDestino.toLowerCase()) {
            case "español":
                return spanishTree.find(palabra);
            case "francés":
                return frenchTree.find(palabra);
            default:
                return englishTree.find(palabra);
        }
    }
}