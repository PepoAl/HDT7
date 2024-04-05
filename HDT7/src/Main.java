import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Diccionario diccionario = new Diccionario();
        diccionario.cargarDiccionario("diccionario.txt");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al traductor. Por favor, ingrese las palabras que desea traducir:");
        String texto = scanner.nextLine();

        System.out.println("Detectando automáticamente el idioma del texto...");
        String idiomaDetectado = diccionario.detectarIdioma(texto);
        System.out.println("Idioma detectado: " + idiomaDetectado);

        System.out.println("Ingrese el idioma al que desea traducir las palabras (inglés/español/francés):");
        String idiomaDestino = scanner.nextLine();

        System.out.println("Traduciendo...");
        diccionario.procesarTexto(texto, idiomaDestino);
    }
}
