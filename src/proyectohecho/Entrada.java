package proyectohecho;

import java.util.Scanner;

/**
 * Utilidades de entrada por teclado compartidas por todos los menús:
 * un único Scanner para todo el programa y lectura validada de enteros/decimales.
 */
public class Entrada {
    public static Scanner sc = new Scanner(System.in);

    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    public static double leerDecimal(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextDouble()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        double valor = sc.nextDouble();
        sc.nextLine();
        return valor;
    }

    // función para pausar en cada parte del menú
    public static void pausar() {
        System.out.println("\n PRESIONE ENTER PARA VOLVER AL MENU");
        sc.nextLine();
    }
}